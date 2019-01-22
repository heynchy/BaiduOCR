package com.heynchy.baiduocr.event;

import java.io.Serializable;

/**
 * Author: Heynchy
 * Date:   2019/1/21
 * <p>
 * Introduce:
 */
public class ResultEvent implements Serializable {
    String resultWorld;   // 分析结果
    String locationJson;  // 位置信息

    public String getLocationJson() {
        return locationJson;
    }

    public void setLocationJson(String locationJson) {
        this.locationJson = locationJson;
    }

    public String getResultWorld() {
        return resultWorld;
    }

    public void setResultWorld(String resultWorld) {
        this.resultWorld = resultWorld;
    }
}
