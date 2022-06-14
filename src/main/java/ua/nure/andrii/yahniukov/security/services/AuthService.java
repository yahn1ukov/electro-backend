package ua.nure.andrii.yahniukov.security.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserRepository;
import ua.nure.andrii.yahniukov.ChargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.StationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.StationUser.StationUserService;
import ua.nure.andrii.yahniukov.User.UserEntity;
import ua.nure.andrii.yahniukov.User.UserRepository;
import ua.nure.andrii.yahniukov.User.UserService;
import ua.nure.andrii.yahniukov.security.models.dto.LoginDto;
import ua.nure.andrii.yahniukov.security.providers.JwtTokenProvider;

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
    private final ChargerUserService chargerUserService;
    private final ChargerUserRepository chargerUserRepository;
    private final StationUserService stationUserService;

    public Map<Object, Object> login(LoginDto loginUser) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        if (userRepository.existsByEmail(loginUser.getEmail())) {
            UserEntity user = userService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), user.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", user.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            response.put("role", user.getRole().name());
            return response;
        } else if (chargerUserRepository.existsByEmail(loginUser.getEmail())) {
            ChargerUserEntity chargerUser = chargerUserService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), chargerUser.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", chargerUser.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            response.put("role", chargerUser.getRole().name());
            return response;
        } else {
            StationUserEntity stationUser = stationUserService.findByEmail(loginUser.getEmail());
            String token = jwtTokenProvider.createToken(loginUser.getEmail(), stationUser.getRole().name());
            Map<Object, Object> response = new HashMap<>();
            response.put("id", stationUser.getId());
            response.put("email", loginUser.getEmail());
            response.put("token", token);
            response.put("role", stationUser.getRole().name());
            return response;
        }

    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }
}
