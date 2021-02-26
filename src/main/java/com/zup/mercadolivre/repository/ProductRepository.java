package com.zup.mercadolivre.repository;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();

    Optional<Product> findByOwnerLogin(String loin);
}
