package com.ex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.dto.OrderRequest;
import com.ex.model.Order;
import com.ex.repository.OrderRepository;
import com.ex.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderRepository repo;


	@PostMapping("/place")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest order) {
        Order savedOrder = orderService.placeOrder(order);
        return ResponseEntity.ok(savedOrder);
    }
	
	@GetMapping("/getall")
	public List<Order> getAllOrders() {
		
		return repo.findAll();
	}
	
	@GetMapping("/{id}")
	public Order getOrderById(@PathVariable Long id)
	{
		return repo.findById(id).orElse(null);
	}
	

	
}
