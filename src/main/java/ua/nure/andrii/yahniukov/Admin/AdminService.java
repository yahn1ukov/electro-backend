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
        File outputFile = new File("C:\\Users\\andri\\OneDrive\\Desktop\\Andrii\\University\\3_course\\2_semester\\APZ\\electro\\electrobackend\\src\\main\\resources\\db\\backups", "backup.sql");
        String sqlCommandBackup = String.format("C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump -u%s -p%s --add-drop-table --databases %s -r %s",
                dbUsername, dbPassword, dbName, outputFile);
        Process process = Runtime.getRuntime().exec(sqlCommandBackup);
        int processComplete = process.waitFor();
        return processComplete == 0;
    }

    public boolean restoreDB()
            throws IOException, InterruptedException {
        String sourceFile = "C:\\Users\\andri\\OneDrive\\Desktop\\Andrii\\University\\3_course\\2_semester\\APZ\\electro\\electrobackend\\src\\main\\resources\\db\\backups\\backup.sql";
        String[] sqlCommandRestore = new String[]{
                "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysql",
                "-u" + dbUsername,
                "-p" + dbPassword,
                "-e",
                " source " + sourceFile,
                dbName
        };
        Process runtimeProcess = Runtime.getRuntime().exec(sqlCommandRestore);
        int processComplete = runtimeProcess.waitFor();
        return processComplete == 0;
    }
}
