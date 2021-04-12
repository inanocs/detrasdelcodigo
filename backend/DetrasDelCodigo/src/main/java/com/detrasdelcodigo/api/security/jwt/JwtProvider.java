package com.detrasdelcodigo.api.security.jwt;


import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.detrasdelcodigo.api.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.java.Log;

@Log
@Component
public class JwtProvider {

	@Autowired
	private Environment env;

	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";

	private String jwtSecreto;

	private int jwtDurationTokenSegs;

	@PostConstruct
	public void init() {

		jwtSecreto = env.getProperty("jwt.secret");
		jwtDurationTokenSegs = Integer.parseInt(env.getProperty("jwt.token-expires"));
	}

	public String generateToken(Authentication authentication) {

		Usuario user = (Usuario) authentication.getPrincipal();

		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationTokenSegs * 1000));

		return Jwts.builder().signWith(Keys.hmacShaKeyFor(jwtSecreto.getBytes()), SignatureAlgorithm.HS512)
				.setHeaderParam("type", TOKEN_TYPE).setSubject(Long.toString(user.getIdusuario()))
				.setIssuedAt(new Date()).setExpiration(tokenExpirationDate).claim("username", user.getUsername())
				.claim("fullname", user.getFullname()).compact();
	}

	public Long getUserIdFromJWT(String token) {

		Claims claims = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(jwtSecreto.getBytes())).parseClaimsJws(token)
				.getBody();

		return Long.parseLong(claims.getSubject());

	}

	public boolean validateToken(String authToken) {

		try {
			Jwts.parser().setSigningKey(jwtSecreto.getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException ex) {
			log.info("Error en la firma del token JWT: " + ex.getMessage());
		} catch (MalformedJwtException ex) {
			log.info("Token malformado: " + ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.info("El token ha expirado: " + ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.info("Token JWT no soportado: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.info("JWT claims vacío");
		}
		return false;

	}
}
