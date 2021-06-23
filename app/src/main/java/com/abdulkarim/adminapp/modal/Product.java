package com.abdulkarim.adminapp.modal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {

    private String id;
    private String name;
    private String price;
    private String category;
    private String image_url;
    private String description;
    private String purchase_date;

    public static final String PRODUCT_ID = "id";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_CATEGORY = "category";
    public static final String PRODUCT_IMAGE_URL = "image_url";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_PURCHASE_DATE = "purchase_date";

    public Product() {

    }

    public Product(String name, String price, String category, String image_url, String description,String purchase_date) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.image_url = image_url;
        this.description = description;
        this.purchase_date = purchase_date;
    }

    public Product(String id, String name, String price, String category, String image_url, String description,String purchase_date) {
        this(name,price,category,image_url,description,purchase_date);
        this.id = id;
    }


    public Map<String,Object> toMap () {

        Map<String,Object> productMap = new HashMap<>();

        productMap.put(PRODUCT_ID,id);
        productMap.put(PRODUCT_NAME,name);
        productMap.put(PRODUCT_PRICE,price);
        productMap.put(PRODUCT_CATEGORY,category);
        productMap.put(PRODUCT_IMAGE_URL,image_url);
        productMap.put(PRODUCT_DESCRIPTION,description);
        productMap.put(PRODUCT_PURCHASE_DATE,purchase_date);

        return productMap;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", category='" + category + '\'' +
                ", image_url='" + image_url + '\'' +
                ", description='" + description + '\'' +
                ", purchase_date='" + purchase_date + '\'' +
                '}';
    }
}
