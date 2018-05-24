package com.onyxiasoftware.linkr.Model;

/**
 * Created by Ramon on 4/24/2018.
 */

public class Job {

    private String Name, Image, Description, MenuId;

    public Job(){

    }

    public Job(String name, String image, String description, String menuId){

        Name = name;
        Image = image;
        Description = description;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setName(String name) {
        Name = name;
    }
}
