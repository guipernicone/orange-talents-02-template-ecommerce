package com.zup.mercadolivre.entity.product.request;

import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductImages;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class ImageProductRequest {
    @NotNull
    private List<String> imageLinks;

    @Deprecated
    public ImageProductRequest() {}

    public ImageProductRequest(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public List<ProductImages> toModel(Product product) {
        return imageLinks.stream().map(image -> new ProductImages(image, product)).collect(Collectors.toList());
    }
}
