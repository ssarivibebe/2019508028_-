package com.jh.afinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class MyPageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mypage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [구현사항 2번. 커스텀 뷰 사용]
        // PieView.java 참조
        PieView pieView = view.findViewById(R.id.pieView);
        pieView.setPositiveValue(7);
        pieView.setNegativeValue(3);
        // 파이차트 클릭 이벤트 시 리스너 구현
        pieView.setOnClickListener((positive, negative) -> {
            String message = String.format(Locale.getDefault(),
                    "받은 요청 %d 건, 보낸 요청 %d 건이 있습니다",
                    (int) positive, (int) negative);
            Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
        });

    }
}