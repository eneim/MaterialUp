
package io.jari.materialup.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("teaser_url")
    @Expose
    private String teaserUrl;
    @SerializedName("preview_url")
    @Expose
    private String previewUrl;

    /**
     * 
     * @return
     *     The teaserUrl
     */
    public String getTeaserUrl() {
        return teaserUrl;
    }

    /**
     * 
     * @param teaserUrl
     *     The teaser_url
     */
    public void setTeaserUrl(String teaserUrl) {
        this.teaserUrl = teaserUrl;
    }

    /**
     * 
     * @return
     *     The previewUrl
     */
    public String getPreviewUrl() {
        return previewUrl;
    }

    /**
     * 
     * @param previewUrl
     *     The preview_url
     */
    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

}
