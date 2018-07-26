package productlist.exam.productlistexam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductDetail extends Product {
    @SerializedName("desc")
    public String productDescription;

    @SerializedName("images")
    public List<ProductImage> productImageList;

    @SerializedName("description_fields")
    public ProductDescriptions descriptions;
}
