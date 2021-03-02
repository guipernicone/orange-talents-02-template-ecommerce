package com.zup.mercadolivre.entity.purcharse.Enum;

public enum GatewayEnum {
    PAYPAL(0),
    PAGSEGURO(1);

    private int value;

    private GatewayEnum(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
