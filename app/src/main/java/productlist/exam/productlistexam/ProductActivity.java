package productlist.exam.productlistexam;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import productlist.exam.productlistexam.adapter.ImagePagerAdapter;
import productlist.exam.productlistexam.constant.Constant;
import productlist.exam.productlistexam.model.ProductDetail;
import productlist.exam.productlistexam.model.ProductImage;
import productlist.exam.productlistexam.utils.ImageUtils;
import productlist.exam.productlistexam.viewmodel.ProductDetailViewModel;

public class ProductActivity extends AppCompatActivity implements ProductDetailViewModel.OnDataRefreshListener {

    private ProductDetailViewModel mViewModel = new ProductDetailViewModel();

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        String productId = getIntent().getStringExtra(Constant.KEY_PRODUCT_ID);
        mViewModel.setListener(this);
        mViewModel.loadProductDetail(productId);
    }

    @Override
    public void onDataRefreshed(ProductDetail productDetail) {
        setupImageSlider(productDetail.productImageList);

    }

    private void setupImageSlider(List<ProductImage> imageList) {
        List<String> urls = new ArrayList<>();
        for (ProductImage imageInfo : imageList) {
            urls.add(ImageUtils.makeIconUrl(imageInfo.iconUrl));
        }
        mViewPager.setAdapter(new ImagePagerAdapter(urls));
    }

    @Override
    public void onDataLoadFailed(String reason) {
        // TODO: error handling
    }
}
