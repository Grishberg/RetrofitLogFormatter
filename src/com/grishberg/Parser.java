package com.grishberg;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * Created by g on 01.09.15.
 */
public class Parser {
    public static Map<String, Object> format(String input) {
        input = normalizeString(input);
        try {
            //(\n?\d{2}-\d{2}\s\d{2}:\d{2}:\d{2}\.\d{0,8}\s*\d{1,8}-\d{1,8}\/\S* D\/Retrofit.\s)
            String reg = "(\\n?\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{0,8}\\s*\\d{1,8}-\\d{1,8}\\/\\S* D\\/Retrofit.\\s)";
            //\d{2}.\d{2}.\d{4}\s\d{2}:\d{2}:\d{2}\.\d{3}\sD\/Retrofit:\s
            String reg2 = "(\\n?\\d{2}.\\d{2}.\\d{4}\\s\\d{2}:\\d{2}:\\d{2}\\.\\d{3}\\sD\\/Retrofit:\\s)";
            input = input.replaceAll(reg, "");
            input = input.replaceAll(reg2, "");

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map = mapper.readValue(input, new TypeReference<Map<String, Object>>() {
            });

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String prettyPrint(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String normalizeString(String in) {
        int firstBracketPos = -1;
        int lastBracletPos = -1;
        for (int i = 0; i < in.length(); i++) {
            if (firstBracketPos < 0 && in.charAt(i) == '{') {
                firstBracketPos = i;
            }
            if (in.charAt(i) == '}') {
                lastBracletPos = i + 1;
            }
        }
        if (firstBracketPos >= 0 && lastBracletPos > 0) {
            return in.substring(firstBracketPos, lastBracletPos).replace("\n", "");
        }
        return in;
    }
}
