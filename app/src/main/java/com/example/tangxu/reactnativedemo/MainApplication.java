package com.example.tangxu.reactnativedemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by tangxu on 2016/12/2.
 */

public class MainApplication extends Application implements ReactApplication {
    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            //Log.d("tangxu33",super.getJSBundleFile());
            //return super.getJSBundleFile();
            String jsBundleFile = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "rn_update" + File.separator + "update" + File.separator + "index.android.bundle";
            File file = new File(jsBundleFile);
            if(file !=null && file.exists()){
                Log.d("tangxu33","getJSBundleFile:"+jsBundleFile);
            }else{
                Log.d("tangxu33","getJSBundleFile:"+null);
            }
            return file != null && file.exists() ? jsBundleFile : null;
        }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}
