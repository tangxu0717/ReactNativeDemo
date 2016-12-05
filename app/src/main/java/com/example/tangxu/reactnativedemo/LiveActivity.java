package com.example.tangxu.reactnativedemo;

import com.facebook.react.ReactActivity;

import javax.annotation.Nullable;

/**
 * Created by tangxu on 2016/12/2.
 */

public class LiveActivity extends ReactActivity {
    @Nullable
    @Override
    protected String getMainComponentName() {
        return "react-native-module";
    }
}
