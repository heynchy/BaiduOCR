/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.heynchy.baiduocr.Utils;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class OCRFileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "baidu_ocr.jpg");
        return file;
    }
}
