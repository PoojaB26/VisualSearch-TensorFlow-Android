
package com.poojab26.visualsearchtensorflow.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_label")
    @Expose
    private List<ProductLabel> productLabel = null;

    public List<ProductLabel> getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(List<ProductLabel> productLabel) {
        this.productLabel = productLabel;
    }

}
