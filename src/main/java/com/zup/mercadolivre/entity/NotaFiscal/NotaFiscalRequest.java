package com.zup.mercadolivre.entity.NotaFiscal;

import javax.validation.constraints.NotBlank;

public class NotaFiscalRequest {

    @NotBlank
    private String userId;
    @NotBlank
    private String purchaseId;

    @Deprecated
    public NotaFiscalRequest(){}

    public NotaFiscalRequest(@NotBlank String userId, @NotBlank String purchaseId) {
        this.userId = userId;
        this.purchaseId = purchaseId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPurchaseId() {
        return purchaseId;
    }
}
