package com.example.tangxu.reactnativedemo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class HttpEasyDwonloadHelper {

	private String mUrl;
	private HttpClient mClient;
	private String mFileName;
	private OnDownloadSuccessListener listener;

	public HttpEasyDwonloadHelper() {
		mClient = new DefaultHttpClient();
	}

	public void setDownloadUrl(String url) {
		this.mUrl = url;
	}

	public void setOnDownloadSuccessListener(OnDownloadSuccessListener listener) {
		this.listener = listener;
	}

	public void setFileName(String fileName) {
		this.mFileName = fileName;
	}

	public void download() {
		if (mUrl == null) {
			return;
		}
		new DownloadThread().start();
	}

	class DownloadThread extends Thread {
		@Override
		public void run() {
			HttpGet get = new HttpGet(mUrl);
			try {
				HttpResponse res = mClient.execute(get);
				InputStream is = res.getEntity().getContent();
				float total = res.getEntity().getContentLength();
				File file = new File(mFileName);
				if (!file.getParentFile().isDirectory()) {
					file.mkdirs();
				}
				if (!file.isFile()) {
					file.createNewFile();
				}
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int line = 0;
				float current = line;
				while ((line = is.read(buffer)) != -1) {
					fos.write(buffer, 0, line);
					current += line;
					listener.onProgress(total,current);
				}
				fos.flush();
				fos.close();
				get.abort();
				if (listener != null) {
					listener.onSuccess();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public interface OnDownloadSuccessListener {
		public void onSuccess();
		public void onProgress(float total, float current);
	}
}
