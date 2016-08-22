package imc.cursoandroid.gdgcali.com.imccalculator.model.wp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josefbernam@gmail.com on 25/06/2015.
 */
public class ImageDetailAnswer {
    @SerializedName("url")
    private String url;

    @SerializedName("width")
    private Integer width;

    @SerializedName("height")
    private Integer height;

    public ImageDetailAnswer(String url, Integer width, Integer height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
