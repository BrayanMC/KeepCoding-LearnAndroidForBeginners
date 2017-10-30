package com.bmc.baccus.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {
    private String name = null;
    private String type = null;
    private int photo = 0;
    private String companyName = null;
    private String companyWeb = null;
    private String notes = null;
    private String origin = null;
    private int rating = 0;
    private List<String> listGrapes = new LinkedList<>();

    public Wine(String name, String type, int photo, String companyName, String companyWeb, String notes, String origin, int rating) {
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.companyName = companyName;
        this.companyWeb = companyWeb;
        this.notes = notes;
        this.origin = origin;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyWeb() {
        return companyWeb;
    }

    public void setCompanyWeb(String companyWeb) {
        this.companyWeb = companyWeb;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void addGrape(String grape) {
        listGrapes.add(grape);
    }

    public int getGrapeCount() {
        return listGrapes.size();
    }

    public String getGrape(int index) {
        return listGrapes.get(index);
    }

    public String toString() {
//        return new Gson().toJson(this);
        return getName();
    }
}