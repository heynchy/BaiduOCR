
package com.heynchy.baiduocr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.heynchy.baiduocr.Utils.OCRFileUtil;
import com.heynchy.baiduocr.Utils.RecognizeManager;
import com.heynchy.baiduocr.resultInterface.ResultListener;

import java.io.File;

import static com.heynchy.baiduocr.Utils.Constant.REC_ACCURATE_LOCATION;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_ACCURATE;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_BASIC;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_ENHANCED;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_LOCATION;


/**
 * 百度OCR文字识别接口的具体实现方式
 */

public class BaiduOCRUtil {
    private static boolean hasToken = false;

    /**
     * 打开百度提供的UI界面
     *
     * @param context  上下文对象
     * @param type  通用识别类型
     */
    public static void openOCRWithOcrUi(Context context, int type) {
        if (!isHasToken()) {
            return;
        }
        switch (type) {
            case REC_GENERAL_BASIC:    // 通用文字识别对应的UI界面
            case REC_GENERAL_ACCURATE: // 通用文字识别对应的UI界面----高精度
            case REC_GENERAL_LOCATION: // 通用文字识别---带位置信息
            case REC_ACCURATE_LOCATION:// 通用文字识别---高精度且带位置信息
            case REC_GENERAL_ENHANCED: // 通用文字识别---生僻字
                Intent intent = new Intent(context, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        OCRFileUtil.getSaveFile(context).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                ((Activity) context).startActivityForResult(intent, type);
                break;
        }
    }

    /**
     * 进行图片的识别和信息的返回
     *
     * @param type     识别的类型
     * @param filePath 图片的路径
     * @param listener 监听事件
     */
    public static void recognizeOCR(int type, String filePath, ResultListener listener) {
        if (!isHasToken()) {
            listener.onError("OCR token 失效！");
            return;
        }
        if (TextUtils.isEmpty(filePath)) {
            listener.onError("文件路径不存在");
            return;
        }
        switch (type) {
            case REC_GENERAL_BASIC:
                // 通用文字识别
                RecognizeManager.recGeneralBasic(filePath, listener);
                break;
            case REC_GENERAL_ACCURATE:
                // 通用文字识别（高精度版）
                RecognizeManager.recAccurateBasic(filePath, listener);
                break;
            case REC_GENERAL_LOCATION:
                // 通用文字识别（带位置信息）
                RecognizeManager.recGeneral(filePath, listener);
                break;
            case REC_ACCURATE_LOCATION:
                // 通用文字识别（高精度带位置信息）
                RecognizeManager.recAccurate(filePath, listener);
                break;
            case REC_GENERAL_ENHANCED:
                // 通用文字识别（生僻字版）
                RecognizeManager.recGeneralEnhanced(filePath, listener);
                    break;
        }
    }

    /**
     * 进行图片的识别和信息的返回
     *
     * @param type     识别的类型
     * @param file     图片的文件
     * @param listener 监听事件
     */
    public static void recognizeOCR(int type, File file, ResultListener listener) {
        if (!isHasToken()) {
            listener.onError("OCR token 失效！");
            return;
        }
        switch (type) {
            case REC_GENERAL_BASIC:
                // 通用文字识别
                RecognizeManager.recGeneralBasic(file, listener);
                break;
            case REC_GENERAL_ACCURATE:
                // 通用文字识别（高精度版）
                RecognizeManager.recAccurateBasic(file, listener);
                break;
            case REC_GENERAL_LOCATION:
                // 通用文字识别（带位置信息）
                RecognizeManager.recGeneral(file, listener);
                break;
            case REC_ACCURATE_LOCATION:
                // 通用文字识别（高精度带位置信息）
                RecognizeManager.recAccurate(file, listener);
                break;
            case REC_GENERAL_ENHANCED:
                // 通用文字识别（生僻字版）
                RecognizeManager.recGeneralEnhanced(file, listener);
                break;
        }
    }

    /**
     * 验证百度OCR是否可用（是否能够获取到OCR的token）
     * <p>
     * 注意：此种获取token的方式，由于AK/SK是明文填写在代码中，
     * 在移动设备中可能会存在AK/SK被盗取的风险，所以安全性
     * 不高
     */
    public static void initAccessTokenWithAkSk(Context context, String ak, String sk) {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                /**
                 * TODO 此处如果要进行操作，请确保在主线程中进行，否则会发生错误
                 *    this.runOnUiThread(new Runnable() {
                 *        @Override
                 *       public void run() {
                 *       显示，弹窗等操作
                 *      }
                 *  }
                 */
                hasToken = true;
                Log.e("Baidu_OCR", "初始化===Baidu_OCR Token===成功");
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                hasToken = false;
                Log.e("Baidu_OCR", "初始化===Baidu_OCR Token===失败");
            }
        }, context, ak, sk);
    }

    /**
     * 此种身份验证方案使用授权文件获得AccessToken，缓存在本地
     * <p>
     * 注意：在您的移动APP分发出去之后，APP存在被反编译的可能，
     * 所以直接将AK / SK 置于APP源码之中，存在被盗取的风险。
     * 采用授权文件的身份验证方法，可有效保护AK/SK在移动设备中的安全。
     * 攻击者即使拦截了流量，盗取了授权文件，也难以盗用您的配额。
     */
    public static void initAccessToken(final Context context) {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(final AccessToken accessToken) {
                /**
                 * TODO 此处如果要进行操作，请确保在主线程中进行，否则会发生错误
                 *    this.runOnUiThread(new Runnable() {
                 *        @Override
                 *       public void run() {
                 *       显示，弹窗等操作
                 *      }
                 *  }
                 */
                hasToken = true;
                Log.e("Baidu_OCR", "初始化===Baidu_OCR Token===成功");
            }

            @Override
            public void onError(OCRError ocrError) {
                hasToken = false;
                Log.e("Baidu_OCR", "初始化===Baidu_OCR Token===失败");
            }
        }, context);
    }

    /**
     * 释放内存资源
     */
    public static void release() {
        if (OCR.getInstance() != null) {
            OCR.getInstance().release();
            hasToken = false;
        }
    }

    public static boolean isHasToken() {
        return hasToken;
    }
}
