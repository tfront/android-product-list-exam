package productlist.exam.productlistexam;

import android.support.annotation.NonNull;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface ProductService {
    @NonNull
    @GET("search")
    Observable<ProductList> getProducts(@Query("page") int pageIndex,
                                        @Query("pageSize") int pageSize);

    @NonNull
    @GET("productList/{product_id}")
    Observable<Product> getProduct(@Path("product_id") int productId);
}
