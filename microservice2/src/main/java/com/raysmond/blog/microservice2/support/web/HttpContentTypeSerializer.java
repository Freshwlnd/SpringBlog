package com.raysmond.blog.microservice2.support.web;

import java.util.Hashtable;

public class HttpContentTypeSerializer {

    private static final Hashtable<String, String> variants = new Hashtable<String, String>();

    private static final String defaultVariant = "application/octet-stream";

    static {
        variants.put(".aac", "audio/aac");
        variants.put(".abw", "application/x-abiword");
        variants.put(".arc", "application/octet-stream");
        variants.put(".avi", "video/x-msvideo");
        variants.put(".azw", "application/vnd.amazon.ebook");
        variants.put(".bin", "application/octet-stream");
        variants.put(".bz", "application/x-bzip");
        variants.put(".bz2", "application/x-bzip2");
        variants.put(".csh", "application/x-csh");
        variants.put(".css", "text/css");
        variants.put(".csv", "text/csv");
        variants.put(".doc", "application/msword");
        variants.put(".eot", "application/vnd.ms-fontobject");
        variants.put(".epub", "application/epub+zip");
        variants.put(".gif", "image/gif");
        variants.put(".htm", "text/html");
        variants.put(".html", "text/html");
        variants.put(".ico", "image/x-icon");
        variants.put(".ics", "text/calendar");
        variants.put(".jar", "application/java-archive");
        variants.put(".jpeg", "image/jpeg");
        variants.put(".jpg", "image/jpeg");
        variants.put(".js", "application/javascript");
        variants.put(".json", "application/json");
        variants.put(".mid", "audio/midi");
        variants.put(".midi", "audio/midi");
        variants.put(".mpeg", "video/mpeg");
        variants.put(".mpkg", "application/vnd.apple.installer+xml");
        variants.put(".odp", "application/vnd.oasis.opendocument.presentation");
        variants.put(".ods", "application/vnd.oasis.opendocument.spreadsheet");
        variants.put(".odt", "application/vnd.oasis.opendocument.text");
        variants.put(".oga", "audio/ogg");
        variants.put(".ogv", "video/ogg");
        variants.put(".ogx", "application/ogg");
        variants.put(".otf", "font/otf");
        variants.put(".png", "image/png");
        variants.put(".pdf", "application/pdf");
        variants.put(".ppt", "application/vnd.ms-powerpoint");
        variants.put(".rar", "application/x-rar-compressed");
        variants.put(".rtf", "application/rtf");
        variants.put(".sh", "application/x-sh");
        variants.put(".svg", "image/svg+xml");
        variants.put(".swf", "application/x-shockwave-flash");
        variants.put(".tar", "application/x-tar");
        variants.put(".tif", "image/tiff");
        variants.put(".tiff", "image/tiff");
        variants.put(".ts", "video/vnd.dlna.mpeg-tts");
        variants.put(".ttf", "font/ttf");
        variants.put(".vsd", "application/vnd.visio");
        variants.put(".wav", "audio/x-wav");
        variants.put(".weba", "audio/webm");
        variants.put(".webm", "video/webm");
        variants.put(".webp", "image/webp");
        variants.put(".woff", "font/woff");
        variants.put(".woff2", "font/woff2");
        variants.put(".xhtml", "application/xhtml+xml");
        variants.put(".xls", "application/vnd.ms-excel");
        variants.put(".xml", "application/xml");
        variants.put(".xul", "application/vnd.mozilla.xul+xml");
        variants.put(".zip", "application/zip");
        variants.put(".7z", "application/x-7z-compressed");
    }

    public static String getContentType(String fileName) {

        if (fileName.isEmpty()) {
            return defaultVariant;
        }

        String fileArray[] = fileName.split("\\.");

        String extension = "." + fileArray[fileArray.length - 1];

        if (variants.containsKey(extension)) {
            return variants.get(extension);
        }

        return defaultVariant;
    }


}
