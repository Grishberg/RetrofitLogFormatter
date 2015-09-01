package com.grishberg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

/**
 * Created by g on 01.09.15.
 */
public class Parser {
    public static String format(String input) {
        String out = "";
        input = normalizeString(input);
        try {
            String reg = "(\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{0,4}\\s*\\d{1,4}-\\d{1,4}\\/info.goodline\\.btv\\.dev D\\/Retrofit﹕\\s)";
            input = input.replaceAll(reg, "");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Map<String, Object> javaRootMapObject = gson.fromJson(input, Map.class);
            out = gson.toJson(javaRootMapObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    private static String normalizeString(String in) {
        int firstBracketPos = -1;
        int lastBracletPos = -1;
        for (int i = 0; i < in.length(); i++) {
            if(firstBracketPos < 0 && in.charAt(i)=='{'){
                firstBracketPos = i;
            }
            if (in.charAt(i)=='}'){
                lastBracletPos = i+1;
            }
        }
        if(firstBracketPos >= 0 && lastBracletPos > 0){
            return in.substring(firstBracketPos, lastBracletPos).replace("\n","");
        }
        return in;
    }
}
