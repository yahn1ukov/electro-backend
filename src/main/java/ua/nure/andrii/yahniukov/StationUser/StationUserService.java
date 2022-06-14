package ua.nure.andrii.yahniukov.StationUser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserDto;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserNoVerificationDto;
import ua.nure.andrii.yahniukov.StationUser.dto.StationUserVerificationDto;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterPartnerDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StationUserService {
    private final StationUserRepository stationUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public StationUserEntity findByEmail(String email) {
        return stationUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Station user doesn't exists"));
    }

    public void create(RegisterPartnerDto partner) {
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

    public StationUserDto getByEmail(String email) {
        return StationUserDto.fromPartner(findByEmail(email));
    }

    public List<StationUserVerificationDto> getAll() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(StationUserVerificationDto::verification)
                .map(StationUserVerificationDto::fromVerification)
                .toList();
    }

    public List<StationUserNoVerificationDto> getAllNoVerification() {
        return stationUserRepository
                .findAll()
                .stream()
                .filter(StationUserNoVerificationDto::noVerification)
                .map(StationUserNoVerificationDto::fromNoVerification)
                .toList();
    }

    public void deleteByEmail(String email) {
        stationUserRepository.deleteByEmail(email);
    }

    public void blockByEmail(String email) {
        StationUserEntity stationUser = findByEmail(email);
        stationUser.setIsNotBlock(!stationUser.getIsNotBlock());
        stationUserRepository.save(stationUser);
    }
}
