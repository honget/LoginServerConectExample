package com.example.loginserverconectexample.utills;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommectSever {

    //서버의 근번 주소
    public final static String BASE_URL = "http://delivery-dev-389146667.ap-northeast-2.elb.amazonaws.com";

    /**
     * 서버의 응답을 처리하는 역할을 담당하는 인터페이스
     */
    public interface  JsonResponsHandler {

        void onResponse(JSONObject json);
    }


    /**
     * 필요한 서버 요청 하나하나 메소드 작성
     */


    /**
     * 은행 정보 조회
     * @param context
     * @param handler
     */
    public static void getRequestInfoBank(Context context, /* 필요한 파라미터 변수들 */ final  JsonResponsHandler handler ){

        // 서버 - 클리이언트 (앱)


        OkHttpClient client = new OkHttpClient();

        /**
         * 요정 정보 생성
         */
        // URL 설정 => 목적지 설정
        //HttpUrl url = HttpUrl.parse(BASE_URL + "/info/bank");
        //url.newBuilder();
        HttpUrl.Builder urlBulder = HttpUrl.parse(BASE_URL + "/info/bank").newBuilder();

        // Get, DELETE 메소드 는 필요 파라임터 url 에 담아줘야 함. 이 담는  과정을 쉽게 해줄려고 사용.

        // 실제로 서버에 접근하는 URL
        final String requestUrl = urlBulder.build().toString();

        // 완성된 url 로 접근하는 request를 생성
        Request request = new Request.Builder().url(requestUrl).build();

        /**
         * 전송
         */
        // 만들어진 Request를 실제로 서버에 요청
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {
                String responseContext = response.body().string();

                Log.d("서버 응답 내용", responseContext);

                try {
                    // String -> JSON 변환
                    JSONObject json = new JSONObject(responseContext);

                    if(handler != null){

                        // 화면에 처리하는 로직 있으면 실행
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    /**
     * 로그인 체크
     * @param context
     * @param usrId
     * @param pwd
     * @param handler
     */
    public static void postRequestSingIn(Context context, String usrId, String pwd, final  JsonResponsHandler handler ){

        //클리이언트 생성
        OkHttpClient client = new OkHttpClient();

        /**
         * POST 메소드 urlBuilder 가 아니라, RequestBody를 Build
         */

        // formData에 파라미터 첨부
        RequestBody requestBody = new FormBody.Builder()
                .add("user_id", usrId)
                .add("password", pwd)
                .build();
        /**
         * 실제 Reqset를 생성, 서버로 보낼 준비
         */
        Request request = new Request.Builder()
                                .url(BASE_URL + "/auth")
                                .post(requestBody)
                                .build();

        /**
         * 전송처리
         */
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse( Call call, Response response) throws IOException {

                String responseContext = response.body().string();

                Log.d("서버 응답 내용", responseContext);

                try {
                    // String -> JSON 변환
                    JSONObject json = new JSONObject(responseContext);

                    if(handler != null){

                        // 화면에 처리하는 로직 있으면 실행
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
