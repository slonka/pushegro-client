package mobi.braincode.pushegro.client.rest;

import android.util.Log;
import com.google.gson.Gson;
import mobi.braincode.pushegro.client.model.Auction;
import mobi.braincode.pushegro.client.model.AuctionList;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RestFacade {
    private static String registrationUrl = "/register";

    public static String register(String username, String registrationId) {
        String responseText = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", username);
            jsonObject.put("gcmId", registrationId);

            responseText = sendRequest(registrationUrl, jsonObject);

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

    public static String addWatcher(String username, String queryTitle) {
        String responseText = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("keyword", queryTitle);

            responseText = sendRequest(urlFor(username), jsonObject);

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

    public static String removeWatcher(String username, int predicateId) {
        String responseText = null;
        try {

            responseText = sendDeleteRequest(urlFor(username) + "/" + predicateId);

            Log.i("Response received", responseText);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Response error", e.getMessage());
            return "Unsuccessful registering";
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return responseText;
    }

    public static List<Auction> getAuctions(String username, String predicateId) {
        String responseText = null;
        try {
            Gson gson = new Gson();
            HttpResponse response = RestSender.get(urlFor(username) + "/" + predicateId);
            String json = EntityUtils.toString(response.getEntity());
            AuctionList auctionList = gson.fromJson(json, AuctionList.class);
            return auctionList.getAuctions();
        } catch (Exception e) {
            Log.e("Response error", e.getMessage());
//            return "Unsuccessful registering";
        }
        return new ArrayList<>();
    }

    private static String sendRequest(String url, JSONObject jsonObject) throws IOException, ExecutionException, InterruptedException {
        HttpResponse response = RestSender.post(url, jsonObject);
        // TODO proper error handling - check response status
        return EntityUtils.toString(response.getEntity());
    }

    private static String sendDeleteRequest(String url) throws IOException, ExecutionException, InterruptedException {
        HttpResponse response = RestSender.delete(url);
        // TODO proper error handling - check response status
        return EntityUtils.toString(response.getEntity());
    }

    private static String urlFor(String username) {
        return "/" + username + "/predicates";
    }
}
