package productlist.exam.productlistexam;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import productlist.exam.productlistexam.adapter.ImagePagerAdapter;
import productlist.exam.productlistexam.adapter.ProductDescriptionAdapter;
import productlist.exam.productlistexam.constant.Constant;
import productlist.exam.productlistexam.model.ProductDescriptionItem;
import productlist.exam.productlistexam.model.ProductDescriptions;
import productlist.exam.productlistexam.model.ProductDetail;
import productlist.exam.productlistexam.model.ProductImage;
import productlist.exam.productlistexam.utils.ImageUtils;
import productlist.exam.productlistexam.viewmodel.ProductDetailViewModel;

public class ProductActivity extends AppCompatActivity implements ProductDetailViewModel.OnDataRefreshListener {

    private ProductDetailViewModel mViewModel = new ProductDetailViewModel();

    @Bind(R.id.viewpager)
    ViewPager mViewPager;

    @Bind(R.id.desc_list_recycler_view)
    RecyclerView mDescList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        String productId = getIntent().getStringExtra(Constant.KEY_PRODUCT_ID);
        String productName = getIntent().getStringExtra(Constant.KEY_PRODUCT_NAME);

        initActionBar(productName);

        mViewModel.setListener(this);
        mViewModel.loadProductDetail(productId);

        mDescList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onDataRefreshed(ProductDetail productDetail) {
        setupImageSlider(productDetail.productImageList);
        setupDescList(productDetail.descriptions);
    }

    private void setupDescList(ProductDescriptions descriptions) {
        List<ProductDescriptionItem> descItems = new ArrayList<>();
        descItems.addAll(descriptions.primary);
        descItems.addAll(descriptions.secondary);
        mDescList.setAdapter(new ProductDescriptionAdapter(descItems));
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

    private void initActionBar(String productName) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(productName);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
