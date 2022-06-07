package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.ChargerUserRepository;
import ua.nure.andrii.yahniukov.repositories.StationUserRepository;
import ua.nure.andrii.yahniukov.repositories.UserRepository;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterPartnerDto;
import ua.nure.andrii.yahniukov.security.dto.register.RegisterUserDto;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserRepository stationUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /*
     * Для власників електромобілів: реєстрація через електронну пошту;
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
     * Для виробників зарядних станцій: подання та реєстрація через електронну пошту;
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
     * Для власників СТО: подання та реєстрація через електронну пошту;
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


}