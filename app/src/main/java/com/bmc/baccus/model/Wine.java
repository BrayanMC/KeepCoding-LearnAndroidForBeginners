package com.bmc.baccus.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Wine implements Serializable {
    private String id = null;
    private String name = null;
    private String type = null;
    private Bitmap photo = null;
    private String companyName = null;
    private String companyWeb = null;
    private String notes = null;
    private String origin = null;
    private int rating = 0;
    private String picture = null;
    private List<String> listGrapes = new LinkedList<>();

    public Wine() {
    }

    public Wine(String id, String name, String type, Bitmap photo, String companyName, String companyWeb, String notes, String origin, int rating, String picture) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.photo = photo;
        this.companyName = companyName;
        this.companyWeb = companyWeb;
        this.notes = notes;
        this.origin = origin;
        this.rating = rating;
        this.picture = picture;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Bitmap getPhoto(Context context) {
        if (photo == null) {
            photo = getBitmapFromURL(getPicture(), context);
        }

        return photo;
    }

    public void setPhoto(Bitmap photo) {
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<String> getListGrapes() {
        return listGrapes;
    }

    public void setListGrapes(List<String> listGrapes) {
        this.listGrapes = listGrapes;
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

    private Bitmap getBitmapFromURL(String urlPicture, Context context) {
        File imageFile = new File(context.getCacheDir(), getId());

        if (imageFile.exists()) {
            return BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        } else {
            InputStream in = null;
            try {
                in = new URL(urlPicture).openStream();
                Bitmap bmp = BitmapFactory.decodeStream(in);

                FileOutputStream fos = new FileOutputStream(imageFile);
                bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);

                return bmp;
            } catch (Exception e) {
//                Log.e("WINE", e.getMessage());
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e) {
                    Log.e("", e.getMessage());
                }
            }

            return null;
        }
    }

    public String toString() {
//        return new Gson().toJson(this);
        return getName();
    }
}