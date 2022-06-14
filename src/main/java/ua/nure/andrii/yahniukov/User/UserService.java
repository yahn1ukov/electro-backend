package ua.nure.andrii.yahniukov.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.User.dto.UserDto;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.security.models.dto.RegisterUserDto;

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
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }

    public UserEntity findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User doesn't exists"));
    }

    public void create(RegisterUserDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
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
    }

    public UserDto getByEmail(String email) {
        return UserDto.fromUser(findByEmail(email));
    }

    public List<UserDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .toList();
    }

    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    public void blockByEmail(String email) {
        UserEntity user = findByEmail(email);
        user.setIsNotBlock(!user.getIsNotBlock());
        userRepository.save(user);
    }
}