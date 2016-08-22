package imc.cursoandroid.gdgcali.com.imccalculator.model.wp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by josefbernam@gmail.com on 25/06/2015.
 */
public class ThumbnailImagesAnswer {

    @SerializedName("full")
    private ImageDetailAnswer  full;

    @SerializedName("thumbnail")
    private ImageDetailAnswer  thumbnail;

    @SerializedName("medium")
    private ImageDetailAnswer  medium;

    public ThumbnailImagesAnswer(ImageDetailAnswer full, ImageDetailAnswer thumbnail, ImageDetailAnswer medium) {
        this.full = full;
        this.thumbnail = thumbnail;
        this.medium = medium;
    }

    public ImageDetailAnswer getFull() {
        return full;
    }

    public void setFull(ImageDetailAnswer full) {
        this.full = full;
    }

    public ImageDetailAnswer getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(ImageDetailAnswer thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ImageDetailAnswer getMedium() {
        return medium;
    }

    public void setMedium(ImageDetailAnswer medium) {
        this.medium = medium;
    }
}
