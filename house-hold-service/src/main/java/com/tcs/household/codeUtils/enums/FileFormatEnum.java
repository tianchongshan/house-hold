package com.tcs.household.codeUtils.enums;

/**
 * @author luodong
 * @create 2019-02-16 10:22
 **/
public enum FileFormatEnum {
    PDF(".pdf", "application/pdf"),

    JPG(".jpg", "image/jpeg"),

    JPEG(".jpeg", "image/jpeg"),

    PNG(".png", "image/png"),

    GIF(".gif", "image/gif"),

    BMP(".bmp", "application/x-bmp"),;

    private String suffix;
    private String contentType;

    FileFormatEnum(final String suffix, final String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

    public static String getName(String suffix) {
        for (FileFormatEnum c : FileFormatEnum.values()) {
            if (c.getSuffix().equals(suffix)) {
                return c.contentType;
            }
        }
        return null;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getContentType() {
        return contentType;
    }
}
