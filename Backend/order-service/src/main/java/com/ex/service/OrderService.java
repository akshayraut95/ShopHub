package com.ex.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ex.dto.OrderRequest;
import com.ex.dto.ProductResponse;
import com.ex.model.Order;
import com.ex.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

	@Autowired
    private OrderRepository orderRepository;
    
	@Autowired
	private  RestTemplate restTemplate;

    public Order placeOrder(OrderRequest order) {

        // Call Product Service
        ProductResponse product = restTemplate.getForObject(
                "http://localhost:8081/api/products/" + order.getProductId(),
                ProductResponse.class
        );

        if (product == null) {
            throw new RuntimeException("Product not found!");
        }
  
        // Step 2: Calculate price
        double total = product.getPrice() * order.getQuantity();

        // Step 3: Create Order Object
        Order order1 = new Order();
        order1.setProductId(order.getProductId());
        order1.setQuantity(order.getQuantity());
        order1.setTotalPrice(total);
        order1.setStatus("CREATED");
        order1.setOrderNumber(UUID.randomUUID().toString());

        // Step 4: Save to database
        return orderRepository.save(order1);
    }
}
