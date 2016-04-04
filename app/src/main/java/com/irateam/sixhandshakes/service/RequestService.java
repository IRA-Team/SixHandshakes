package com.irateam.sixhandshakes.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.irateam.sixhandshakes.model.Node;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RequestService extends Service {

    public static final String TAG = RequestService.class.getName();

    private RequestServiceBinder binder = new RequestServiceBinder();
    private Thread worker;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ResultListener listener;

    private int self;
    private int target;

    private Node selfTree;
    private Node targetTree;

    private Set<Integer> selfIds;
    private Set<Integer> targetIds;

    private int[] resultPath;


    public void start(int self, int target) {
        this.self = self;
        this.target = target;

        selfTree = new Node() {{
            setId(self);
        }};

        targetTree = new Node() {{
            setId(target);
        }};

        selfIds = new LinkedHashSet<Integer>() {{
            add(self);
        }};

        targetIds = new LinkedHashSet<Integer>() {{
            add(target);
        }};

        resultPath = null;

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
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, self));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray idsArray = response.json.getJSONObject("response").getJSONArray("items");
                    for (int i = 0; i < idsArray.length(); i++) {
                        int id = idsArray.getInt(i);
                        if (id == target) {
                            int[] res = {
                                    self,
                                    target
                            };
                            notifyPathFound(res);
                            return;
                        }
                        selfIds.add(id);
                        selfTree.addChildNode(new Node() {{
                            setId(id);
                            setParent(selfTree);
                        }});
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Self friends processing error");
                    e.printStackTrace();
                }
            }
        });
    }

    // Second step processing
    public void secondStepProcessing() {
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.USER_ID, target));
        request.executeSyncWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                try {
                    JSONArray idsArray = response.json.getJSONObject("response").getJSONArray("items");
                    for (int i = 0; i < idsArray.length(); i++) {
                        int id = idsArray.getInt(i);
                        if (selfIds.contains(id)) {
                            int[] res = {
                                    self,
                                    id,
                                    target
                            };
                            notifyPathFound(res);
                            return;
                        }
                        targetIds.add(i);
                        targetTree.addChildNode(new Node() {{
                            setId(id);
                            setParent(targetTree);
                        }});
                    }
                } catch (JSONException e) {
                    Log.e(TAG, "Self friends processing error");
                    e.printStackTrace();
                }
            }
        });
    }

    public void thirdStepProcessing() {
        Map<Integer, Node> childrenMap = selfTree.getChildren();
        List<Integer> children = new ArrayList<>(childrenMap.keySet());
        for (int multiplier = 0; multiplier < Math.ceil(children.size() / 25.0); multiplier++) {

            StringBuilder builder = new StringBuilder();
            for (int i = 25 * multiplier; i < (25 * multiplier + 25) && i < children.size(); i++) {
                builder.append(String.valueOf(children.get(i)));
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);

            VKRequest request = new VKRequest("execute.friend_ids", VKParameters.from("users", builder.toString()));
            request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    try {
                        JSONArray jsonResponse = response.json.getJSONArray("response");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONArray a = jsonResponse.getJSONObject(i).getJSONArray("items");
                            int parentId = jsonResponse.getJSONObject(i).getInt("id");
                            for (int j = 0; j < a.length(); j++) {
                                int id = a.getInt(j);

                                selfIds.add(id);

                                Node node = childrenMap.get(parentId);
                                node.addChildNode(new Node() {{
                                    setId(id);
                                    setParent(node);
                                }});

                                if (targetIds.contains(id)) {
                                    int[] res = {
                                            self,
                                            selfTree.findById(id).getParent().getId(),
                                            id,
                                            target
                                    };
                                    notifyPathFound(res);
                                    return;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void fourthStepProcessing() {
        Map<Integer, Node> childrenMap = targetTree.getChildren();
        List<Integer> children = new ArrayList<>(childrenMap.keySet());
        for (int multiplier = 0; multiplier < Math.ceil(children.size() / 25.0); multiplier++) {

            StringBuilder builder = new StringBuilder();
            for (int i = 25 * multiplier; i < (25 * multiplier + 25) && i < children.size(); i++) {
                builder.append(String.valueOf(children.get(i)));
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);

            VKRequest request = new VKRequest("execute.friend_ids", VKParameters.from("users", builder.toString()));
            request.executeSyncWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    try {
                        JSONArray jsonResponse = response.json.getJSONArray("response");
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONArray a = jsonResponse.getJSONObject(i).getJSONArray("items");
                            int parentId = jsonResponse.getJSONObject(i).getInt("id");
                            for (int j = 0; j < a.length(); j++) {
                                int id = a.getInt(j);

                                targetIds.add(id);

                                Node node = childrenMap.get(parentId);
                                node.addChildNode(new Node() {{
                                    setId(id);
                                    setParent(node);
                                }});

                                if (selfIds.contains(id)) {
                                    int[] res = {
                                            self,
                                            selfTree.findById(id).getParent().getId(),
                                            id,
                                            targetTree.findById(id).getParent().getId(),
                                            target
                                    };
                                    notifyPathFound(res);
                                    return;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
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
            handler.post(() -> {
                if (listener != null) {
                    listener.onNothingFound();
                }
            });
        }
    }

    private void notifyPathFound(int[] path) {
        resultPath = path;
        handler.post(() -> {
            if (listener != null) {
                listener.onPathFound(path);
            }
        });
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
