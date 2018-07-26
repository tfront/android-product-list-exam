package productlist.exam.productlistexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductsActivity extends AppCompatActivity
        implements ProductListViewModel.OnDataRefreshListener {

    ProductListViewModel mViewModel = new ProductListViewModel();

    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;

    ProductListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        ButterKnife.bind(this);
        mAdapter = new ProductListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mViewModel.setListener(this);
        mViewModel.loadProductList(0);
    }

    @Override
    public void onDataRefreshed(List<Product> products) {
        mAdapter.appendData(products);
    }

    @Override
    public void onDataLoadFailed(String reason) {

    }
}
