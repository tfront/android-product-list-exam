package productlist.exam.productlistexam.model;

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

    @SerializedName("filters")
    public ProductFilter filter;
}
