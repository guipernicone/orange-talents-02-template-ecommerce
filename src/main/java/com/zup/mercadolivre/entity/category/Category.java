package com.zup.mercadolivre.entity.category;

import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String name;

    @ManyToOne
    private Category parentCategory;

    @Deprecated
    public Category (){}

    public Category(String name, Category parentCategory){
        Assert.hasLength(name, "The name must not be empty");

        this.name = name;
        this.parentCategory = parentCategory;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }
}
