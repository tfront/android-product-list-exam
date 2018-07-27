package productlist.exam.productlistexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import productlist.exam.productlistexam.adapter.ProductListAdapter;
import productlist.exam.productlistexam.model.Product;
import productlist.exam.productlistexam.viewmodel.ProductListViewModel;

public class ProductsActivity extends AppCompatActivity
        implements ProductListViewModel.OnDataRefreshListener {

    ProductListViewModel mViewModel = new ProductListViewModel();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.refresh_layout)
    RefreshLayout mRefreshLayout;

    ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);

        mRefreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        mRefreshLayout.setOnRefreshListener(refreshLayout -> mViewModel.loadFirstPageProductList());
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mViewModel.loadNextPageProductList());


        mAdapter = new ProductListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.setListener(this);
        mViewModel.loadFirstPageProductList();
    }

    @Override
    public void onDataRefreshed(List<Product> products) {
        if (mViewModel.isFirstPage()) {
            mAdapter.clearData();
            mRefreshLayout.finishRefresh();
        } else {
            mRefreshLayout.finishLoadMore();
        }
        mAdapter.appendData(products);
    }

    @Override
    public void onDataLoadFailed(String reason) {
        Toast.makeText(this, reason, Toast.LENGTH_LONG).show();
    }
}
