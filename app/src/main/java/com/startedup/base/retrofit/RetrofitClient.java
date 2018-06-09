package com.startedup.base.retrofit;

import com.google.gson.GsonBuilder;
import com.startedup.base.api.RetrofitService;
import com.startedup.base.ui.App;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String TAG = RetrofitClient.class.getSimpleName();
    private static String BASE_URL = "YOUR_BASE_URL_HERE";
    private static volatile Retrofit mRetrofit = null;
    private static RetrofitService mRetrofitService;


    private synchronized static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            synchronized (RetrofitClient.class) {
                if (mRetrofit == null) {
                    mRetrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .client(createClient())
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()))
                            .build();
                }
            }
        }
        return mRetrofit;
    }

    public static RetrofitService getApiService() {
        return initRetrofitService();
    }

    private static RetrofitService initRetrofitService() {
        if (mRetrofitService == null) {
            synchronized (RetrofitClient.class) {
                if (mRetrofitService == null) {
                    if (mRetrofit == null) {
                        new Throwable("Retrofit instance is not created yet in RetrofitClient.class\nFirst call getRetrofit() to create instance");
                        return null;
                    }
                    mRetrofitService = mRetrofit.create(RetrofitService.class);
                }
            }
        }
        return mRetrofitService;
    }


    private static OkHttpClient createClient() {
        return new OkHttpClient.Builder()
                .cache(new Cache(new File(App.getContext().getCacheDir(), "http"), 1024 * 1024 * 10))
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(createHeaderInterceptor())
                .addInterceptor(createHttpLoggingInterceptor())
                .build();

    }

    private static Interceptor createHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Authorization", "auth-value");

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    private static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
}