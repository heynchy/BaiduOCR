package com.chy.baiduocrdemo.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chy.baiduocrdemo.Activity.util.BasicUtils;
import com.chy.baiduocrdemo.R;
import com.heynchy.baiduocr.BaiduOCRUtil;
import com.heynchy.baiduocr.Utils.OCRFileUtil;
import com.heynchy.baiduocr.event.ResultEvent;
import com.heynchy.baiduocr.resultInterface.ResultListener;

import static com.heynchy.baiduocr.Utils.Constant.REC_ACCURATE_LOCATION;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_ACCURATE;


/**
 * 通用文字识别服务高精度版的Activity-------高精度 带位置信息
 *
 * @author CHY
 * Create at 2017/12/6 15:57.
 */
public class RecAccurateWithLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mBeginTv;            // 开始按钮
    private TextView mIntroduceTv;        // 基本的介绍
    private TextView mResultTv;           // 显示分析的结果
    private TextView mTitleTv;            // 显示标题

    /**
     * 静态跳转至该界面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, RecAccurateWithLocationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_general);
        initView();
    }

    /**
     * 初始化控件和UI
     */
    private void initView() {
        mBeginTv = (TextView) findViewById(R.id.tv_begin_recognize);
        mIntroduceTv = (TextView) findViewById(R.id.tv_general_introduce);
        mResultTv = (TextView) findViewById(R.id.tv_show_result);
        mIntroduceTv.setText("免费版-----高精度的识别图片中的文字信息，高精度带位置信息；每天最高50次识别");
        mTitleTv = findViewById(R.id.tv_title);
        mTitleTv.setText(BasicUtils.getTitle(REC_GENERAL_ACCURATE));
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
            case R.id.tv_begin_recognize:  // 通用文字识别---高精度版
                BaiduOCRUtil.openOCRWithOcrUi(this, REC_ACCURATE_LOCATION);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别----高精度
        if (requestCode == REC_ACCURATE_LOCATION && resultCode == Activity.RESULT_OK) {
            BaiduOCRUtil.recognizeOCR(REC_ACCURATE_LOCATION,
                    OCRFileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new ResultListener() {
                        @Override
                        public void onResult(ResultEvent result) {
                            /**
                             *  文字解析结果 result.getResultWorld()
                             *  文字位置信息  result.getLocationJson()
                             */
                            mResultTv.setText(result.getResultWorld());
                            Log.i("chy", "result.location: " + result.getLocationJson());
                        }

                        @Override
                        public void onError(String error) {
                            mResultTv.setText(error);
                        }
                    });
        }
    }


}