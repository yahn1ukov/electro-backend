package ua.nure.andrii.yahniukov.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.BadRequestException;
import ua.nure.andrii.yahniukov.models.entities.UserEntity;
import ua.nure.andrii.yahniukov.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserEntity getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("User with id: " + id + " not found"));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(UserEntity user) {
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
                        .isBlock(false)
                        .cars(new ArrayList<>(Collections.emptyList()))
                        .isVerification(user.getIsVerification())
                        .createdAt(new Date())
                        .updatedAt(new Date())
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

    public void updateUserRoleById(Long id, String role) {
        UserEntity user = userRepository
                .findById(id)
                .orElseThrow(() -> new BadRequestException("User with id: " + id + " not found"));
        if (user.getRole().equals(UserRole.valueOf(role.toUpperCase()))) {
            throw new BadRequestException("Can't set the same role");
        }
        if (user.getRole().equals(UserRole.ADMIN)) {
            throw new BadRequestException("Can't change admin role");
        }
        user.setRole(UserRole.valueOf(role.toUpperCase()));
        userRepository.save(user);
    }
}