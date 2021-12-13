package com.jh.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);

        // [구현사항 5번. 인텐트 사용]
        // 전체 뷰를 구하고, 뷰가 클릭되면 액티비티 종료
        View root = findViewById(R.id.root);
        root.setOnClickListener(v -> finish());
    }

}