package com.irateam.sixhandshakes.util;

import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class ExecuteRequest {

    public Map<Integer, VKApiUser> getUsers(int id) {
        Map<Integer, VKApiUser> users = new HashMap<>();
        VKRequest request = new VKRequest("friends.get", VKParameters.from(VKApiConst.FIELDS, "sex", VKApiConst.USER_ID, id));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray jsonArray = response.json.getJSONObject("response").getJSONArray("items");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        VKApiUser vkApiUser = new VKApiUser(jsonArray.getJSONObject(i));
                        users.put(vkApiUser.id, vkApiUser);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return users;
    }

}
