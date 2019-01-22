package com.heynchy.baiduocr.resultInterface;

import com.heynchy.baiduocr.event.ResultEvent;

/**
 * @author CHY
 *         Create at 2017/12/12 14:23.
 */

public interface ResultListener {
    void onResult(ResultEvent result);
    void onError(String error);
}
