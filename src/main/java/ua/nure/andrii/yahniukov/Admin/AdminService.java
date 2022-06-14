package ua.nure.andrii.yahniukov.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserRepository;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.StationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.StationUser.StationUserRepository;
import ua.nure.andrii.yahniukov.StationUser.StationUserService;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserRepository;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.User.dto.FormRoleDto;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserService stationUserService;
    private final StationUserRepository stationUserRepository;

    @Value("${db.name}")
    private String dbName;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;


    public void changeRole(String email, FormRoleDto role) {
        UserEntity user = userService.findByEmail(email);
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new BadRequestException("Administrator cannot change role");
        }
        if (user.getRole().equals(role.getRole())) {
            throw new BadRequestException("User with email: " + email + " cannot have the same role");
        }
        if (role.getRole().equals(UserRole.SERVICE) || role.getRole().equals(UserRole.CHARGER)) {
            throw new BadRequestException("Can't set partner role");
        }
        user.setRole(role.getRole());
        userRepository.save(user);
    }

    public void acceptVerificationChargerUser(String email) {
        ChargerUserEntity chargerUser = chargerUserService.findByEmail(email);
        chargerUser.setIsVerification(true);
        chargerUserRepository.save(chargerUser);
    }

    public void acceptVerificationStationUser(String email) {
        StationUserEntity stationUser = stationUserService.findByEmail(email);
        stationUser.setIsVerification(true);
        stationUserRepository.save(stationUser);
    }

    public boolean backupDB()
            throws IOException, InterruptedException {
        String projectPath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        String newBackupFile = projectPath + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "db" + fileSeparator + "backups" + fileSeparator + "backup.sql";
        File outputFile = new File(newBackupFile);
        String sqlCommand = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);
        Process process = Runtime.getRuntime().exec(sqlCommand);
        int result = process.waitFor();
        return result == 0;
    }

    public boolean restoreDB()
            throws IOException, InterruptedException {
        String projectPath = System.getProperty("user.dir");
        String fileSeparator = System.getProperty("file.separator");
        String sourceFile = projectPath + fileSeparator + "src" + fileSeparator + "main" + fileSeparator + "resources" + fileSeparator + "db" + fileSeparator + "backups" + fileSeparator + "backup.sql";
        String sqlCommandRestore = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql -u%s -p%s -e source %s",
                dbUsername, dbPassword, sourceFile);
        Process process = Runtime.getRuntime().exec(sqlCommandRestore);
        int result = process.waitFor();
        return result == 0;
    }
}
