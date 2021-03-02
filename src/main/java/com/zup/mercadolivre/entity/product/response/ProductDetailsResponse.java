package com.zup.mercadolivre.entity.product.response;

import com.zup.mercadolivre.entity.opinion.Opinion;
import com.zup.mercadolivre.entity.opinion.response.OpinionDetailsResponse;
import com.zup.mercadolivre.entity.product.Product;
import com.zup.mercadolivre.entity.product.ProductImages;
import com.zup.mercadolivre.entity.question.Question;
import com.zup.mercadolivre.entity.question.response.QuestionDetailsResponse;
import io.jsonwebtoken.lang.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsResponse {

    private String name;
    private BigDecimal price;
    private List<ProductCharacteristicsResponse> characteristics;
    private List<String> images;
    private String description;
    private double averageRating;
    private int numberOfRatings;
    private String user;
    private List<OpinionDetailsResponse> opinions;
    private List<QuestionDetailsResponse> questions;

    public ProductDetailsResponse (Product product){
        Assert.notNull(product, "The product cannot be null");
        Assert.notNull(product.getOwner(), "The product owner cannot be null");

        this.images = new ArrayList<>();
        this.opinions = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.averageRating = 0;
        this.numberOfRatings = 0;

        this.name = product.getName();
        this.price = product.getPrice();
        this.characteristics = product.getProductCharacteristics()
                .stream()
                .map(ProductCharacteristicsResponse::new)
                .collect(Collectors.toList());

        if (product.getImages() != null){
            this.images = product.getImages()
                    .stream()
                    .map(ProductImages::getLink)
                    .collect(Collectors.toList());
        }

        this.description = product.getDescription();

        if(product.getOpinions() != null) {
            this.averageRating = product.getOpinions().stream()
                    .mapToDouble(opinion -> Double.parseDouble(opinion.getRating()))
                    .average()
                    .orElse(0);

            this.numberOfRatings = product.getOpinions().size();

            this.opinions = product.getOpinions().stream()
                    .map(OpinionDetailsResponse::new)
                    .collect(Collectors.toList());
        }

        this.user = product.getOwner().getLogin();

        if(product.getQuestions() != null){
            this.questions = product.getQuestions().stream()
                    .map(QuestionDetailsResponse::new)
                    .collect(Collectors.toList());
        }
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public List<ProductCharacteristicsResponse> getCharacteristics() {
        return characteristics;
    }

    public List<String> getImages() {
        return images;
    }

    public String getDescription() {
        return description;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public String getUser() {
        return user;
    }

    public List<OpinionDetailsResponse> getOpinions() {
        return opinions;
    }

    public List<QuestionDetailsResponse> getQuestions() {
        return questions;
    }
}
