package com.example.loginserverconectexample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.loginserverconectexample.databinding.ActivityMainBinding;
import com.example.loginserverconectexample.utills.CommectSever;
import com.example.loginserverconectexample.utills.ContextUtill;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {

    ActivityMainBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setValues();
        setupEvents();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        //로그인에 성공한 사람의 토큰을 받아오기
        String token = getIntent().getStringExtra("USER_TOKEN");

        Log.d("사용자 토큰 정보", token);

        //받아온 토큰을 가지고 /v2/me_info API 에서 사용자 데이터 조회 및 표시

        CommectSever.getRequestMyInfo(mContext, token, new CommectSever.JsonResponsHandler() {
            @Override
            public void onResponse(JSONObject json) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        try {
                            int code = json.getInt("code");

                            if(code == 200){

                                // 정상 수신

                                JSONObject data = json.getJSONObject("data");

                                JSONObject user = data.getJSONObject("user");
                                JSONObject bankCode = user.getJSONObject("bank_code");

                                /**
                                 * 연습 문제 1
                                 *
                                 * -로그인 me info 호출 완료
                                 * 해당 값으로 값 파싱
                                 *
                                 * 응용 문제
                                 * - 해당 과정에서 필요한 라이브러리 있다면 검색해서 활용
                                 *
                                 */
                                String userName = user.getString("name");
                                int userBalance = user.getInt("balance");
                                String userImgUrl = user.getString("profile_image");
                                String bankName = bankCode.getString("name");
                                String bankImgUrl = bankCode.getString("logo");
                                String billingAccount = user.getString("billing_account");

                                // 프로필
                                Glide.with(mContext).load(userImgUrl).into(act.usrImgV);

                                // 접속자명
                                act.usrNm.setText(userName);
                                // 보유 잔액
                                act.bankTotal.setText(String.format("%,d P", userBalance) );

                                // 은행 이미지
                                Glide.with(mContext).load(bankImgUrl).into(act.bankImgV);
                                // 은행명
                                act.bankNm.setText(bankName);
                                // 계좌 번호
                                act.bankAddr.setText(billingAccount);

                            }else{

                                // 연동 비정상
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_main);
    }
}
