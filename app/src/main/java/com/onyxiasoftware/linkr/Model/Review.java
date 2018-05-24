package com.onyxiasoftware.linkr.Model;

/**
 * Created by Ramon on 4/24/2018.
 */

public class Review {

    private String Description, Image, MenuId, Name, Rating;

    public Review(){

    }

    public Review(String description, String image, String menuId, String name, String rating){

        Description = description;
        Image = image;
        MenuId = menuId;
        Name = name;
        Rating = rating;
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

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
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
