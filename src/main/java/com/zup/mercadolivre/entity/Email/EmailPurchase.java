package com.zup.mercadolivre.entity.email;

import com.zup.mercadolivre.entity.purcharse.Purchase;

public class EmailPurchase {
    Purchase purchase;

    public EmailPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void sendEmail(){
        System.out.println("From: " + purchase.getUser().getLogin() +
                "\nTo: " + purchase.getProduct().getOwner().getLogin() +
                "\nProduct id: " + purchase.getProduct().getId() +
                " name: " + purchase.getProduct().getName() +
                "\nQuantity: " + purchase.getQuantity()
        );
    }
}
