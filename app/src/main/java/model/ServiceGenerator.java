package model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl("https://eun1.api.riotgames.com/lol/")
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static RiotAPI riotAPI = retrofit.create(RiotAPI.class);

    public static RiotAPI getRiotAPI() {
        return riotAPI;
    }
}
