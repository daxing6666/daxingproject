package com.briup.frame.net.http;

import com.briup.frame.net.config.OkHttpConfig;
import com.briup.frame.net.gson.GsonAdapter;
import com.briup.frame.net.interceptor.RxHttpLogger;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * RetrofitClient工具类
 */

public class RetrofitClient {

    private static RetrofitClient instance;

    private Retrofit.Builder retrofitBuilder;

    private OkHttpClient okHttpClient;

    public RetrofitClient() {

        initDefaultOkHttpClient();
        retrofitBuilder = new Retrofit.Builder()
                //支持RxJava平台
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create(GsonAdapter.buildGson()));
    }

    private void initDefaultOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);
        builder.connectTimeout(10, TimeUnit.SECONDS);

        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new RxHttpLogger());
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);

        okHttpClient = builder.build();
    }


    public static RetrofitClient getInstance() {

        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null) {
                    instance = new RetrofitClient();
                }
            }

        }
        return instance;
    }


    public Retrofit.Builder getRetrofitBuilder() {
        return retrofitBuilder;
    }

    public Retrofit getRetrofit() {
        if (null == OkHttpConfig.getOkHttpClient()) {
            return retrofitBuilder.client(okHttpClient).build();
        } else {
            return retrofitBuilder.client(OkHttpConfig.getOkHttpClient()).build();
        }
    }

}
