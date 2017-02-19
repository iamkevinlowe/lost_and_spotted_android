package com.lostandspotted.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Pet implements Serializable {

    private int id;
    private String color;
    private boolean found;
    @SerializedName("hair_length")
    private String hairLength;
    private boolean licensed;
    private Object note;
    @SerializedName("pet_type")
    private String petType;
    private String size;
    private List<Comment> comments = null;
    private List<Image> images = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public String getHairLength() {
        return hairLength.substring(0, 1).toUpperCase() + hairLength.substring(1);
    }

    public void setHairLength(String hairLength) {
        this.hairLength = hairLength.toLowerCase();
    }

    public boolean isLicensed() {
        return licensed;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        this.note = note;
    }

    public String getPetType() {
        return petType.substring(0, 1).toUpperCase() + petType.substring(1);
    }

    public void setPetType(String petType) {
        this.petType = petType.toLowerCase();
    }

    public String getSize() {
        return size.substring(0, 1).toUpperCase() + size.substring(1);
    }

    public void setSize(String size) {
        this.size = size.toLowerCase();
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void setImage(Image image) {
        this.images.add(image);
    }
}
