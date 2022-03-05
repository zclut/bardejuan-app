package es.iespuertodelacruz.juan.restaurant.security;

import io.jsonwebtoken.Claims;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FiltroJWT extends OncePerRequestFilter{

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        GestorDeJWT gestorDeJWT = GestorDeJWT.getInstance();
        String token = null;
        try {
            token = request
                    .getHeader(gestorDeJWT.AUTHORIZATIONHEADER)
                    .replace(gestorDeJWT.BEARERPREFIX, "");
            Claims claims = gestorDeJWT.getClaims(token);
            if (claims.get(gestorDeJWT.ROLSCLAIMS) != null) {
                setUpSpringAuthentication(claims);
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

    private void setUpSpringAuthentication(Claims claims) {
        GestorDeJWT gestorDeJWT = GestorDeJWT.getInstance();
        @SuppressWarnings("unchecked")
        List<String> authorities = (List)
                claims.get(gestorDeJWT.ROLSCLAIMS);

        UserDetails usuario = new User(claims.getSubject(), "1234",
                authorities.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        usuario.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}