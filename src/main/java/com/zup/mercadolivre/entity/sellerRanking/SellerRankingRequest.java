package com.zup.mercadolivre.entity.sellerRanking;

import javax.validation.constraints.NotBlank;

public class SellerRankingRequest {

    @NotBlank
    private String purchaseId;
    @NotBlank
    private String sellerId;

    @Deprecated
    public SellerRankingRequest(){}

    public SellerRankingRequest(@NotBlank String purchaseId, @NotBlank String sellerId) {
        this.purchaseId = purchaseId;
        this.sellerId = sellerId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public String getSellerId() {
        return sellerId;
    }
}
