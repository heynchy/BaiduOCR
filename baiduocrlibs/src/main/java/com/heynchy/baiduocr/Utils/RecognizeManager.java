package com.heynchy.baiduocr.Utils;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.sdk.model.GeneralBasicParams;
import com.baidu.ocr.sdk.model.GeneralParams;
import com.baidu.ocr.sdk.model.GeneralResult;
import com.baidu.ocr.sdk.model.OcrRequestParams;
import com.baidu.ocr.sdk.model.OcrResponseResult;
import com.baidu.ocr.sdk.model.Word;
import com.baidu.ocr.sdk.model.WordSimple;
import com.heynchy.baiduocr.event.ResultEvent;
import com.heynchy.baiduocr.resultInterface.ResultListener;

import java.io.File;

/**
 * Author: Heynchy
 * Date:   2019/1/17
 * <p>
 * Introduce:
 */
public class RecognizeManager {

    /**
     * 处理各个解析事件的返回监听
     *
     * @param result
     * @param listener
     */
    private static void resultInfo(GeneralResult result, ResultListener listener, boolean basic) {
        StringBuilder sb = new StringBuilder();
        for (WordSimple wordSimple : result.getWordList()) {
            if (basic) {
                WordSimple word = wordSimple;
                sb.append(word.getWords());
            } else {
                Word word = (Word) wordSimple;
                sb.append(word.getWords());
            }
            sb.append("\n");
        }
        ResultEvent resultEvent = new ResultEvent();
        resultEvent.setLocationJson(result.getJsonRes());
        resultEvent.setResultWorld(sb.toString());
        listener.onResult(resultEvent);
    }

    /**
     * 通用文字识别 (有位置信息)
     *
     * @param filePath 图片路径
     * @param listener 监听器
     */
    public static void recGeneral(String filePath, final ResultListener listener) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeGeneral(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, false);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 (有位置信息)
     *
     * @param file     图片文件
     * @param listener 监听器
     */
    public static void recGeneral(File file, final ResultListener listener) {
        if (file == null || !file.exists()) {
            listener.onError("文件不存在");
            return;
        }
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(file);
        OCR.getInstance().recognizeGeneral(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, false);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别---高精度且带位置信息版
     *
     * @param filePath
     * @param listener
     */
    public static void recAccurate(String filePath, final ResultListener listener) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeAccurate(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, false);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别---高精度且带位置信息版
     *
     * @param file
     * @param listener
     */
    public static void recAccurate(File file, final ResultListener listener) {
        if (file == null || !file.exists()) {
            listener.onError("文件不存在");
            return;
        }
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(file);
        OCR.getInstance().recognizeAccurate(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, false);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 (高精度版, 无位置信息)-----更高精度地识别图片中的文字信息
     *
     * @param filePath
     * @param listener
     */
    public static void recAccurateBasic(String filePath, final ResultListener listener) {
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeAccurateBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 (高精度版, 无位置信息)-----更高精度地识别图片中的文字信息
     *
     * @param file
     * @param listener
     */
    public static void recAccurateBasic(File file, final ResultListener listener) {
        if (file == null || !file.exists()) {
            listener.onError("文件不存在");
            return;
        }
        GeneralParams param = new GeneralParams();
        param.setDetectDirection(true);
        param.setVertexesLocation(true);
        param.setRecognizeGranularity(GeneralParams.GRANULARITY_SMALL);
        param.setImageFile(file);
        OCR.getInstance().recognizeAccurateBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }


    /**
     * 通用文字识别 (非高精度，无位置信息)---识别图片中的文字信息
     *
     * @param filePath
     * @param listener
     */
    public static void recGeneralBasic(String filePath, final ResultListener listener) {
        File file = new File(filePath);
        if (!file.exists()) {
            listener.onError("文件不存在");
            return;
        }
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));


        // 调用通用文字识别服务, 返回的result需要自己解析一下
        OCR.getInstance().recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 (非高精度，无位置信息)---识别图片中的文字信息
     *
     * @param file
     * @param listener
     */
    public static void recGeneralBasic(File file, final ResultListener listener) {
        if (file == null || !file.exists()) {
            listener.onError("该文件不存在");
            return;
        }
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(file);


        // 调用通用文字识别服务, 返回的result需要自己解析一下
        OCR.getInstance().recognizeGeneralBasic(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 ----- 生僻字版
     * @param filePath
     * @param listener
     */
    public static void recGeneralEnhanced(String filePath, final ResultListener listener) {
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeGeneralEnhanced(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    /**
     * 通用文字识别 ----- 生僻字版
     * @param file
     * @param listener
     */
    public static void recGeneralEnhanced(File file, final ResultListener listener) {
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(file);
        OCR.getInstance().recognizeGeneralEnhanced(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                resultInfo(result, listener, true);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recWebimage(String filePath, final ResultListener listener) {
        GeneralBasicParams param = new GeneralBasicParams();
        param.setDetectDirection(true);
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeWebimage(param, new OnResultListener<GeneralResult>() {
            @Override
            public void onResult(GeneralResult result) {
                StringBuilder sb = new StringBuilder();
                for (WordSimple wordSimple : result.getWordList()) {
                    WordSimple word = wordSimple;
                    sb.append(word.getWords());
                    sb.append("\n");
                }
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recBankCard(String filePath, final ResultListener listener) {
        BankCardParams param = new BankCardParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeBankCard(param, new OnResultListener<BankCardResult>() {
            @Override
            public void onResult(BankCardResult result) {
                String res = String.format("卡号：%s\n类型：%s\n发卡行：%s",
                        result.getBankCardNumber(),
                        result.getBankCardType().name(),
                        result.getBankName());
//                listener.onResult(res);
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recVehicleLicense(String filePath, final ResultListener listener) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeVehicleLicense(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recDrivingLicense(String filePath, final ResultListener listener) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeDrivingLicense(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recLicensePlate(String filePath, final ResultListener listener) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeLicensePlate(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recBusinessLicense(String filePath, final ResultListener listener) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        OCR.getInstance().recognizeBusinessLicense(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }

    public static void recReceipt(String filePath, final ResultListener listener) {
        OcrRequestParams param = new OcrRequestParams();
        param.setImageFile(new File(filePath));
        param.putParam("detect_direction", "true");
        OCR.getInstance().recognizeReceipt(param, new OnResultListener<OcrResponseResult>() {
            @Override
            public void onResult(OcrResponseResult result) {
//                listener.onResult(result.getJsonRes());
            }

            @Override
            public void onError(OCRError error) {
                listener.onError(error.getMessage());
            }
        });
    }
}
