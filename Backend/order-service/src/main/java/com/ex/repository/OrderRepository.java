package com.ex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
