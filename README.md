## BaiduOCR
简单的了解一下百度OCR文字识别SDK的集成，环境的配置和相关接口的使用
#### 百度OCR通用文字识别功能介绍：   
     1. 通用文字识别功能（调用百度自带的UI方案）-----通用识别
     2. 高精度通用文字识别功能（调用百度自带的UI方案）-----高精度识别
     3. 对本地的图片进行识别
### Usage
#### 1. Add dependency
```groovy
   dependencies {
       implementation 'com.github.heynchy:BaiduOCR:0.0.3'
   }
```
#### 2. 配置信息
    在百度OCR的官网注册时，如果生成了权限文件--aip.license; 可将该文件拷贝至工程的assets文件夹中
    （此文件与文字识别的token验证方式（第一种验证方式）有关）;
    如果不拷贝该文件则，在第三步需要使用第二种验证方式
#### 3. 使用时要先验证功能是否可用，一般放在onCreate()中执行----两种验证方式任选一种
```java
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
        BaiduOCRUtil.initAccessTokenWithAkSk(this,
                "simWLUnb7R8keKGF7cnsllMq",
                "lI5GlFDYGva3YUicsfMRH0q07FC79wzH");
```
#### 4. 使用完毕后，释放OCR的相关资源-----一般放在onDestory()中执行
```java
      // 释放内存资源
       BaiduOCRUtil.release();
```
#### 5. 功能介绍
##### 5.1 通用识别类型 (文字识别的相关类型)---识别图片采用的方式---识别方法中的type参数
    REC_GENERAL_BASIC = 100;    // 通用文字识别-----无位置信息 50000次/天
    REC_GENERAL_ACCURATE = 101; // 通用文字识别(高精度版)--无位置信息 500次/天
    REC_GENERAL_LOCATION = 102；// 通用文字识别-----带位置信息 500次/天
    
##### 5.2 通过百度提供的相关UI进行文字识别功能的实现（拍照界面由百度的UI提供的）

```java
    /**
     * 打开百度提供的UI界面----BaiduOCRUtil
     *
     * @param context  上下文对象
     * @param type  通用识别类型(也作为onActivityResult 的 requestCode)----（5.1中的类型）
     */
    BaiduOCRUtil.openOCRWithOcrUi(Context context, int type)
```
```java
    /**
     *  在onActivityResult的方法中获取图片的返回并进行文字识别处理
     *  
     * @param requestCode   此处的返回参数应该等于通用文字识别的类型
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 识别成功回调，通用文字识别（含位置信息）---REC_GENERAL_BASIC 为打开UI界面时的type
        if (requestCode == REC_GENERAL_BASIC && resultCode == Activity.RESULT_OK) {
            BaiduOCRUtil.recognizeOCR(REC_GENERAL_BASIC,
                    OCRFileUtil.getSaveFile(getApplicationContext()).getAbsolutePath(),
                    new ResultListener() {
                        @Override
                        public void onResult(ResultEvent result) {
                            /**
                             *  文字解析结果 result.getResultWorld()
                             *  文字位置信息  result.getLocationJson()
                             */
                            mResultTv.setText(result.getResultWorld());
                        }

                        @Override
                        public void onError(String error) {
                            mResultTv.setText(error);
                        }
            });
        }
    }
```
##### 5.3 本地图片识别（直接识别某一张图片）
```java
   /**
     * 进行图片的识别和信息的返回------方法1---传filePath
     *
     * @param type      通用识别类型（5.1中的类型）
     * @param filePath  图片的路径
     * @param listener  监听事件
     */
 BaiduOCRUtil.recognizeOCR(REC_GENERAL_BASIC, filePath, new ResultListener() {
     @Override
     public void onResult(ResultEvent result) {
         /**
          *  文字解析结果 result.getResultWorld()
          *  文字位置信息  result.getLocationJson()-----仅针对有位置信息返回的方法，没有位置信息返回的为null
          */
     }

     @Override
     public void onError(String error) {
     }
 });
 
 /**
  * 进行图片的识别和信息的返回------方法2--- 传File
  *
  * @param type      通用识别类型
  * @param file      图片的文件
  * @param listener  监听事件
  */
  BaiduOCRUtil.recognizeOCR(REC_GENERAL_BASIC, filePath, new ResultListener() {
      @Override
      public void onResult(ResultEvent result) {
          /**
           *  文字解析结果 result.getResultWorld()
           *  文字位置信息  result.getLocationJson()----仅针对有位置信息返回的方法，没有位置信息返回的为null
           */
      }

      @Override
      public void onError(String error) {
         mResultTv.setText(error);
      }
  });
```
   

License
-------
    Copyright 2019 heynchy

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

