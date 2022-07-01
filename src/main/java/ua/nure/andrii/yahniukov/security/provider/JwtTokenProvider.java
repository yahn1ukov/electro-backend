package ua.nure.andrii.yahniukov.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ua.nure.andrii.yahniukov.enums.UserRole;
import ua.nure.andrii.yahniukov.exceptions.user.UnauthorizedException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final UserDetailsService userDetailsService;
    @Value("${jwt.token.header}")
    private String authorizationHeader;
    @Value("${jwt.token.secret}")
    private String tokenSecretKey;
    @Value("${jwt.token.expiration}")
    private Long tokenValidityInMilliseconds;
    @Value("jwt.token.type")
    private String tokenType;
    @Value("jwt.token.prefix")
    private String tokenPrefix;

    @PostConstruct
    protected void init() {
        tokenSecretKey = Base64.getEncoder().encodeToString(tokenSecretKey.getBytes());
    }

    public String createToken(Long id, String email, UserRole role) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("id", id);
        claims.put("role", role.name());

        Date now = new Date();
        Date validity = new Date(now.getTime() + tokenValidityInMilliseconds * 1000);

        return String.format("%s %s",
                tokenPrefix,
                Jwts.builder()
                        .signWith(SignatureAlgorithm.HS256, tokenSecretKey)
                        .setHeaderParam("type", tokenType)
                        .setClaims(claims)
                        .setIssuedAt(now)
                        .setExpiration(validity)
                        .compact()
        );
    }

    public boolean validateToken(String token) throws UnauthorizedException {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(tokenSecretKey)
                .parseClaimsJws(token.replace(tokenPrefix, ""));
        return !claimsJws.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token.replace(tokenPrefix, "")));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts
                .parser()
                .setSigningKey(tokenSecretKey)
                .parseClaimsJws(token.replace(tokenPrefix, ""))
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(authorizationHeader);
    }
}