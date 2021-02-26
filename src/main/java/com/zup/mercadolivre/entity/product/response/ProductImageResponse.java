package com.zup.mercadolivre.entity.product.response;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductImages;

import java.util.List;
import java.util.stream.Collectors;

public class ProductImageResponse extends ProductResponse{

    private List<String> image;

    public ProductImageResponse(Product product){
        super(product);
        image = product.getImages()
                .stream()
                .map(ProductImages::getLink)
                .collect(Collectors.toList());
    }

    public List<String> getImage() {
        return image;
    }
}
