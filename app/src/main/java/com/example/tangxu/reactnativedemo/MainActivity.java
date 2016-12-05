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

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private Button btnUpdate;

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
    }
}
