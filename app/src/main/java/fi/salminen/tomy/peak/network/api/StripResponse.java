package fi.salminen.tomy.peak.network.api;


import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * Extracts the payload from API response.
 */
public class StripResponse implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        JsonElement payload = extractPayload(response.body().string());
        return payload != null
                ? strippedResponse(payload.toString(), response)
                : response;
    }

    private Response strippedResponse(String payloadStr, Response response) throws IOException {
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), payloadStr))
                .build();
    }

    private JsonElement extractPayload(String responseBody) throws IOException {
        JsonParser parser = new JsonParser();
        return parser.parse(responseBody).getAsJsonObject().get("body");
    }
}
