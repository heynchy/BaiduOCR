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

import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_BASIC;
import static com.heynchy.baiduocr.Utils.Constant.REC_GENERAL_LOCATION;


/**
 * 通用文字识别服务的Activity-------通用文字识别（带位置信息的）
 *
 * @author CHY
 * Create at 2017/12/6 15:57.
 */
public class RecGeneralWithLocationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitleTv;              // 标题
    private TextView mBeginTv;            // 开始按钮
    private TextView mIntroduceTv;        // 基本的介绍
    private TextView mResultTv;           // 显示分析的结果

    /**
     * 静态跳转至该界面
     *
     * @param context
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, RecGeneralWithLocationActivity.class);
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
        mIntroduceTv.setText("免费版-----识别图片中的文字信息, 非高精度，有位置信息；免费使用，每天最高500次识别");
        mTitleTv = findViewById(R.id.tv_title);
        mTitleTv.setText(BasicUtils.getTitle(REC_GENERAL_LOCATION));
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
            case R.id.tv_begin_recognize:  // 通用文字识别--带位置信息
                BaiduOCRUtil.openOCRWithOcrUi(this, REC_GENERAL_LOCATION);
                break;
        }
    }

    /**
     * 在onActivityResult的方法中获取图片的返回并进行文字识别处理
     *
     * @param requestCode 此处的返回参数应该等于通用文字识别的类型
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 识别成功回调，通用文字识别---带位置信息
        if (requestCode == REC_GENERAL_LOCATION && resultCode == Activity.RESULT_OK) {
            BaiduOCRUtil.recognizeOCR(REC_GENERAL_LOCATION,
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