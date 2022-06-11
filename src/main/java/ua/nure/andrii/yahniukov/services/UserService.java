package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormRoleDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.PartnerNoVerificationDto;
import ua.nure.andrii.yahniukov.models.dto.helpers.PartnerVerificationDto;
import ua.nure.andrii.yahniukov.models.dto.users.PartnerDto;
import ua.nure.andrii.yahniukov.models.dto.users.UserDto;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.users.ChargerUserRepository;
import ua.nure.andrii.yahniukov.repositories.users.StationUserRepository;
import ua.nure.andrii.yahniukov.repositories.users.UserRepository;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterUserDto;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserRepository stationUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${db.name}")
    private String dbName;
    @Value("${spring.datasource.username}")
    private String dbUsername;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    @Value("${db.file}")
    private String dbFile;

    public UserEntity findUserById(Long userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id " + userId + " not found"));
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }

    public ChargerUserEntity findChargerUserById(Long chargerUserId) {
        return chargerUserRepository
                .findById(chargerUserId)
                .orElseThrow(() -> new BadRequestException("Charger user with id " + chargerUserId + " not found"));
    }

    public ChargerUserEntity findChargerUserByEmail(String email) {
        return chargerUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Charger user doesn't exists"));
    }

    public StationUserEntity findStationUserById(Long stationUserId) {
        return stationUserRepository
                .findById(stationUserId)
                .orElseThrow(() -> new BadRequestException("Station user with id " + stationUserId + " not found"));
    }

    public StationUserEntity findStationUserByEmail(String email) {
        return stationUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Station user doesn't exists"));
    }

    /*
     * Для власників електромобілів: реєстрація через електронну пошту
     */
    public void createUser(RegisterUserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(
                    UserEntity.builder()
                            .email(user.getEmail())
                            .fName(user.getFName())
                            .lName(user.getLName())
                            .role(UserRole.ADMIN)
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
            );
        } else {
            userRepository.save(
                    UserEntity.builder()
                            .email(user.getEmail())
                            .fName(user.getFName())
                            .lName(user.getLName())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
            );
        }
    }

    /*
     * Для виробників зарядних станцій: подання та реєстрація через електронну пошту
     */
    public void createChargerUser(RegisterPartnerDto partner) {
        if (chargerUserRepository.existsByEmail(partner.getEmail())) {
            throw new BadRequestException("User with email " + partner.getEmail() + " already exists");
        }
        chargerUserRepository.save(
                ChargerUserEntity.builder()
                        .email(partner.getEmail())
                        .company(partner.getCompany())
                        .password(passwordEncoder.encode(partner.getPassword()))
                        .build()
        );
    }

    /*
     * Для власників СТО: подання та реєстрація через електронну пошту
     */
    public void createStationUser(RegisterPartnerDto partner) {
        if (stationUserRepository.existsByEmail(partner.getEmail())) {
            throw new BadRequestException("User with email " + partner.getEmail() + " already exists");
        }
        stationUserRepository.save(
                StationUserEntity.builder()
                        .email(partner.getEmail())
                        .company(partner.getCompany())
                        .password(passwordEncoder.encode(partner.getPassword()))
                        .build()
        );
    }

    /*
     * Для власників електромобілів: отримання даних про користувача
     */
    public UserDto getUserById(Long userId) {
        return UserDto.fromUserForUser(findUserById(userId));
    }

    /*
     * Для виробників зарядних станцій: отримання даних про користувача зарядних станцій
     */
    public PartnerDto getChargerUserById(Long chargerUserId) {
        return PartnerDto.fromPartner(findChargerUserById(chargerUserId));
    }

    /*
     * Для власників СТО: отримання даних про користувача СТО
     */
    public PartnerDto getStationUserById(Long stationUserId) {
        return PartnerDto.fromPartner(findStationUserById(stationUserId));
    }

    /*
     * Для адміністраторів: список усіх користувачів
     */
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUserForAdmin)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх користувачів зарядних станцій
     */
    public List<PartnerVerificationDto> getAllChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(PartnerVerificationDto::verification)
                .map(PartnerVerificationDto::fromVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх користувачів СТО
     */
    public List<PartnerVerificationDto> getAllStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(PartnerVerificationDto::verification)
                .map(PartnerVerificationDto::fromVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів зарядних станцій
     */
    public List<PartnerNoVerificationDto> getAllNoVerificationChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(PartnerNoVerificationDto::noVerification)
                .map(PartnerNoVerificationDto::fromNoVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів СТО
     */
    public List<PartnerNoVerificationDto> getAllNoVerificationStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(PartnerNoVerificationDto::noVerification)
                .map(PartnerNoVerificationDto::fromNoVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: зміна ролі користувача
     */
    public void changeRoleUser(Long userId, FormRoleDto role) {
        UserEntity user = findUserById(userId);
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new BadRequestException("Administrator cannot change role");
        }
        if (user.getRole().equals(role.getRole())) {
            throw new BadRequestException("User with id: " + userId + " cannot have the same role");
        }
        if (role.getRole().equals(UserRole.SERVICE) || role.getRole().equals(UserRole.CHARGER)) {
            throw new BadRequestException("Can't set partner role");
        }
        user.setRole(role.getRole());
        userRepository.save(user);
    }

    /*
     * Для адміністраторів: підтвердження верифікації користувача зарядних станцій
     */
    public void acceptVerificationChargerUser(Long chargerUserId) {
        ChargerUserEntity chargerUser = findChargerUserById(chargerUserId);
        chargerUser.setIsVerification(true);
        chargerUserRepository.save(chargerUser);
    }

    /*
     * Для адміністраторів: підтвердження верифікації користувача СТО
     */
    public void acceptVerificationStationUser(Long stationUserId) {
        StationUserEntity stationUser = findStationUserById(stationUserId);
        stationUser.setIsVerification(true);
        stationUserRepository.save(stationUser);
    }

    /*
     * Для адміністраторів: блокування користувача
     */
    public void blockUser(Long userId) {
        UserEntity user = findUserById(userId);
        user.setIsBlock(!user.getIsBlock());
        userRepository.save(user);
    }

    /*
     * Для адміністраторів: блокування користувачів зарядних станцій
     */
    public void blockChargerUser(Long chargerUserId) {
        ChargerUserEntity chargerUser = findChargerUserById(chargerUserId);
        chargerUser.setIsBlock(!chargerUser.getIsBlock());
        chargerUserRepository.save(chargerUser);
    }

    /*
     * Для адміністраторів: блокування користувачів СТО
     */
    public void blockStationUser(Long stationUserId) {
        StationUserEntity stationUser = findStationUserById(stationUserId);
        stationUser.setIsBlock(!stationUser.getIsBlock());
        stationUserRepository.save(stationUser);
    }

    /*
     * Для адміністраторів: видалення користувача
     */
    public void deleteUser(Long userId) {
        UserEntity user = findUserById(userId);
        userRepository.delete(user);
    }

    /*
     * Для адміністраторів: видалення користувача зарядних станцій
     */
    public void deleteChargerUser(Long chargerUserId) {
        ChargerUserEntity chargerUser = findChargerUserById(chargerUserId);
        chargerUserRepository.delete(chargerUser);
    }

    /*
     * Для адміністраторів: видалення користувача СТО
     */
    public void deleteStationUser(Long stationUserId) {
        StationUserEntity stationUser = findStationUserById(stationUserId);
        stationUserRepository.delete(stationUser);
    }

    /*
     * Для адміністраторів: створення backup бази даних
     */
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

    /*
     * Для адміністраторів: відновлення бази даних
     */
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