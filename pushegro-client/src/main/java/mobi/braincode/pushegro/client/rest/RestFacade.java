package mobi.braincode.pushegro.client.rest;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RestFacade {
    private static String registrationUrl = "/register";

    public static String register(String username, String registrationId) {

        String responseText = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("gcmId", registrationId);

            HttpResponse response = RestSender.post(registrationUrl, jsonObject);
            responseText = EntityUtils.toString(response.getEntity());

            Log.i("Response received", responseText);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.e("Response error", e.getMessage());
            return "Unsuccessful registering";
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return responseText;
    }


}
