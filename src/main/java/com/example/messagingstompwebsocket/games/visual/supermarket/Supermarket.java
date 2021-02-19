package com.example.messagingstompwebsocket.games.visual.supermarket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;

public class Supermarket extends Product {
    private static ArrayList productList;
    private ArrayList userList = new <Product>ArrayList();

    public Supermarket(int level) {
        productList = new ArrayList<Product>();
        fillProductList();
        chooseRandomProductsForLevel(level);
    }

    public static void fillProductList() {

        productList.add(new Product("μήλο", "/photographs/supermarket/apple.png"));
        productList.add(new Product("ψωμί", "/photographs/supermarket/bread.png"));
        productList.add(new Product("δημητριακά", "/photographs/supermarket/cereals.png"));
        productList.add(new Product("τυρί", "/photographs/supermarket/cheese.png"));
        productList.add(new Product("πατατάκια", "/photographs/supermarket/chips.png"));
        productList.add(new Product("σοκολάτα", "/photographs/supermarket/chocolate.png"));
        productList.add(new Product("καφές", "/photographs/supermarket/coffee.png"));
        productList.add(new Product("αυγά", "/photographs/supermarket/eggs.png"));
        productList.add(new Product("χυμός", "/photographs/supermarket/juice.png"));
        productList.add(new Product("κέτσαπ", "/photographs/supermarket/ketchup.png"));
        productList.add(new Product("πατάτες", "/photographs/supermarket/potato.png"));
        productList.add(new Product("μακαρόνια", "/photographs/supermarket/spaghetti.png"));
        productList.add(new Product("μπριζόλα", "/photographs/supermarket/steak.png"));
        productList.add(new Product("ζάχαρη", "/photographs/supermarket/sugar.png"));
        productList.add(new Product("ντομάτα", "/photographs/supermarket/tomato.png"));
        //productList.add(new Product("apple", "/photographs/supermarket/apple.png"));

    }

    public String getProductNameAt(ArrayList list, int pos) {
        Product p = (Product) list.get(pos);
        return p.getProductName();
    }

    public String getProductImageAt(ArrayList list, int pos) {
        Product p = (Product) list.get(pos);
        return p.getProductImage();
    }

    public Product getRandomProduct(ArrayList array) {
        int rnd = new Random().nextInt(array.size());
        return (Product) array.get(rnd);
    }

    //added by LJ
    public ArrayList<Product> getProductList(){
        return productList;
    }
    
    public ArrayList<Product> chooseRandomProductsForLevel(int level) {
        Product p;
        switch (level) {
            case 1:
                while (userList.size() != 4) {
                    p = getRandomProduct(productList);
                    if (!userList.contains(p)) {
                        userList.add(p);
                    }
                }
                break;

            case 2:
                while (userList.size() != 5) {
                    p = getRandomProduct(productList);
                    if (!userList.contains(p)) {
                        userList.add(p);
                    }
                }
                break;

            case 3:
                while (userList.size() != 6) {
                    p = getRandomProduct(productList);
                    if (!userList.contains(p)) {
                        userList.add(p);
                    }
                }
                break;

            case 4:
                while (userList.size() != 7) {
                    p = getRandomProduct(productList);
                    if (!userList.contains(p)) {
                        userList.add(p);
                    }
                }
                break;

            case 5:
                while (userList.size() != 8) {
                    p = getRandomProduct(productList);
                    if (!userList.contains(p)) {
                        userList.add(p);
                    }
                }
                break;

        }
        return userList;
    }

    public void printList() {
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(getProductNameAt(userList, i));
        }
    }

    public ArrayList returnGeneratedList() {
        return userList;
    }

    public ArrayList returnShuffledProductList() {
        Collections.shuffle(productList);
        return productList;
    }
}