package com.jh.afinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.afinal.adapters.MembersAdapter;
import com.jh.afinal.models.Member;

import java.util.ArrayList;
import java.util.List;

public class SearchingFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_searching, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // [구현사항 4번. 어댑터 뷰 사용]
        // 회원 목록 리사이클러 뷰를 어댑터와 연결한다
        RecyclerView recycler = view.findViewById(R.id.recyclerMember);
        MembersAdapter adapter = new MembersAdapter();
        recycler.setAdapter(adapter);
        recycler.setHasFixedSize(true);

        // 회원 목록 리스트를 생성한다
        List<Member> members = new ArrayList<>();
        members.add(new Member("Louis", true, "프로덕트 매니저", 3, R.drawable.profile_louis));
        members.add(new Member("Phillip", true, "품질 관리", 11, R.drawable.profile_phillip));
        members.add(new Member("제일제당", false, "기업 홍보", 7, R.drawable.profile_cheil));
        members.add(new Member("Microsoft", false, "Development", 37, R.drawable.profile_louis));

        // 회원 목록 리스트를 어댑터에 입력한다
        adapter.submitList(members);

}
}