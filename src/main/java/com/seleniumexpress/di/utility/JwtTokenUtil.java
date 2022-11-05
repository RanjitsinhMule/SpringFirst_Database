package com.seleniumexpress.di.utility;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;
import com.seleniumexpress.di.dto.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable {

//	@Autowired
//	IUserRepository userRepo;
	LoginDto loginDTO = new LoginDto();
	// @Value("${jwt.secret}")
	private String secret = "spring";

	public String generateToken(LoginDto loginDTO) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("Email", loginDTO.getEmail());
		claims.put("Password", loginDTO.getPassword()); // payload

		System.out.println("claims : " + claims);
		// System.out.println("Generated Token for : " );
		return doGenerateToken(claims);
	}

	private String doGenerateToken(Map<String, Object> claims) {

		return Jwts.builder().setClaims(claims)
				// .setIssuedAt(new Date(System.currentTimeMillis()))
				// .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY *
				// 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public LoginDto deCode(String token) {

		final Map<String, Object> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		loginDTO.setEmail((String) claims.get("Email"));
		loginDTO.setPassword((String) claims.get("Password"));
		return loginDTO;
		

	}

	public String generateToken(String email, String password) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("Email", loginDTO.getEmail());
//		claims.put("Password", loginDTO.getPassword()); //payload
		Map<String, Object> claims = new HashMap<>();
		claims.put("Email", email);
		claims.put("Password", password);
		System.out.println("claims : " + claims);
		System.out.println("Generated Token for : ");
		return doGenerateToken(claims);
	}
}