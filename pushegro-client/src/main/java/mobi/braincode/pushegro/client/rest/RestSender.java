package mobi.braincode.pushegro.client.rest;

import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class RestSender {

    private static String BASE_URL = "http://pushegro-server.herokuapp.com";
    //    private static String BASE_URL = "http://10.0.2.251:8080";
    private static DefaultHttpClient client = new DefaultHttpClient();

    public static HttpResponse post(String url, JSONObject jsonObject) throws IOException, ExecutionException, InterruptedException {
        StringEntity stringEntity = new StringEntity(jsonObject.toString());
        stringEntity.setContentType("application/json");
        final HttpPost httpRequest = new HttpPost(getAbsoluteUrl(url));
        httpRequest.setEntity(stringEntity);

        HttpResponse response = null;
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            Log.e("error during rest request", e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static HttpResponse post(String url) {
        final HttpPost httpRequest = new HttpPost(getAbsoluteUrl(url));
        HttpResponse response = null;
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse delete(String url) {
        final HttpDelete httpRequest = new HttpDelete(getAbsoluteUrl(url));
        HttpResponse response = null;
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HttpResponse get(String url) {
        final HttpGet httpRequest = new HttpGet(getAbsoluteUrl(url));
        HttpResponse response = null;
        try {
            response = client.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
