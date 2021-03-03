package com.zup.mercadolivre.entity.email;

import com.zup.mercadolivre.entity.purcharse.GatewayPayment;
import com.zup.mercadolivre.entity.purcharse.Purchase;

public class EmailPurchase {
    Purchase purchase;

    public EmailPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public void sendPurchaseEmail(){
        System.out.println("From: " + purchase.getUser().getLogin() +
                "\nTo: " + purchase.getProduct().getOwner().getLogin() +
                "\nProduct id: " + purchase.getProduct().getId() +
                " name: " + purchase.getProduct().getName() +
                "\nQuantity: " + purchase.getQuantity()
        );
    }

    public void sendErrorFeedbackEmail(){
        System.out.println("From: System" +
                "\nTo: " + purchase.getUser().getLogin() +
                "\nProduct: \nid - " + purchase.getProduct().getId() +
                "\nname - " + purchase.getProduct().getName() +
                "\nQuantity - " + purchase.getQuantity() +
                "\nYour Payment was refused, please try again on - " +
                purchase.generateURL()
        );
    }

    public void sendSuccessFeedbackEmail(){
        System.out.println("From: System" +
                "\nTo: " + purchase.getUser().getLogin() +
                "\nProduct: \nid - " + purchase.getProduct().getId() +
                "\nname - " + purchase.getProduct().getName() +
                "\nQuantity - " + purchase.getQuantity() +
                "\nYour payment was accept!"
        );
    }
}
