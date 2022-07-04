package ua.nure.andrii.yahniukov.security.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserEntity;
import ua.nure.andrii.yahniukov.chargerUser.ChargerUserService;
import ua.nure.andrii.yahniukov.exception.user.UnauthorizedException;
import ua.nure.andrii.yahniukov.security.provider.JwtTokenProvider;
import ua.nure.andrii.yahniukov.stationUser.StationUserEntity;
import ua.nure.andrii.yahniukov.stationUser.StationUserService;
import ua.nure.andrii.yahniukov.user.UserEntity;
import ua.nure.andrii.yahniukov.user.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final ChargerUserService chargerUserService;
    private final StationUserService stationUserService;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        try {
            if (token != null && jwtTokenProvider.validateToken(token) && token.startsWith(tokenPrefix)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    if (userService.existsByEmail(authentication.getName())) {
                        UserEntity user = userService.findByEmail(authentication.getName());
                        request.setAttribute("userId", user.getId());
                    } else if (chargerUserService.existsByEmail(authentication.getName())) {
                        ChargerUserEntity chargerUser = chargerUserService.findByEmail(authentication.getName());
                        request.setAttribute("userId", chargerUser.getId());
                    } else {
                        StationUserEntity stationUser = stationUserService.findByEmail(authentication.getName());
                        request.setAttribute("userId", stationUser.getId());
                    }
                }
            }
        } catch (UnauthorizedException e) {
            SecurityContextHolder.clearContext();
            throw new UnauthorizedException();
        }
        filterChain.doFilter(request, response);
    }
}