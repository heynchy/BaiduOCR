package com.chy.baiduocrdemo.Activity.util;

import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_ACCURATE;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_BASIC;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_LOCATION;

/**
 * Author: Heynchy
 * Date:   2019/1/21
 * <p>
 * Introduce:
 */
public class BasicUtils {

    public static String getTitle(int type) {
        String title = "通用文字识别";
        switch (type) {
            case REC_GENERAL_BASIC:
                return "通用文字识别";
            case REC_GENERAL_LOCATION:
                return "通用文字识别--带位置信息";
            case REC_GENERAL_ACCURATE:
                return "通用文字识别--高精度";

            default:
                return "通用文字识别--本地图片";
        }

    }
}
