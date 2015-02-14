package com.ftfl.myvisitingplaces.util;

import java.io.File;

import android.content.Context;

public class FileCache {
    private File cacheDir;

    public FileCache(Context context) {
        super();
    }

    public FileCache(Context context, long evt) {
        //Find the dir to save cached images
        cacheDir = context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public File getFile(String url) {
        return new File(cacheDir, String.valueOf(url.hashCode()));
    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }
}