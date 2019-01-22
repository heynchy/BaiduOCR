package com.chy.baiduocrdemo.Activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chy.baiduocrdemo.Activity.util.PermissionUtil;
import com.chy.baiduocrdemo.R;
import com.heynchy.baiduocr.BaiduOCRUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mGeneralOCRTv;            // 通用文字识别按钮
    private TextView mRecLocationTv;           // 通用文字识别带位置信息按钮
    private TextView mGeneralAccurateTv;       // 通用文字识别高精度版按钮
    private TextView mAccurateLocalTv;         // 通用文字识别高精度版按钮带位置信息
    private TextView mLocalPicRecTv;           // 本地图片的识别
    private AlertDialog.Builder mAlertDialog;  // 提示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // 获取OCR的token(验证服务是否可用)---两种获取方式任选一种
        /**
         * 获取OCR的token(验证服务是否可用)---两种获取方式任选一种
         *
         * 第一种 BaiduOCRUtil.initAccessToken
         *        ------安全性好，该方式的初始化与上一步的aip.license文件有关
         *
         * 第二种 BaiduOCRUtil.initAccessTokenWithAkSk（this, ak, sk）
         *      ------ 安全性差， 该方式需要注册时获得的两个字符串（ak, sk）
         */
        BaiduOCRUtil.initAccessToken(this);
//        BaiduOCRUtil.initAccessTokenWithAkSk(this,
//                "simWLUnb7R8keKGF7cnsllMq",
//                "lI5GlFDYGva3YUicsfMRH0q07FC79wzH");
    }
    /**
     * 初始化控件和UI
     */
    private void initView() {
        mGeneralOCRTv = (TextView) findViewById(R.id.tv_general_ocr);
        mGeneralAccurateTv = (TextView) findViewById(R.id.tv_general_accurate);
        mLocalPicRecTv = findViewById(R.id.tv_picture_rec);
        mRecLocationTv = findViewById(R.id.tv_location_ocr);
        mAccurateLocalTv = findViewById(R.id.tv_accurate_local);
        mAlertDialog = new AlertDialog.Builder(this);
        mGeneralOCRTv.setOnClickListener(this);
        mGeneralAccurateTv.setOnClickListener(this);
        mLocalPicRecTv.setOnClickListener(this);
        mRecLocationTv.setOnClickListener(this);
        mAccurateLocalTv.setOnClickListener(this);
        PermissionUtil.getStoragePermissions(this);
    }

    /**
     * 监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_general_ocr:
                // 通用文字识别
                RecognizeGeneralActivity.start(this);
                break;
            case R.id.tv_general_accurate:
                // 通用文字识别高精度版
                RecGeneralAccurateActivity.start(this);
                break;
            case R.id.tv_picture_rec:
                // 本地图片识别
                LocalPicRecActivity.start(this);
                break;
            case R.id.tv_location_ocr:
                // 通用文字识别--带位置信息
                RecGeneralWithLocationActivity.start(this);
                break;
            case R.id.tv_accurate_local:
                // 通用文字识别--高精度带位置信息
                RecAccurateWithLocationActivity.start(this);
                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放内存资源
       BaiduOCRUtil.release();

    }
}