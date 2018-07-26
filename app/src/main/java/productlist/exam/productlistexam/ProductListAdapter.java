package productlist.exam.productlistexam;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProductListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_list_item, parent, false);
        return new ProductItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductItemViewHolder) {
            Product product = mProducts.get(position);
            ProductItemViewHolder productItemViewHolder = (ProductItemViewHolder) holder;
            PicassoWrapper.loadUrlIntoImageView(
                    "http://s3-ap-southeast-1.amazonaws.com/media.redmart.com/newmedia/150x" + product.productImage.iconUrl,
                    productItemViewHolder.icon);
            productItemViewHolder.name.setText(product.productName);
            productItemViewHolder.price.setText(String.valueOf(product.productPricing.price));
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    private List<Product> mProducts;

    public ProductListAdapter() {
        mProducts = new ArrayList<>();
    }

    public void appendData(List<Product> products) {
        int currentSize = getItemCount();
        int newItemCount = products.size();
        mProducts.addAll(products);
        notifyItemRangeInserted(currentSize, newItemCount);
    }

    class ProductItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.product_name)
        public TextView name;
        @Bind(R.id.product_price)
        public TextView price;
        @Bind(R.id.product_icon)
        public ImageView icon;

        public ProductItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
