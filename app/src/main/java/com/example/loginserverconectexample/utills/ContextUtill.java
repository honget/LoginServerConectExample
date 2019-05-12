package com.example.loginserverconectexample.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class ContextUtill {

    /**
     * 메모 파일정보 변수
     */
    private  static final String PREF_NM = "LoginServerExamplePref";

    /**
     * 이 메모에서 다루는 항목들의 리스트를 변수로 생성.
     */
    private  static final String USER_INPUT_ID = "USER_INPUT_ID";
    private  static final String USER_TOKEN = "USER_TOKEN";

    /**
     * 실제로 각각의 항목을 저장/불러오는 기능
     */

     // setter : ID를 저장하는 id_setter
    public static void setUserInputId(Context context, String inputId){

        // 메모장 파일을 여는 작업
        SharedPreferences pref = context.getSharedPreferences(PREF_NM, Context.MODE_PRIVATE);

        // 실제 데이터 저장

        //수정 시작
        pref.edit().putString(USER_INPUT_ID, inputId).apply();

    }

    // getter : 저장된 id 있으면 불러오는 기능
    public static String getUserInputId(Context context){

        // 메모장 파일을 여는 작업
        SharedPreferences pref = context.getSharedPreferences(PREF_NM, Context.MODE_PRIVATE);

        // 키, null 일경우 값
        return pref.getString(USER_INPUT_ID, "");
    }

    // setter : TOKEN를 저장하는 token_setter
    public static void setUserToken(Context context, String inputToken){

        // 메모장 파일을 여는 작업
        SharedPreferences pref = context.getSharedPreferences(PREF_NM, Context.MODE_PRIVATE);

        // 실제 데이터 저장

        //수정 시작
        pref.edit().putString(USER_TOKEN, inputToken).apply();

    }

    // getter : 저장된 Token 있으면 불러오는 기능
    public static String getUserToken(Context context){

        // 메모장 파일을 여는 작업
        SharedPreferences pref = context.getSharedPreferences(PREF_NM, Context.MODE_PRIVATE);

        // 키, null 일경우 값
        return pref.getString(USER_TOKEN, "");
    }

}
