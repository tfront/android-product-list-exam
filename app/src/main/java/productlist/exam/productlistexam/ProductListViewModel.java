package productlist.exam.productlistexam;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ProductListViewModel {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private int mPageIndex = 0;

    public interface OnDataRefreshListener {
        void onDataRefreshed(List<Product> products);

        void onDataLoadFailed(String reason);
    }

    private OnDataRefreshListener mListener = null;

    public void setListener(OnDataRefreshListener listener) {
        mListener = listener;
    }

    public void loadFirstPageProductList() {
        mPageIndex = 0;
        loadProductList(mPageIndex);
    }

    public void loadNextPageProductList() {
        mPageIndex++;
        loadProductList(mPageIndex);
    }

    private void loadProductList(int pageIndex) {
        loadProductList(pageIndex, DEFAULT_PAGE_SIZE);
    }

    public boolean isFirstPage() {
        return mPageIndex == 0;
    }

    public void loadProductList(int pageIndex, int pageSize) {
        ProductService service = RetrofitFactory.getRetrofit().create(ProductService.class);
        service.getProducts(pageIndex, pageSize)
                .subscribeOn(Schedulers.from(ProductListApplication.ASYNC_THREAD_POOL))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        productList -> {
                            if (mListener != null) {
                                mListener.onDataRefreshed(productList.productList);
                            }
                        },
                        throwable -> {
                            if (mListener != null) {
                                mListener.onDataLoadFailed(throwable.getMessage());
                            }
                        },
                        () -> {
                            mListener = mListener;
                        }
                );
    }
}
