
package com.poojab26.visualsearchtensorflow.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductLabel {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
