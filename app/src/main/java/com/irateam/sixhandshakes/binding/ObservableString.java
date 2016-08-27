package com.irateam.sixhandshakes.binding;

import android.databinding.BaseObservable;
import android.databinding.BindingConversion;

public final class ObservableString extends BaseObservable {

    private String value;

    public String get() {
        return value != null ? value : "";
    }

    public void set(String value) {
        if (!isValueEquals(value)) {
            this.value = value;
            notifyChange();
        }
    }

    public boolean isValueEquals(String s) {
        return get() != null && get().equals(s);
    }

    public boolean isEmpty() {
        return get().isEmpty();
    }

    @BindingConversion
    public static String convertObservableToString(ObservableString observableString) {
        return observableString.get();
    }
}
