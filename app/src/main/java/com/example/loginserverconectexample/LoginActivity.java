package com.example.loginserverconectexample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.example.loginserverconectexample.databinding.ActivityLoginBinding;

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
                 * 2. 받아온 아이디 비번 확인
                 *    아이디 확인
                 *    비번 확인
                 *
                 *    test123 /test123
                 *    iu0001 / Test!123
                 *    testorder1 / Testorder1
                 *    gggg1111 / gggg1111
                 */

            }
        });
    }

    @Override
    public void setValues() {

    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
