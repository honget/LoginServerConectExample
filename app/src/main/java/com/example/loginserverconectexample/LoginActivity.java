package com.example.loginserverconectexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.util.Log;
import android.view.View;

import com.example.loginserverconectexample.databinding.ActivityLoginBinding;
import com.example.loginserverconectexample.utills.CommectSever;
import com.example.loginserverconectexample.utills.ContextUtill;

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
                ContextUtill.setUserInputPw(mContext, pw);

                CommectSever.postRequestSingIn(mContext, id, pw, new CommectSever.JsonResponsHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("결과", json.toString());

                    }
                });
            }
        });
    }

    @Override
    public void setValues() {

        String savedUserId = ContextUtill.getUserInputId(mContext);
        String savedUserPw = ContextUtill.getUserInputPw(mContext);

        act.idTxtV.setText(savedUserId);
        act.pwdTxtV.setText(savedUserPw);

    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
