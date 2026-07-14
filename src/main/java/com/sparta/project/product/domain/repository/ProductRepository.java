package com.sparta.project.product.domain.repository;

import com.sparta.project.product.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, ProductRepositoryCustom{
}

