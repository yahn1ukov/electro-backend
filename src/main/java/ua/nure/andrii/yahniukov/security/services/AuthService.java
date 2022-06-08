package ua.nure.andrii.yahniukov.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.models.entities.users.ChargerUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.StationUserEntity;
import ua.nure.andrii.yahniukov.models.entities.users.UserEntity;
import ua.nure.andrii.yahniukov.repositories.users.ChargerUserRepository;
import ua.nure.andrii.yahniukov.repositories.users.UserRepository;
import ua.nure.andrii.yahniukov.security.models.dto.LoginDto;
import ua.nure.andrii.yahniukov.security.providers.JwtTokenProvider;
import ua.nure.andrii.yahniukov.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChargerUserRepository chargerUserRepository;

    public Map<Object, Object> login(LoginDto loginUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        if (userRepository.existsByEmail(loginUser.getEmail())) {
            UserEntity user = userService.findUserByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            return response;
        } else if (chargerUserRepository.existsByEmail(loginUser.getEmail())) {
            ChargerUserEntity chargerUser = userService.findChargerUserByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), chargerUser.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", chargerUser.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            return response;
        } else {
            StationUserEntity stationUser = userService.findStationUserByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), stationUser.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", stationUser.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            return response;
        }

    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
