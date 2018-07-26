package productlist.exam.productlistexam.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {
    @SerializedName("products")
    public List<Product> productList;
}
