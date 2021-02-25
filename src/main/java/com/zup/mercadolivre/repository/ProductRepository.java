package com.zup.mercadolivre.repository;

import com.zup.mercadolivre.entity.product.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();
}
