package com.zup.mercadolivre.repository;

import com.zup.mercadolivre.entity.category.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
