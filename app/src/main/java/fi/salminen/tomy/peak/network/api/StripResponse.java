package fi.salminen.tomy.peak.network.api;


import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Extracts the payload from API response.
 */
public class StripResponse implements Interceptor {
    private final String CONTENT_TYPE = "Content-Type";
    private JsonParser mParser = new JsonParser();

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        String responseBody = response.body().string();
        String payload = stripExcess(responseBody);
        System.out.println(payload);
        return repackage(response, payload);
    }

    @NonNull
    private Response repackage(@NonNull Response response, @NonNull String payload) {
        return response.newBuilder()
                .body(ResponseBody.create(getMediaType(response), payload))
                .build();
    }

    private MediaType getMediaType(@NonNull Response response) {
        return MediaType.parse(response.headers().get(CONTENT_TYPE));
    }

    private String stripExcess(String responseBody) {
        JsonElement jsonBody = mParser.parse(responseBody);
        if (jsonBody.isJsonObject()) {
            JsonElement jsonPayload = jsonBody.getAsJsonObject().get("body");

            if (jsonPayload != null && jsonPayload.isJsonArray()) {
                return jsonPayload.toString();
            }
        }

        return responseBody;
    }
}
