package com.stt.speechtotext.utils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

public class CommonUtils {

    public static String getTextValueFromResult(String input)  {
        if (input.isEmpty()) {
            return "";
        }
        JsonReader jsonReader = Json.createReader(new StringReader(input.replace('"','\"')));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        return jsonObject.getString("text");
    }

}
