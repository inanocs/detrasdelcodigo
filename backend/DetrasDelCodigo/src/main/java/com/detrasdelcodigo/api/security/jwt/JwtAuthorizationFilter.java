package com.detrasdelcodigo.api.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.detrasdelcodigo.api.model.Usuario;
import com.detrasdelcodigo.api.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
	
	private final JwtProvider tokenProvider;
	private final CustomUserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			
			String token = getJwtFromRequest(request);
			
			if(StringUtils.hasText(token)&&tokenProvider.validateToken(token)) {
				
				Long userId = tokenProvider.getUserIdFromJWT(token);
				
				Usuario user = (Usuario) userDetailsService.loadUserById(userId);
				
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(user, "ROLE_"+user.getRol(), user.getAuthorities());
				
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
		}catch(Exception e) {
			
			log.info("No se ha podido establecer la autenticacion de usuario en el contexto de seguridad");
		}
		
		filterChain.doFilter(request, response);

	}

	private String getJwtFromRequest(HttpServletRequest request) {
		
		String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
		
		if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
			
			return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length(),bearerToken.length());
		}
		
		return null;
	}

}
