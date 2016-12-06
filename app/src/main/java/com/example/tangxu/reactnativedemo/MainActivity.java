package com.example.tangxu.reactnativedemo;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.react.JSCConfig;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private Button btnUpdate,btnReload,btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = (TextView)findViewById(R.id.tv_view);
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,LiveActivity.class));
            }
        });
        btnUpdate = (Button)findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HttpEasyDwonloadHelper helper = new HttpEasyDwonloadHelper();
                String fileName = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "rn_update" + File.separator;
                final File file = new File(fileName);
                if(!file.isDirectory()){
                    file.mkdirs();
                }else{
                    //file.delete();
                }
                file.mkdirs();
                fileName = fileName + "update.zip";

                final String zipPath = fileName;

                helper.setDownloadUrl("http://192.168.11.205:8181/rn_update/update.zip");
                helper.setFileName(fileName);
                helper.setOnDownloadSuccessListener(new HttpEasyDwonloadHelper.OnDownloadSuccessListener() {
                    @Override
                    public void onSuccess() {
                        //Toast.makeText(MainActivity.this, "update success", Toast.LENGTH_LONG).show();
                        Log.d("tangxu33","down load update.zip success");
                        try{
                            ZipUtil.Ectract(zipPath,Environment.getExternalStorageDirectory().getAbsolutePath()
                                    + File.separator + "rn_update");
                            Log.d("tangxu33","unzip success");
                            new File(zipPath).delete();
                        }catch (Exception e){
                            Log.d("tangxu33","unzip fail");
                        }
                    }

                    @Override
                    public void onProgress(float total, float current) {
                        //Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_LONG).show();
                    }
                });
                helper.download();
            }
        });
        btnReload = (Button)findViewById(R.id.btn_reload);
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onJSBundleLoadedFromServer();
            }
        });
        btnClear = (Button)findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + File.separator + "rn_update");
                deleteAllFilesOfDir(file);
            }
        });
    }

    private void onJSBundleLoadedFromServer() {
        String JS_BUNDLE_LOCAL_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "rn_update" + File.separator + "update" + File.separator + "index.android.bundle";
        File file = new File(JS_BUNDLE_LOCAL_PATH);
        if (file == null || !file.exists()) {
            //Log.i(TAG, "js bundle file download error, check URL or network state");
            //JS_BUNDLE_LOCAL_PATH="assets://index.android.bundle";
            return;
        }

        //Log.i(TAG, "js bundle file file success, reload js bundle");

        Toast.makeText(MainActivity.this, "reload bundle complete", Toast.LENGTH_LONG).show();
        try {
            ReactInstanceManager mReactInstanceManager =((MainApplication)getApplication()).getReactNativeHost().getReactInstanceManager();

            Class<?> RIManagerClazz = mReactInstanceManager.getClass();

            Field f = RIManagerClazz.getDeclaredField("mJSCConfig");
            f.setAccessible(true);
            JSCConfig jscConfig = (JSCConfig)f.get(mReactInstanceManager);

            Method method = RIManagerClazz.getDeclaredMethod("recreateReactContextInBackground",
                    com.facebook.react.cxxbridge.JavaScriptExecutor.Factory.class,
                    com.facebook.react.cxxbridge.JSBundleLoader.class);
            method.setAccessible(true);
            method.invoke(mReactInstanceManager,
                    new com.facebook.react.cxxbridge.JSCJavaScriptExecutor.Factory(jscConfig.getConfigMap()),
                    com.facebook.react.cxxbridge.JSBundleLoader.createFileLoader(JS_BUNDLE_LOCAL_PATH));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e){
            e.printStackTrace();
        }
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }
}
