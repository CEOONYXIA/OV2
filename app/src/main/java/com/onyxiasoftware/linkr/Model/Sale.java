package com.onyxiasoftware.linkr.Model;

/**
 * Created by Ramon on 4/24/2018.
 */

public class Sale {

    private String Description, Image, MenuId, Name, Price;

    public Sale(){

    }

    public Sale(String description, String image, String menuId, String name, String price){

        Description = description;
        Image = image;
        MenuId = menuId;
        Name = name;
        Price = price;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public String getMenuId() {
        return MenuId;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
