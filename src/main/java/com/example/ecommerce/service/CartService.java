package com.example.ecommerce.service;

import java.util.ArrayList;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.ecommerce.entity.Cart;
import com.example.ecommerce.entity.CartItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.CartRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRespository;

@Service
public class CartService {
	private final UserRespository userRespository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;
	
	public CartService(UserRespository userRespository, ProductRepository productRepository,
			CartRepository cartRepository) {
		super();
		this.userRespository = userRespository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
	}
	
	private User getLoggedInUser(Authentication auth)
	{
		return userRespository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
	}
	
	public Cart getCart(Authentication auth)
	{
		User user=getLoggedInUser(auth);
		return cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("Cart empty"));
	}
	
	public void addToCart(Long productid,Authentication auth)
	{
		String email=auth.getName();
		User user=userRespository.findByEmail(auth.getName()).orElseThrow(()->new RuntimeException("User not found"));
		Cart cart=cartRepository.findByUser(user).orElseGet(()->{
			Cart newCart=new Cart();
			newCart.setUser(user);
    		newCart.setItems(new ArrayList<>());
    		return cartRepository.save(newCart);
		});
		
		 Product product = productRepository.findById(productid).orElseThrow(()->new RuntimeException("Product not found"));
	     CartItem item=cart.getItems().stream()
	    		 .filter(i->i.getProduct().getId().equals(productid))
	    		 .findFirst()
	         .orElse(null); 
	     
	     if(item!=null)
	     {
	    	 item.setQuantity(item.getQuantity()+1);
	    	 
	     }
	     else
	     {
	    	 CartItem newItem=new CartItem();
	    	 newItem.setCart(cart);
	    	 newItem.setProduct(product);
	    	 newItem.setQuantity(1);
	    	 cart.getItems().add(newItem);
	     }
	     cartRepository.save(cart);
	}
	
	public Cart increaseQty(Long productid,Authentication auth)
	{
		User user=getLoggedInUser(auth);
		Cart cart=cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("Cart not found"));
		CartItem item=cart.getItems().stream()
				.filter(i->i.getProduct().getId().equals(productid))
				.findFirst()
				.orElseThrow(()->new RuntimeException("Product not in cart"));
		
		item.setQuantity(item.getQuantity()+1);
		return cartRepository.save(cart);
	}
	
	public Cart decreaseQty(Long productid,Authentication auth)
	{
		User user=getLoggedInUser(auth);
		Cart cart=cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("Cart not found"));
		CartItem item=cart.getItems().stream()
				.filter(i->i.getProduct().getId().equals(productid))
				.findFirst()
				.orElseThrow(()->new RuntimeException("Product not in cart"));
		
		if(item.getQuantity()>1)
		{
			item.setQuantity(item.getQuantity()-1);
		}
		else {
			cart.getItems().remove(item);
		}
		return cartRepository.save(cart);
	}
	
	public Cart removeItem(Long productId,Authentication auth)
	{
		User user=getLoggedInUser(auth);
		Cart cart=cartRepository.findByUser(user).orElseThrow(()->new RuntimeException("Cart not found"));
		cart.getItems().removeIf(i->i.getProduct().getId().equals(productId));
		return cartRepository.save(cart);
	}
	
}
