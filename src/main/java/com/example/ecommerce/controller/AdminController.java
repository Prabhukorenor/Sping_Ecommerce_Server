package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.enums.Role;
import com.example.ecommerce.enums.SellerStatus;
import com.example.ecommerce.repository.UserRespository;

@RestController
public class AdminController 
{
	private final UserRespository userRespository;

	public AdminController(UserRespository userRespository) {
		super();
		this.userRespository = userRespository;
	}
	
	@PostMapping("/approve/{id}")
	public ResponseEntity<String> approveSeller(@PathVariable Long id)
	{
		User user=userRespository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
		if(user.getSellerStatus()==SellerStatus.APPROVED)
		{
			return ResponseEntity.badRequest().body("Seller already approve");
		}
		user.setRole(Role.SELLER);
		user.setSellerStatus(SellerStatus.APPROVED);
		userRespository.save(user);
		
		return ResponseEntity.ok("Seller role approved successfully.....");
	}
}
