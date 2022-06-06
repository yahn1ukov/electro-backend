package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.dto.RoleDto;
import ua.nure.andrii.yahniukov.models.dto.UserDto;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;
import ua.nure.andrii.yahniukov.repositories.UserRepository;
import ua.nure.andrii.yahniukov.security.dto.RegisterDto;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserDto getUserById(Long id) {
        return UserDto.fromUser(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new BadRequestException("User with id: " + id + " not found"))
        );
    }

    public List<UserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserDto::fromUser)
                .collect(Collectors.toList());
    }

    public void createUser(RegisterDto user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists");
        }
        userRepository.save(
                UserEntity.builder()
                        .fName(user.getFName())
                        .lName(user.getLName())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .isVerification(user.getIsVerification())
                        .build()
        );
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new BadRequestException("User with id: " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    public void blockUserById(Long id) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("User with id: " + id + " not found"));
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new BadRequestException("User with role: " + user.getRole() + " cannot be banned");
        }
        user.setIsBlock(!user.getIsBlock());
        userRepository.save(user);
    }

    public void updateUserRoleById(Long id, RoleDto role) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("User with id: " + id + " not found"));
        if (user.getRole().equals(role.getRole())) {
            throw new BadRequestException("Can't set the same role");
        }
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new BadRequestException("Can't change admin role");
        }
        user.setRole(role.getRole());
        userRepository.save(user);
    }
}