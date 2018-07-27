package productlist.exam.productlistexam.network;

import android.annotation.SuppressLint;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import productlist.exam.productlistexam.ProductListApplication;

public class HttpClient {

    private static final long DEFAULT_KEEP_ALIVE_DURATION = 5 * 60; // 5 min
    private static final int DEFAULT_KEEP_ALIVE_COUNT = 5;

    @SuppressLint("StaticFieldLeak")
    private static volatile HttpClient mInstance = null;
    private OkHttpClient mOKHttpClient = null;

    private HttpClient() {
        initClient();
    }

    public static HttpClient getInstance() {
        if (mInstance == null) {
            synchronized (HttpClient.class) {
                if (mInstance == null) {
                    mInstance = new HttpClient();
                }
            }
        }

        return mInstance;
    }

    public OkHttpClient getOKHttpClient() {
        return mOKHttpClient;
    }

    private synchronized void initClient() {
        if (mOKHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .dispatcher(new Dispatcher(ProductListApplication.ASYNC_THREAD_POOL))
                    .connectionPool(new ConnectionPool(DEFAULT_KEEP_ALIVE_COUNT,
                            DEFAULT_KEEP_ALIVE_DURATION, TimeUnit.SECONDS));
            builder.addNetworkInterceptor(new StethoInterceptor());
            mOKHttpClient = builder.build();
        }
    }
}