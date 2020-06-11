package com.hamza3202.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hamza3202.config.OpenLibraryConfiguration;

public class Book
{

    private String key;
    private String preview;
    private String previewURL;
    private String infoURL;
    private String thumbnailURL;

    @JsonCreator
    public Book(@JsonProperty(OpenLibraryConfiguration.RESP_BIB_KEY) String key,
                @JsonProperty(OpenLibraryConfiguration.RESP_PREVIEW_KEY) String preview,
                @JsonProperty(OpenLibraryConfiguration.RESP_PREVIEW_URL_KEY) String previewURL,
                @JsonProperty(OpenLibraryConfiguration.RESP_INFO_URL_KEY) String infoURL,
                @JsonProperty(OpenLibraryConfiguration.RESP_THUMBNAIL_URL_KEY) String thumbnailURL)
    {
        this.key = key;
        this.preview = preview;
        this.previewURL = previewURL;
        this.infoURL = infoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getPreview()
    {
        return preview;
    }

    public void setPreview(String preview)
    {
        this.preview = preview;
    }

    public String getPreviewURL()
    {
        return previewURL;
    }

    public void setPreviewURL(String previewURL)
    {
        this.previewURL = previewURL;
    }

    public String getInfoURL()
    {
        return infoURL;
    }

    public void setInfoURL(String infoURL)
    {
        this.infoURL = infoURL;
    }

    public String getThumbnailURL()
    {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL)
    {
        this.thumbnailURL = thumbnailURL;
    }

}
