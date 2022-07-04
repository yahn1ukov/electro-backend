package ua.nure.andrii.yahniukov.stationUser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.dto.stationUser.StationUserDto;
import ua.nure.andrii.yahniukov.exception.user.UserAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.user.UserNotFoundException;
import ua.nure.andrii.yahniukov.security.dto.registration.RegistrationRequestPartnerDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationUserService {
    private final StationUserRepository stationUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email) {
        return stationUserRepository.existsByEmail(email);
    }

    public StationUserEntity findById(Long id) {
        return stationUserRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public StationUserEntity findByEmail(String email) {
        return stationUserRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public SuccessMessageDto create(RegistrationRequestPartnerDto partner) {
        if (stationUserRepository.existsByEmail(partner.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        stationUserRepository.save(
                StationUserEntity.builder()
                        .email(partner.getEmail())
                        .company(partner.getCompany())
                        .password(passwordEncoder.encode(partner.getPassword()))
                        .build()
        );
        return SuccessMessageDto.builder().message("Application for cooperation successfully submitted").build();
    }

    public StationUserDto get(Long id) {
        return StationUserDto.fromStationUser(findById(id));
    }

    public List<StationUserDto> getAll() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(StationUserDto::isVerification)
                .map(StationUserDto::fromStationUser)
                .toList();
    }

    public List<StationUserDto> getAllNoVerification() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(StationUserDto::isNotVerification)
                .map(StationUserDto::fromStationUser)
                .toList();
    }

    public void delete(Long id) {
        stationUserRepository.deleteById(id);
    }

    public void block(Long id) {
        StationUserEntity stationUser = findById(id);
        stationUser.setIsNotBlock(!stationUser.getIsNotBlock());
        stationUserRepository.save(stationUser);
    }

    public void accept(Long id) {
        StationUserEntity stationUser = findById(id);
        stationUser.setIsVerification(true);
        stationUserRepository.save(stationUser);
    }
}