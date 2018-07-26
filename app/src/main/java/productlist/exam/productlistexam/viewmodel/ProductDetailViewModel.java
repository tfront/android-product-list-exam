package productlist.exam.productlistexam.viewmodel;

import productlist.exam.productlistexam.ProductListApplication;
import productlist.exam.productlistexam.model.ProductDetail;
import productlist.exam.productlistexam.network.ProductService;
import productlist.exam.productlistexam.network.RetrofitFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductDetailViewModel {
    public interface OnDataRefreshListener {
        void onDataRefreshed(ProductDetail productDetail);

        void onDataLoadFailed(String reason);
    }

    private OnDataRefreshListener mListener = null;

    public void setListener(OnDataRefreshListener listener) {
        mListener = listener;
    }

    public void loadProductDetail(String productId) {
        ProductService service = RetrofitFactory.getRetrofit().create(ProductService.class);
        service.getProductDetail(productId)
                .subscribeOn(Schedulers.from(ProductListApplication.ASYNC_THREAD_POOL))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productDetailResponse -> {
                            if (mListener != null) {
                                mListener.onDataRefreshed(productDetailResponse.product);
                            }
                        },
                        throwable -> {
                            if (mListener != null) {
                                mListener.onDataLoadFailed(throwable.getMessage());
                            }
                        }
                );
    }
}
