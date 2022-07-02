package ua.nure.andrii.yahniukov.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserRepository;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.security.dto.login.LoginRequestDto;
import ua.nure.andrii.yahniukov.security.dto.login.LoginResponseDto;
import ua.nure.andrii.yahniukov.security.provider.JwtTokenProvider;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserRepository;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChargerUserService chargerUserService;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserService stationUserService;

    public LoginResponseDto login(LoginRequestDto loginUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        if (userRepository.existsByEmail(loginUser.getEmail())) {
            UserEntity user = userService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(user.getId(), user.getEmail());
            return LoginResponseDto.builder()
                    .id(user.getId())
                    .token(token)
                    .role(user.getRole())
                    .build();
        } else if (chargerUserRepository.existsByEmail(loginUser.getEmail())) {
            ChargerUserEntity chargerUser = chargerUserService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(chargerUser.getId(), chargerUser.getEmail());
            return LoginResponseDto.builder()
                    .id(chargerUser.getId())
                    .token(token)
                    .role(chargerUser.getRole())
                    .build();
        } else {
            StationUserEntity stationUser = stationUserService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(stationUser.getId(), stationUser.getEmail());
            return LoginResponseDto.builder()
                    .id(stationUser.getId())
                    .token(token)
                    .role(stationUser.getRole())
                    .build();
        }
    }
}