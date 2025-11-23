package com.ex.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ex.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
