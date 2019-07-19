package com.invoker.myapp.request;

import com.invoker.myapp.base.MyApp;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestUtil {

    public static String requestText(String protocol, String file) throws RequestException {
        return requestText(protocol, file, null);
    }

    public static String requestText(String protocol, String file, InputStream inputStream) throws RequestException {
        URL url;

        try {
            url = new URL(protocol, MyApp.instance.getHost(), MyApp.instance.getPort(), file);
        } catch (MalformedURLException e) {
            throw new RequestException("Illegal URL");
        }

        OkHttpClient client = new OkHttpClient();
        Request request;
        if (inputStream != null) {
            MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
            RequestBody requestBody = RequestBodyUtil.create(MEDIA_TYPE_MARKDOWN, inputStream);
            request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(url)
                    .build();
        }
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new RequestException("Unexpected code " + response);
            return response.body().string();
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

    public static InputStream requestFile(String protocol, String file) throws RequestException {
        URL url;
        try {
            url = new URL(protocol, MyApp.instance.getHost(), MyApp.instance.getPort(), file);
        } catch (MalformedURLException e) {
            throw new RequestException("Illegal URL");
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new RequestException("Unexpected code " + response);
            return response.body().byteStream();
        } catch (Exception e) {
            throw new RequestException(e);
        }
    }

}
