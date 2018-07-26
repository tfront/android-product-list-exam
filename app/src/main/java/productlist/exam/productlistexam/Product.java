package productlist.exam.productlistexam;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("id")
    public int productId;

    @SerializedName("title")
    public String productName;

    @SerializedName("img")
    public ProductImage productImage;

    @SerializedName("pricing")
    public ProductPricing productPricing;
}
