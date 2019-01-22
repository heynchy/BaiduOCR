package com.chy.baiduocrdemo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.chy.baiduocrdemo.Activity.util.BasicUtils;
import com.chy.baiduocrdemo.R;
import com.heynchy.baiduocr.BaiduOCRUtil;
import com.heynchy.baiduocr.event.ResultEvent;
import com.heynchy.baiduocr.resultInterface.ResultListener;

import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_BASIC;


/**
 * 通用文字识别服务的Activity-------本地图片识别
 *
 * @author CHY
 * Create at 2017/12/6 15:57.
 */
public class LocalPicRecActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mBeginTv;            // 开始按钮
    private TextView mIntroduceTv;        // 基本的介绍
    private TextView mResultTv;           // 显示分析的结果
    private TextView mTitleTv;            // 标题

    /**
     * 静态跳转至该界面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, LocalPicRecActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_rec);
        initView();
    }

    /**
     * 初始化控件和UI
     */
    private void initView() {
        mBeginTv = (TextView) findViewById(R.id.tv_begin_recognize);
        mIntroduceTv = (TextView) findViewById(R.id.tv_general_introduce);
        mResultTv = (TextView) findViewById(R.id.tv_show_result);
        mTitleTv = findViewById(R.id.tv_title);
        mTitleTv.setText(BasicUtils.getTitle(0));
        mIntroduceTv.setText("需要提供本地图片的路径");
        mBeginTv.setOnClickListener(this);
    }

    /**
     * 监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_begin_recognize:   // 通用文字识别
                // 本地图片路径===/storage/emulated/0/test.jpg -----此为测试路径和测试资源名称
                String filePath = Environment.getExternalStorageDirectory() + "/test.jpg";
                BaiduOCRUtil.recognizeOCR(REC_GENERAL_BASIC, filePath, new ResultListener() {
                    @Override
                    public void onResult(ResultEvent result) {
                        mResultTv.setText(result.getResultWorld());
                    }

                    @Override
                    public void onError(String error) {
                        mResultTv.setText(error);
                    }
                });
                break;
        }
    }
}