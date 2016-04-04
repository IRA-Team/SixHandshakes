package com.irateam.sixhandshakes.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
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

    private int self;
    private int target;

    private Node selfTree;
    private Node targetTree;

    private Set<Integer> selfIds;
    private Set<Integer> targetIds;


    public void start(int target) {
        this.self = 51843688;
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

        worker = new Thread(() -> {
            firstStepProcessing();
            secondStepProcessing();
            thirdStepProcessing();
            fourthStepProcessing();
            System.out.println(selfIds.size());
            System.out.println(targetIds.size());
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
                            //TODO: notify success
                            Log.e(TAG, "Self friends success");
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
                            //TODO: notify success
                            Log.e(TAG, "Target friends success");
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
                                if (targetIds.contains(id)) {
                                    Log.e(TAG, "Third step processing success");
                                }

                                selfIds.add(id);

                                Node node = childrenMap.get(parentId);
                                node.addChildNode(new Node() {{
                                    setId(id);
                                    setParent(node);
                                }});
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

                                if (selfIds.contains(id)) {
                                    Log.e(TAG, "Fourth step processing success");

                                    System.out.println("CENTER: " + id);
                                    System.out.println("SELF: " + selfTree.findById(id).getParent().getId());
                                    System.out.println("TARGET: " + parentId);
                                }

                                targetIds.add(id);

                                Node node = childrenMap.get(parentId);
                                node.addChildNode(new Node() {{
                                    setId(id);
                                    setParent(node);
                                }});

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
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
}
