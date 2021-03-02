package com.zup.mercadolivre.entity.purcharse.Enum;

public enum PurchaseStatusEnum {
    NOT_INITIATE(0),
    INITIATE(1),
    FINISHED(2);

    private int value;

    private PurchaseStatusEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
