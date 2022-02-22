package com.example.messagingstompwebsocket.games.visual.supermarket;

public class Product {
    private String productName;
    private String productImage;

    public Product() {
        productName="";
        productImage="";
    }

    public Product(String pName,String pImage) {
        productName = pName;
        productImage = pImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

}
