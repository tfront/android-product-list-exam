package productlist.exam.productlistexam.network;

import productlist.exam.productlistexam.ProductListApplication;
import productlist.exam.productlistexam.constant.Constant;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class RetrofitFactory {
    private static volatile Retrofit mRetrofitInstance = null;
    public static synchronized Retrofit getRetrofit() {
        if (mRetrofitInstance == null) {
            mRetrofitInstance = new Retrofit.Builder().baseUrl(Constant.BASE_URL)
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
