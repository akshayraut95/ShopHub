package com.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.model.Product;
import com.ex.repository.ProductRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	
	@PostMapping("/save")
	public Product addProduct(@RequestBody Product product) {
		
		return repository.save(product);
	}
	
	@GetMapping("/getallproducts")
	public List<Product> getAllProducts() {
		
		return repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Product getProductById(@PathVariable Long id) {
		return repository.findById(id).orElse(null);
	}
	
}
