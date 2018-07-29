package com.zmm.yuekao.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * OkHtttp工具类
 */
public class HttpUtils {
    private static final String TAG = "HttpUtils-----";
    private static HttpUtils httpUtils;
    private static final int SUCCESS = 0;
    private static final int ERROR = 1;
    private MyHandler myHandler = new MyHandler();
    private static OkLoadListener okLoadListener;

    //单例模式
    public static HttpUtils getHttpUtils() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    //因为Ok请求是在子线程，这样会提供效率，所以提供Handler进行线程通信，把获取到的json字符串，传入主线程，进行解析
    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    //成功
                    String json = (String) msg.obj;
                    okLoadListener.okLoadSuccess(json);
                    break;

                case ERROR:
                    //失败
                    String error = (String) msg.obj;
                    okLoadListener.okLoadError(error);
                    break;
            }
        }
    }

    //get方式post方式，两种请求方式大同小异
    //get
    public void okGet(String url) {
        //获取ok实例
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //get直接拼接参数就可以了 比如url = url+""...
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = myHandler.obtainMessage();
                message.what = ERROR;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = myHandler.obtainMessage();
                message.what = SUCCESS;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });
    }

    public void setOkLoadListener(OkLoadListener okLoadListener) {
        this.okLoadListener = okLoadListener;
    }

    //post
    public void okPost(String url, Map<String, String> params) {
        //OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new MyIntercepter()).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //post需要创建请求体，进行拼接参数
        FormBody.Builder builder = new FormBody.Builder();
        Set<String> keySet = params.keySet();
        for (String key :
                keySet) {
            String value = params.get(key);
            builder.add(key, value);
        }
        FormBody formBody = builder.build();
        Request request = new Request.Builder().url(url).post(formBody).build();
        Call call = okHttpClient.newCall(request);
        //获取失败时调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = myHandler.obtainMessage();
                message.what = ERROR;
                message.obj = e.getMessage();
                myHandler.sendMessage(message);
            }

            //获取成功时调用
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = myHandler.obtainMessage();
                message.what = SUCCESS;
                message.obj = response.body().string();
                myHandler.sendMessage(message);
            }
        });

    }

    //拦截器
    class MyIntercepter implements Interceptor {
        //intercept 拦截
        @Override
        public Response intercept(Chain chain) throws IOException {
            //添加公共参数
//            post 取出原来所有的参数，将之加到新的请求体里面。然后让请求去执行
            Request request = chain.request();
            //获取请求方法
            String method = request.method();
            if (method.equals("GET")) {//---------------------------GET 拦截
                //取出url地址
                String url = request.url().toString();
                //拼接公共参数
                boolean contains = url.contains("?");
                if (contains) {
                    url = url + "&source=android";
                } else {
                    url = url + "?source=android";
                }

                Request request1 = request.newBuilder().url(url).build();

                Response response = chain.proceed(request1);

                return response;


            } else if (method.equals("POST")) {//---------------------POST 拦截
                RequestBody body = request.body();//请求体
                if (body instanceof FormBody) {
                    //创建新的请求体
                    FormBody.Builder newBuilder = new FormBody.Builder();
                    for (int i = 0; i < ((FormBody) body).size(); i++) {
                        String key = ((FormBody) body).name(i);
                        String value = ((FormBody) body).value(i);
                        newBuilder.add(key, value);
                    }
                    //添加公共参数
                    newBuilder.add("source", "android");
                    FormBody newBody = newBuilder.build();
                    //创建新的请求体
                    Request request1 = request.newBuilder().post(newBody).build();
                    //去请求
                    Response response = chain.proceed(request1);
                    return response;
                }
            }
            return null;
        }
    }

    //上传文件（图片）
    public void upLoadImage(String url, String path) {//url 要上传的地址。path 要上传的文件路径
        //媒体类型
        MediaType mediaType = MediaType.parse("image/*");//这里写图片类型
        //multipartbody
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(path);
        MultipartBody multipartBody = builder.addFormDataPart("file", file.getName(), RequestBody.create(mediaType, file)).build();

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "上传失败: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "上传成功: ");
            }
        });

    }

    public interface OkLoadListener {
        void okLoadSuccess(String success);

        void okLoadError(String error);
    }
}
