package fi.salminen.tomy.peak.util;


import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;


public class JsonValidator {

    private JsonParser mParser;

    public JsonValidator() {
        this.mParser = new JsonParser();
    }

    public boolean isValid(String jsonString) {
        try {
            mParser.parse(jsonString);
            return true;
        } catch (JsonParseException e) {
            return false;
        }
    }
}
