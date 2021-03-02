package com.zup.mercadolivre.entity.purcharse.Enum;

public enum GatewayStatusEnum {
    SUCESSO(0),
    ERRO(1);

    private int value;

    private GatewayStatusEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
