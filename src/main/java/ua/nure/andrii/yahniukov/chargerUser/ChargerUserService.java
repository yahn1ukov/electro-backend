package ua.nure.andrii.yahniukov.chargerUser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.chargerUser.ChargerUserDto;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.exceptions.user.UserAlreadyExistsException;
import ua.nure.andrii.yahniukov.exceptions.user.UserNotFoundException;
import ua.nure.andrii.yahniukov.security.dto.registration.RegistrationRequestPartnerDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChargerUserService {
    private final ChargerUserRepository chargerUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ChargerUserEntity findById(Long id) {
        return chargerUserRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public ChargerUserEntity findByEmail(String email) {
        return chargerUserRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public SuccessMessageDto create(RegistrationRequestPartnerDto partner) {
        if (chargerUserRepository.existsByEmail(partner.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        chargerUserRepository.save(
                ChargerUserEntity.builder()
                        .email(partner.getEmail())
                        .company(partner.getCompany())
                        .password(passwordEncoder.encode(partner.getPassword()))
                        .build()
        );
        return SuccessMessageDto.builder().message("User successfully created").build();
    }

    public ChargerUserDto get(Long id) {
        return ChargerUserDto.fromChargerUser(findById(id));
    }

    public List<ChargerUserDto> getAll() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(ChargerUserDto::isVerification)
                .map(ChargerUserDto::fromChargerUser)
                .toList();
    }

    public List<ChargerUserDto> getAllNoVerification() {
        return chargerUserRepository
                .findAll()
                .stream()
                .filter(ChargerUserDto::isNotVerification)
                .map(ChargerUserDto::fromChargerUser)
                .toList();
    }

    public void delete(Long id) {
        chargerUserRepository.deleteById(id);
    }

    public void block(Long id) {
        ChargerUserEntity chargerUser = findById(id);
        chargerUser.setIsNotBlock(!chargerUser.getIsNotBlock());
        chargerUserRepository.save(chargerUser);
    }

    public void accept(Long id) {
        ChargerUserEntity chargerUser = findById(id);
        chargerUser.setIsVerification(true);
        chargerUserRepository.save(chargerUser);
    }
}