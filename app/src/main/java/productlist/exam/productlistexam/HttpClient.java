package productlist.exam.productlistexam;

import android.content.Context;
import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

public class HttpClient {

    private static final long DEFAULT_KEEP_ALIVE_DURATION = 5 * 60; // 5 min
    private static final int DEFAULT_KEEP_ALIVE_COUNT = 5;

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

    private static Context sContext;

    public static void setContext(Context context) {
        sContext = context;
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
            mOKHttpClient = builder.build();
        }
    }
}