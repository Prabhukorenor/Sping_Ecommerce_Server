package com.example.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.service.CartService;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {
	
	private final CartService cartService;
	
	//add to cart
	@PostMapping("/add/{productid}")
	@PostAuthorize("hasRole('USER')")
	public ResponseEntity<?> addtocart(@PathVariable Long productid,Authentication auth)
	{
		cartService.addToCart(productid, auth);
		return ResponseEntity.ok("Product added to cart");
	}
	
	//delete to cart
	@DeleteMapping("/remove/{productid}")
	public ResponseEntity<Cart> removecart(@PathVariable Long productid,Authentication auth)
	{
		return ResponseEntity.ok(cartService.removeItem(productid, auth));
	}
	
	//increaQty
	@PutMapping("/increase/{productid}")
	public ResponseEntity<Cart> increase(@PathVariable Long productid,Authentication auth)
	{
		return ResponseEntity.ok(cartService.increaseQty(productid, auth));
	}
	
	//decrease
	@PutMapping("/decrease/{productid}")
	public ResponseEntity<Cart> decrease(@PathVariable Long productid,Authentication auth)
	{
		return ResponseEntity.ok(cartService.decreaseQty(productid, auth));
	}
	
	//show carts
	@GetMapping
	public ResponseEntity<Cart> view(Authentication auth)
	{
		return ResponseEntity.ok(cartService.getCart(auth));
	}
	
	public CartController(CartService cartService)
	{
		super();
		this.cartService=cartService;
	}
}