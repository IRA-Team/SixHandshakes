package com.irateam.sixhandshakes.utils;

import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class SimpleCallback extends VKRequest.VKRequestListener {

    private VKCompleteListener completeListener;
    private VKErrorListener errorListener;

    public SimpleCallback(VKCompleteListener completeListener) {
        this.completeListener = completeListener;
    }

    @Override
    public void onComplete(VKResponse response) {
        if (completeListener != null) {
            try {
                completeListener.onComplete(response);
            } catch (Exception ignored) {

            }
        }
    }

    @Override
    public void onError(VKError error) {
        if (errorListener != null) {
            try {
                errorListener.onError(error);
            } catch (Exception ignored) {

            }
        }
    }


    public interface VKCompleteListener {
        void onComplete(VKResponse response) throws Exception;
    }

    public interface VKErrorListener {
        void onError(VKError error) throws Exception;
    }
}
