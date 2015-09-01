package com.grishberg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Created by g on 01.09.15.
 */
public class Parser {
    public static String format(String input){
        String out = "";
        try{
            input = input.replaceAll("<b>([^<]*)</b>","");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> javaRootMapObject = gson.fromJson(input, Map.class);
            out = gson.toJson(javaRootMapObject);

        }catch (Exception e){
            e.printStackTrace();
        }
        return out;
    }
}
