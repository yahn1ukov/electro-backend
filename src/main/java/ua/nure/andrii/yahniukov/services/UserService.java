package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.NoVerificationPartnerDto;
import ua.nure.andrii.yahniukov.models.dto.PartnerDto;
import ua.nure.andrii.yahniukov.models.dto.RoleDto;
import ua.nure.andrii.yahniukov.models.dto.UserDto;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.ChargerUserRepository;
import ua.nure.andrii.yahniukov.repositories.StationUserRepository;
import ua.nure.andrii.yahniukov.repositories.UserRepository;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterUserDto;

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

    /*
     * Для власників електромобілів: реєстрація через електронну пошту
     */
    public void createUser(RegisterUserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }
        userRepository.save(
                UserEntity.builder()
                        .email(user.getEmail())
                        .fName(user.getFName())
                        .lName(user.getLName())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .build()
        );
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
     * Для адміністраторів: список усіх користувачів
     */
    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх користувачів зарядних станцій
     */
    public List<PartnerDto> getAllChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .map(chargerUser -> chargerUser.getIsVerification() ? PartnerDto.fromChargerUser(chargerUser) : null)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх користувачів СТО
     */
    public List<PartnerDto> getAllStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .map(stationUser -> stationUser.getIsVerification() ? PartnerDto.fromStationUser(stationUser) : null)
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів зарядних станцій
     */
    public List<NoVerificationPartnerDto> getAllNoVerificationChargerUsers() {
        return chargerUserRepository
                .findAll()
                .stream()
                .map(chargerUser -> !chargerUser.getIsVerification()
                        ? NoVerificationPartnerDto.fromChargerUser(chargerUser)
                        : null
                )
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: список усіх не верифікованих користувачів СТО
     */
    public List<NoVerificationPartnerDto> getAllNoVerificationStationUsers() {
        return stationUserRepository
                .findAll()
                .stream()
                .map(stationUser -> !stationUser.getIsVerification()
                        ? NoVerificationPartnerDto.fromStationUser(stationUser)
                        : null
                )
                .collect(Collectors.toList());
    }

    /*
     * Для адміністраторів: зміна ролі користувача
     */
    public void changeRoleUser(Long userId, RoleDto role) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id " + userId + " not found"));

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
        ChargerUserEntity chargerUser = chargerUserRepository
                .findById(chargerUserId)
                .orElseThrow(() -> new BadRequestException("Charger user with id " + chargerUserId + " not found"));
        chargerUser.setIsVerification(true);
        chargerUserRepository.save(chargerUser);
    }

    /*
     * Для адміністраторів: підтвердження верифікації користувача СТО
     */
    public void acceptVerificationStationUser(Long stationUserId) {
        StationUserEntity stationUser = stationUserRepository
                .findById(stationUserId)
                .orElseThrow(() -> new BadRequestException("Charger user with id " + stationUserId + " not found"));
        stationUser.setIsVerification(true);
        stationUserRepository.save(stationUser);
    }

    /*
     * Для адміністраторів: блокування користувача
     */
    public void blockUser(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));

        user.setIsBlock(!user.getIsBlock());
        userRepository.save(user);
    }

    /*
     * Для адміністраторів: блокування користувачів зарядних станцій
     */
    public void blockChargerUser(Long chargerUserId) {
        ChargerUserEntity chargerUser = chargerUserRepository
                .findById(chargerUserId)
                .orElseThrow(() -> new BadRequestException("Charger user with id: " + chargerUserId + " not found"));

        chargerUser.setIsBlock(!chargerUser.getIsBlock());
        chargerUserRepository.save(chargerUser);
    }

    /*
     * Для адміністраторів: блокування користувачів СТО
     */
    public void blockStationUser(Long stationUserId) {
        StationUserEntity stationUser = stationUserRepository
                .findById(stationUserId)
                .orElseThrow(() -> new BadRequestException("Station user with id: " + stationUserId + " not found"));

        stationUser.setIsBlock(!stationUser.getIsBlock());
        stationUserRepository.save(stationUser);
    }

    /*
     * Для адміністраторів: видалення користувача
     */
    public void deleteUser(Long userId) {
        UserEntity user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException("User with id: " + userId + " not found"));

        userRepository.delete(user);
    }

    /*
     * Для адміністраторів: видалення користувача зарядних станцій
     */
    public void deleteChargerUser(Long chargerUserId) {
        ChargerUserEntity chargerUser = chargerUserRepository
                .findById(chargerUserId)
                .orElseThrow(() -> new BadRequestException("Charger user with id: " + chargerUserId + " not found"));

        chargerUserRepository.delete(chargerUser);
    }

    /*
     * Для адміністраторів: видалення користувача СТО
     */
    public void deleteStationUser(Long stationUserId) {
        StationUserEntity stationUser = stationUserRepository
                .findById(stationUserId)
                .orElseThrow(() -> new BadRequestException("Station user with id: " + stationUserId + " not found"));

        stationUserRepository.delete(stationUser);
    }
}