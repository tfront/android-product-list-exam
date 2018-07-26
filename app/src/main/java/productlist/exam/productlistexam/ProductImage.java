package productlist.exam.productlistexam;

import com.google.gson.annotations.SerializedName;

public class ProductImage {
    @SerializedName("w")
    public int width;
    @SerializedName("h")
    public int height;
    @SerializedName("name")
    public String iconUrl;
    @SerializedName("position")
    public int position;
}
