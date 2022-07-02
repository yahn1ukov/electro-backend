package ua.nure.andrii.yahniukov.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.dto.message.SuccessMessageDto;
import ua.nure.andrii.yahniukov.dto.user.FormRoleDto;
import ua.nure.andrii.yahniukov.dto.user.UserDto;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exception.user.UserAlreadyExistsException;
import ua.nure.andrii.yahniukov.exception.user.UserNotFoundException;
import ua.nure.andrii.yahniukov.security.dto.registration.RegistrationRequestUserDto;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserEntity findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public UserEntity findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    public SuccessMessageDto create(RegistrationRequestUserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }
        if (userRepository.findAll().isEmpty()) {
            userRepository.save(
                    UserEntity.builder()
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .role(UserRole.ADMIN)
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
            );
        } else {
            userRepository.save(
                    UserEntity.builder()
                            .email(user.getEmail())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
            );
        }
        return SuccessMessageDto.builder().message("User successfully created").build();
    }

    public UserDto get(Long id) {
        return UserDto.fromUser(findById(id));
    }

    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public void block(Long id) {
        UserEntity user = findById(id);
        user.setIsNotBlock(!user.getIsNotBlock());
        userRepository.save(user);
    }

    public void changeRole(Long id, FormRoleDto role) {
        UserEntity user = findById(id);
        user.setRole(role.getRole());
        userRepository.save(user);
    }
}