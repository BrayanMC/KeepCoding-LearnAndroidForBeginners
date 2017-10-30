package com.bmc.baccus.model;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.bmc.baccus.network.JSONKeys;
import com.bmc.baccus.network.RestAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class Winery {

    private static Winery sInstace = null;

    private List<Wine> listWines = null;

    @SuppressLint("ObsoleteSdkInt")
    public synchronized static Winery getInstance() {
        if (sInstace == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }

            sInstace = downloadWines();
        }

        return sInstace;
    }

    private static Winery downloadWines() {
        Winery oWinery = new Winery();
        oWinery.listWines = new LinkedList<Wine>();

        try {
            URLConnection conn = new URL(RestAPI.URL_WINES).openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();

            JSONArray jsonArray = new JSONArray(stringBuilder.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has(JSONKeys.WINE_NAME)) {
                    Wine oWine = new Wine();

                    oWine.setId(jsonObject.getString(JSONKeys.WINE_ID));
                    oWine.setName(jsonObject.getString(JSONKeys.WINE_NAME));
                    oWine.setType(jsonObject.getString(JSONKeys.WINE_TYPE));
                    oWine.setCompanyName(jsonObject.getString(JSONKeys.WINE_COMPANY));
                    oWine.setCompanyWeb(jsonObject.getString(JSONKeys.WINE_COMPANY_WEB));
                    oWine.setNotes(jsonObject.getString(JSONKeys.WINE_NOTES));
                    oWine.setRating(jsonObject.getInt(JSONKeys.WINE_RATING));
                    oWine.setOrigin(jsonObject.getString(JSONKeys.WINE_ORIGIN));
                    oWine.setPicture(jsonObject.getString(JSONKeys.WINE_PICTURE));

                    JSONArray jsonGrapes = jsonObject.getJSONArray(JSONKeys.WINE_GRAPES);
                    for (int j = 0; j < jsonGrapes.length(); j++) {
                        oWine.addGrape(jsonGrapes.getJSONObject(j).getString(JSONKeys.WINE_GRAPE));
                    }

                    oWinery.listWines.add(oWine);
                }
            }

        } catch (IOException e) {
            Log.e("", e.getMessage());
        } catch (JSONException e) {
            Log.e("", e.getMessage());
        }

        return oWinery;
    }

    public static boolean isInstanceAvailable() {
        return sInstace != null;
    }

    public Wine getWine(int index) {
        return listWines.get(index);
    }

    public int getWineCount() {
        return listWines.size();
    }

    public List<Wine> getWineList() {
        return listWines;
    }
}
