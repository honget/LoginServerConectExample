package com.example.loginserverconectexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.example.loginserverconectexample.databinding.ActivityLoginBinding;
import com.example.loginserverconectexample.utills.CommectSever;
import com.example.loginserverconectexample.utills.ContextUtill;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setValues();
        setupEvents();

    }

    @Override
    public void setupEvents() {

        act.singBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 1. 아이디 비번 조회
                 *
                 * 1.2 - 입력 받은 ID 를 저장 처리
                 *
                 * 2. 받아온 아이디 비번 확인
                 *    아이디 확인
                 *    비번 확인
                 *
                 *    test123 / Test!123
                 *    iu0001 / Test!123
                 *    testorder1 / Testorder1
                 *    gggg1111 / gggg1111
                 */
                String id = act.idTxtV.getText().toString();
                String pw = act.pwdTxtV.getText().toString();

                ContextUtill.setUserInputId(mContext, id);

                CommectSever.postRequestSingIn(mContext, id, pw, new CommectSever.JsonResponsHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        Log.d("결과", json.toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int code = json.getInt("code");

                                    if( code == 200 ){
                                        // 로그인 성공

                                        JSONObject data = json.getJSONObject("data");
                                        String token = data.getString("token");


                                        //자동 로그인 체크 확인
                                        if(act.autoLginChkB.isChecked()){

                                            //자동 로그인 토큰 저장
                                            ContextUtill.setUserToken(mContext, token);
                                        }

                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        intent.putExtra("USER_TOKEN", token);
                                        startActivity(intent);

                                        finish();

                                    } else {
                                      // 로그인 실패 원인 리턴

                                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

                                        alert.setTitle("로그인 실패 알림");
                                        alert.setMessage(json.getString("message"));
                                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                act.pwdTxtV.setText("");
                                            }
                                        });
                                        alert.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void setValues() {

        String savedUserId = ContextUtill.getUserInputId(mContext);

        act.idTxtV.setText(savedUserId);

        String userToken = ContextUtill.getUserToken(mContext);
        Log.d("저장된 토큰 값", String.format("값은 %s",userToken));

    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
