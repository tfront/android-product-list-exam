package productlist.exam.productlistexam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import productlist.exam.productlistexam.R;
import productlist.exam.productlistexam.model.ProductDescriptionItem;

public class ProductDescriptionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ProductDescriptionItem> mData;

    public ProductDescriptionAdapter(List<ProductDescriptionItem> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_description_item, parent, false);
        return new ProductDescItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductDescItemViewHolder) {
            ProductDescItemViewHolder descHolder = (ProductDescItemViewHolder) holder;
            ProductDescriptionItem item = mData.get(position);
            descHolder.title.setText(item.name);
            descHolder.body.setText(item.content);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ProductDescItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.desc_title)
        public TextView title;
        @Bind(R.id.desc_body)
        TextView body;

        ProductDescItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
