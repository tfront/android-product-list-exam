package productlist.exam.productlistexam.model;

import com.google.gson.annotations.SerializedName;

public class ProductPricing {
    @SerializedName("onsale")
    public boolean onSale;
    @SerializedName("price")
    public float price;
    // TODO: add more
}
