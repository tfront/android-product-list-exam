package productlist.exam.productlistexam.network;

import android.support.annotation.NonNull;

import productlist.exam.productlistexam.model.Product;
import productlist.exam.productlistexam.model.ProductDetail;
import productlist.exam.productlistexam.model.ProductDetailResponse;
import productlist.exam.productlistexam.model.ProductList;
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
    @GET("products/{product_id}")
    Observable<ProductDetailResponse> getProductDetail(@Path("product_id") String productId);
}
