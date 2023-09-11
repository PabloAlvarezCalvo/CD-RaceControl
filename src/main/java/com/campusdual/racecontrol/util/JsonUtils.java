package com.campusdual.racecontrol.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;

public class JsonUtils {
    private JsonUtils() {
    }

    public static void exportJsonObjectToFile(JSONObject object, String filename){
        try (
                FileWriter fw = new FileWriter(filename)
        ) {
            fw.write(object.toJSONString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static JSONObject importJSONFile(String filename){
        try(FileReader fr = new FileReader(filename)){
            JSONParser parser = new JSONParser();
            return (JSONObject)parser.parse(fr);

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
