package productlist.exam.productlistexam;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RetrofitFactory {
    private static final String BASE_URL = "https://api.redmart.com/v1.6.0/catalog/";
    private static volatile Retrofit mRetrofitInstance = null;
    public static synchronized Retrofit getRetrofit() {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(HttpClient.getInstance().getOKHttpClient())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(
                            Schedulers.from(ProductListApplication.ASYNC_THREAD_POOL)
                    ))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofitInstance;
    }
}
