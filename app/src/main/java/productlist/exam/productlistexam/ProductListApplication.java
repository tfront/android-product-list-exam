package productlist.exam.productlistexam;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Process;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProductListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
    }

    @SuppressLint("StaticFieldLeak")
    private static Picasso mPicassoInstance = null;

    public static Picasso getPicasso() {
        return mPicassoInstance;
    }

    private void initApplication() {
        initPicasso();
        Stetho.initializeWithDefaults(this);
    }

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int MINIMUM_CPU_COUNT = CPU_COUNT < 4 ? 4 : CPU_COUNT;
    private static final int CORE_POOL_SIZE = MINIMUM_CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = MINIMUM_CPU_COUNT * 2 + 1;
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(@NonNull Runnable r) {
            Thread t = new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
            t.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return t;
        }
    };
    public static final ThreadPoolExecutor ASYNC_THREAD_POOL = new ThreadPoolExecutor(CORE_POOL_SIZE,
            MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<>
            (128), sThreadFactory);

    private void initPicasso() {
        mPicassoInstance = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(this))
                .build();
    }
}
