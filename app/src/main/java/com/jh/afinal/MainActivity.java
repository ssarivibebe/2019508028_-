package com.jh.afinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jh.afinal.adapters.MembersAdapter;
import com.jh.afinal.brs.NoticeReceiver;
import com.jh.afinal.models.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private Fragment currentFragment;
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private SearchingFragment searchingFragment;
    private MyPageFragment myPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 바텀 메뉴 리스너 규현
        BottomNavigationView bottomNavigationView = findViewById(R.id.btmenu);
        bottomNavigationView.setOnItemSelectedListener(this);

        // 프래그먼트 생성
        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        searchingFragment = new SearchingFragment();
        myPageFragment = new MyPageFragment();

        // 처음 home 프래그먼트 띄우기
        showFragment(homeFragment);

        // [구현사항 6번. 방송 수신자 이용]
        // 공지사항을 60초마다 발송한다
        String[] notices = {
                "이벤트 참여하면 바나나 10 묶음 사은품",
                "Linfitner 뉴스 - 현재 기온 영하 5도. 추위 조심하세요",
                "[공지사항] 2023년 5월 서버 점검 예정",
                "앱 업데이트하고 갤럭시 폴드 받자",
        };

        new CountDownTimer(86400 * 1000, 60000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // 방송을 내용과 함께 송신한다. 이 방송을 NoticeReceiver 가 수신해서 알림을 띄운다
                // brs 패키지 NoticeReceiver.java 참조
                Intent intent = new Intent(MainActivity.this, NoticeReceiver.class);
                int random = new Random().nextInt(notices.length);
                intent.putExtra("content", notices[random]);
                sendBroadcast(intent);
            }

            @Override
            public void onFinish() {
            }
        }.start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.home) {
            showFragment(homeFragment);
        } else if (id == R.id.searching) {
            showFragment(searchingFragment);
        } else if (id == R.id.chat) {
            showFragment(chatFragment);
        } else if (id == R.id.mypage) {
            showFragment(myPageFragment);
        }

        return true;
    }

    private void showFragment(Fragment fragment) {

        currentFragment = fragment;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, currentFragment)
                .commit();
    }

}






