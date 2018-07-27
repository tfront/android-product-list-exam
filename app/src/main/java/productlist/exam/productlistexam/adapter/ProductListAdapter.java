package productlist.exam.productlistexam.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import productlist.exam.productlistexam.ProductActivity;
import productlist.exam.productlistexam.R;
import productlist.exam.productlistexam.constant.Constant;
import productlist.exam.productlistexam.model.Product;
import productlist.exam.productlistexam.utils.ImageUtils;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mApplicationContext;

    public ProductListAdapter(Context context) {
        mApplicationContext = context.getApplicationContext();
        mProducts = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductItemViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductItemViewHolder) {
            Product product = mProducts.get(position);
            ProductItemViewHolder productItemViewHolder = (ProductItemViewHolder) holder;
            ImageUtils.loadUrlIntoImageView(ImageUtils.makeIconUrl(product.productImage.iconUrl),
                    productItemViewHolder.icon);
            productItemViewHolder.name.setText(product.productName);
            productItemViewHolder.price.setText(String.format("%.2f", product.productPricing.price));
            productItemViewHolder.vendorName.setText(product.filter.vendorName);
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private List<Product> mProducts;

    public void clearData() {
        mProducts.clear();
        notifyDataSetChanged();
    }

    public void appendData(List<Product> products) {
        int currentSize = getItemCount();
        int newItemCount = products.size();
        mProducts.addAll(products);
        notifyItemRangeInserted(currentSize, newItemCount);
    }

    public void onItemClick(int position) {
        Intent intent = new Intent(mApplicationContext, ProductActivity.class);
        intent.putExtra(Constant.KEY_PRODUCT_ID, String.valueOf(mProducts.get(position).productId));
        intent.putExtra(Constant.KEY_PRODUCT_NAME, String.valueOf(mProducts.get(position).productName));
        mApplicationContext.startActivity(intent);
    }

    class ProductItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.product_name)
        public TextView name;
        @Bind(R.id.product_price)
        public TextView price;
        @Bind(R.id.product_icon)
        public ImageView icon;
        @Bind(R.id.product_vendor_name)
        public TextView vendorName;

        public ProductItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> onItemClick(getAdapterPosition()));
        }
    }
}
