package com.irateam.sixhandshakes.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import com.irateam.sixhandshakes.model.Node;
import com.irateam.sixhandshakes.util.RequestUtils;
import com.irateam.sixhandshakes.util.SimpleCallback;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;

import org.json.JSONArray;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class RequestService extends Service {

    public static final String TAG = RequestService.class.getName();

    private RequestServiceBinder binder = new RequestServiceBinder();
    private Thread worker;
    private Handler uiHandler = new Handler(Looper.getMainLooper());
    private ResultListener listener;

    private int selfId;
    private int targetId;

    private Node selfFriendsTree;
    private Node targetFriendsTree;

    private Set<Integer> selfFriendIds;
    private Set<Integer> targetFriendIds;

    private int[] resultPath;

    public void start(int self, int target) {
        this.selfId = self;
        this.targetId = target;

        resultPath = null;

        selfFriendsTree = new Node(self);
        targetFriendsTree = new Node(target);

        selfFriendIds = new LinkedHashSet<Integer>() {{
            add(self);
        }};

        targetFriendIds = new LinkedHashSet<Integer>() {{
            add(target);
        }};

        worker = new Thread(() -> {
            firstStepProcessing();
            if (resultPath == null)
                secondStepProcessing();
            if (resultPath == null)
                thirdStepProcessing();
            if (resultPath == null)
                fourthStepProcessing();
            notifyNothingFound();
        });
        worker.start();
    }

    // First step processing
    public void firstStepProcessing() {
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, selfId));
        request.executeSyncWithListener(new SimpleCallback(response -> {

            JSONArray friendIds = response.json
                    .getJSONObject("response")
                    .getJSONArray("items");

            for (int i = 0; i < friendIds.length(); i++) {
                int id = friendIds.getInt(i);
                if (id == targetId) {
                    int[] res = {
                            selfId,
                            targetId
                    };
                    notifyPathFound(res);
                    return;
                }
                selfFriendIds.add(id);
                selfFriendsTree.addChild(new Node(id, selfFriendsTree));
            }
        }));
    }

    // Second step processing
    public void secondStepProcessing() {
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, targetId));
        request.executeSyncWithListener(new SimpleCallback(response -> {

            JSONArray friendIds = response.json
                    .getJSONObject("response")
                    .getJSONArray("items");

            for (int i = 0; i < friendIds.length(); i++) {
                int id = friendIds.getInt(i);
                if (selfFriendIds.contains(id)) {
                    int[] res = {
                            selfId,
                            id,
                            targetId
                    };
                    notifyPathFound(res);
                    return;
                }
                targetFriendIds.add(id);
                targetFriendsTree.addChild(new Node(id, targetFriendsTree));
            }
        }));
    }


    public void thirdStepProcessing() {
        Map<Integer, Node> friendsMap = selfFriendsTree.getChildren();
        for (String requestIds : RequestUtils.buildRequestStrings(friendsMap.keySet())) {

            VKRequest request = new VKRequest("execute.friend_ids", VKParameters.from("users", requestIds));
            request.executeSyncWithListener(new SimpleCallback(response -> {

                JSONArray jsonResponse = response.json.getJSONArray("response");

                for (int i = 0; i < jsonResponse.length(); i++) {

                    int userId = jsonResponse.getJSONObject(i).getInt("id");
                    JSONArray friendIds = jsonResponse.getJSONObject(i).getJSONArray("items");

                    for (int j = 0; j < friendIds.length(); j++) {
                        int id = friendIds.getInt(j);

                        selfFriendIds.add(id);

                        Node node = friendsMap.get(userId);
                        node.addChild(new Node(id, node));

                        if (targetFriendIds.contains(id)) {
                            int[] res = {
                                    selfId,
                                    selfFriendsTree.search(id).getParent().getId(),
                                    id,
                                    targetId
                            };
                            notifyPathFound(res);
                            return;
                        }
                    }
                }
            }));
        }
    }

    public void fourthStepProcessing() {
        Map<Integer, Node> friendsMap = targetFriendsTree.getChildren();
        for (String requestIds : RequestUtils.buildRequestStrings(friendsMap.keySet())) {

            VKRequest request = new VKRequest("execute.friend_ids", VKParameters.from("users", requestIds));
            request.executeSyncWithListener(new SimpleCallback(response -> {

                JSONArray jsonResponse = response.json.getJSONArray("response");

                for (int i = 0; i < jsonResponse.length(); i++) {

                    int userId = jsonResponse.getJSONObject(i).getInt("id");
                    JSONArray friendIds = jsonResponse.getJSONObject(i).getJSONArray("items");

                    for (int j = 0; j < friendIds.length(); j++) {
                        int id = friendIds.getInt(j);

                        targetFriendIds.add(id);

                        Node node = friendsMap.get(userId);
                        node.addChild(new Node(id, node));

                        if (selfFriendIds.contains(id)) {
                            int[] res = {
                                    selfId,
                                    selfFriendsTree.search(id).getParent().getId(),
                                    id,
                                    targetFriendsTree.search(id).getParent().getId(),
                                    targetId
                            };
                            notifyPathFound(res);
                            return;
                        }
                    }
                }
            }));
        }
    }

    public ResultListener getListener() {
        return listener;
    }

    public void setListener(ResultListener listener) {
        this.listener = listener;
    }

    private void notifyNothingFound() {
        if (resultPath == null) {
            uiHandler.post(() -> {
                if (listener != null) {
                    listener.onNothingFound();
                }
            });
        }
    }

    private void notifyPathFound(int[] path) {
        if (resultPath == null) {
            resultPath = path;
            uiHandler.post(() -> {
                if (listener != null) {
                    listener.onPathFound(path);
                }
            });
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public class RequestServiceBinder extends Binder {
        public RequestService getService() {
            return RequestService.this;
        }
    }

    public interface ResultListener {
        void onPathFound(int[] ids);

        void onNothingFound();
    }
}
