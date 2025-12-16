package com.ex.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ex.dto.OrderRequest;
import com.ex.dto.ProductResponse;
import com.ex.dto.StockUpdateRequest;
import com.ex.model.Order;
import com.ex.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order placeOrder(OrderRequest request) {

        // 1️⃣ Call Product Service
        ProductResponse product = restTemplate.getForObject(
            "http://product-service/products/" + request.getProductId(),
            ProductResponse.class
        );

        if (product == null || product.getStock() < request.getQuantity()) {
            throw new RuntimeException("Product not available or insufficient stock");
        }

        // 2️⃣ Create Order
        Order order = new Order();
        order.setProductId(product.getId());
        order.setQuantity(request.getQuantity());
        order.setTotalPrice(product.getPrice() * request.getQuantity());
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setStatus("PLACED");

        // 3️⃣ Save Order
        
        Order savedOrder=orderRepository.save(order);
        // 3️⃣ Update stock in Product Service
        
        StockUpdateRequest stockeRequest=new StockUpdateRequest();
        stockeRequest.setQuantity(request.getQuantity());
        
        restTemplate.put("http://product-service/products/" + product.getId() + "/reduce-stock", stockeRequest);
        
        return savedOrder;
        
        
        
        
        
    }
}