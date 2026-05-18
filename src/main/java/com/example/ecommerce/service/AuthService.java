package com.example.ecommerce.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce.config.JwtUtil;
import com.example.ecommerce.dto.JwtResponse;
import com.example.ecommerce.dto.LoginRequest;
import com.example.ecommerce.dto.RegisterRequest;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.enums.SellerStatus;
import com.example.ecommerce.repository.UserRespository;

@Service
public class AuthService 
{
	private final UserRespository  userRespository;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;
	
	public String register(RegisterRequest request)
	{
		if(userRespository.findByEmail(request.getEmail()).isPresent())
		{
			throw new RuntimeException("Email already registered");
		}
		
		User user=new User();
		user.setEmail(request.getEmail());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(Role.USER);
		user.setSellerStatus(SellerStatus.NONE);
		
		userRespository.save(user);
		return "Registration Successfully";
		
		
	}

	public JwtResponse login(LoginRequest request)
	{
		User user =userRespository.findByEmail(request.getEmail()).orElseThrow(()->new RuntimeException("Invalid Credential"));
		if(!encoder.matches(request.getPassword(),user.getPassword())) {
			throw new RuntimeException("Invalid Credentail");
		}
		String token=jwtUtil.generateToken(user.getEmail(),user.getRole());
		return new JwtResponse(token,user.getRole());
	}
	public AuthService(UserRespository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
		super();
		this.userRespository = userRepository;
		this.encoder = encoder;
		this.jwtUtil = jwtUtil;
	}
}
