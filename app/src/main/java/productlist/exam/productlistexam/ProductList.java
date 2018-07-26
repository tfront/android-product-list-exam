package productlist.exam.productlistexam;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductList {
    @SerializedName("products")
    List<Product> productList;
}
