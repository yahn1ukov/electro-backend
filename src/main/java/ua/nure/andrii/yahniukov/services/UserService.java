package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.forms.FormRoleDto;
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

    public StationUserEntity findStationUserById(Long stationUserId) {
        return stationUserRepository
                .findById(stationUserId)
                .orElseThrow(() -> new BadRequestException("Station user with id " + stationUserId + " not found"));
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
    public List<PartnerDto> getAllChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(PartnerDto::verification)
                .map(PartnerDto::fromVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх користувачів СТО
     */
    public List<PartnerDto> getAllStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(PartnerDto::verification)
                .map(PartnerDto::fromVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів зарядних станцій
     */
    public List<PartnerDto> getAllNoVerificationChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(PartnerDto::noVerification)
                .map(PartnerDto::fromNoVerification)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів СТО
     */
    public List<PartnerDto> getAllNoVerificationStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(PartnerDto::noVerification)
                .map(PartnerDto::fromNoVerification)
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
}