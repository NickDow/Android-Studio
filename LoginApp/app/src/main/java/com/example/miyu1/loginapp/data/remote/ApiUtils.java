package com.example.miyu1.loginapp.data.remote;

/**
 * Created by nickd on 11/1/17.
 */

public class ApiUtils {
    private ApiUtils() {}

    //TODO Change api to REST endpoint before testing!
    public static final String BASE_URL = "http://51cde512.ngrok.io/";

    public static APIService getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
