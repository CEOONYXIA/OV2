package com.onyxiasoftware.linkr;

/**
 * Created by Ramon on 4/10/2018.
 */

public class Customers {
    public String name, image, description;

    public Customers(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customers(String name, String image, String description) {
        this.name = name;
        this.image = image;
        this.description = description;
    }
}
