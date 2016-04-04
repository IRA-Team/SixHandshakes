package com.irateam.sixhandshakes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendService {

    @GET("execute.friend_ids")
    Call<VkResponse> getFriendsId(@Query("users") String ids, @Query("access_token") String accessToken, @Query("v") String v);

}
