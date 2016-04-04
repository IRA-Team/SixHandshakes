package com.irateam.sixhandshakes;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class API {
    public static API api = new API();

    public static API getInstance() {
        return api;
    }

    private FriendService friendService;

    private API() {
/*        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(new AuthenticationInterceptor())
                .build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.vk.com/method/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        friendService = retrofit.create(FriendService.class);
    }

    public FriendService getFriendService() {
        return friendService;
    }
}
