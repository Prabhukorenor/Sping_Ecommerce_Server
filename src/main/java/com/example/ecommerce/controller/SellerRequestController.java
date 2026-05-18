package com.example.ecommerce.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.SellerStatus;
import com.example.ecommerce.repository.UserRespository;
import com.example.ecommerce.service.AuthService;

@RestController
@RequestMapping("/api/seller")
public class SellerRequestController {
	private final UserRespository userRespository;
	
	@PostMapping("/request")
	public String requestSeller(Authentication auth)
	{
		String email=auth.getName();
		User user=userRespository.findByEmail(email).orElseThrow(null);
		user.setSellerStatus(SellerStatus.REQUESTED);
		userRespository.save(user);
		
		return "Seller request sent to admin";
	}

	public SellerRequestController(UserRespository userRespository) {
		super();
		this.userRespository = userRespository;
	}
	
	
	
}
