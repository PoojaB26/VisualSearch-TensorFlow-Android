
package com.poojab26.visualsearchtensorflow.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Product {

    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_label")
    @Expose
    private String productLabel;
    @SerializedName("product_url")
    @Expose
    private String productUrl;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

}