package ua.nure.andrii.yahniukov.ChargerUser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserDto;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserNoVerificationDto;
import ua.nure.andrii.yahniukov.ChargerUser.dto.ChargerUserVerificationDto;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterPartnerDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerUserService {
    private final ChargerUserRepository chargerUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ChargerUserEntity findByEmail(String email) {
        return chargerUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Charger user doesn't exists"));
    }

    public void create(RegisterPartnerDto partner) {
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

    public ChargerUserDto getByEmail(String email) {
        return ChargerUserDto.fromPartner(findByEmail(email));
    }

    public List<ChargerUserVerificationDto> getAll() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(ChargerUserVerificationDto::verification)
                .map(ChargerUserVerificationDto::fromVerification)
                .toList();
    }

    public List<ChargerUserNoVerificationDto> getAllNoVerification() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(ChargerUserNoVerificationDto::noVerification)
                .map(ChargerUserNoVerificationDto::fromNoVerification)
                .toList();
    }

    public void deleteByEmail(String email) {
        chargerUserRepository.deleteByEmail(email);
    }

    public void blockByEmail(String email) {
        ChargerUserEntity chargerUser = findByEmail(email);
        chargerUser.setIsNotBlock(!chargerUser.getIsNotBlock());
        chargerUserRepository.save(chargerUser);
    }
}
