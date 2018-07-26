package productlist.exam.productlistexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import productlist.exam.productlistexam.constant.Constant;
import productlist.exam.productlistexam.model.Product;
import productlist.exam.productlistexam.model.ProductDetail;
import productlist.exam.productlistexam.viewmodel.ProductDetailViewModel;

public class ProductActivity extends AppCompatActivity implements ProductDetailViewModel.OnDataRefreshListener {

    private ProductDetailViewModel mViewModel = new ProductDetailViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String productId = getIntent().getStringExtra(Constant.KEY_PRODUCT_ID);
        mViewModel.setListener(this);
        mViewModel.loadProductDetail(productId);
    }

    @Override
    public void onDataRefreshed(ProductDetail productDetail) {
        int k = 0;
    }

    @Override
    public void onDataLoadFailed(String reason) {

    }
}
