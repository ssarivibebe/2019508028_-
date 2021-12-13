package com.jh.afinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jh.afinal.adapters.MembersAdapter;
import com.jh.afinal.models.Member;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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

        // [구현사항 3번. 팝업, 대화상자 사용]
        // 아이템이 클릭될 때, 팝업 메뉴를 보여준다
        adapter.setOnItemSelectedListener(position -> {
            // 클릭된 멤버 확인
            Member member = adapter.getCurrentList().get(position);
            // 클릭된 아이템 뷰 획득
            RecyclerView.ViewHolder viewHolder = recycler.findViewHolderForAdapterPosition(position);
            View itemView = viewHolder != null ? viewHolder.itemView : recycler;
            // 팝업 메뉴 띄우기
            PopupMenu popupMenu = new PopupMenu(requireContext(), itemView, Gravity.NO_GRAVITY);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.action_information) {
                    // 팝업 메뉴 - 정보 보기가 클릭되면 대화상자를 보여준다
                    showMemberDialog(member);
                } else if (id == R.id.action_hide) {
                    // 팝업 메뉴 - 숨기기가 클릭되면 해당 멤버를 삭제한다
                    members.remove(member);
                    adapter.notifyItemRemoved(position);
                }
                return true;
            });
            popupMenu.show();
        });

        // [구현사항 5번. 인텐트 사용]
        // Welcome 버튼 클릭 시 Welcome 액티비티로 이동한다
        ImageButton imageButtonWelcome = view.findViewById(R.id.welcome);
        imageButtonWelcome.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), WelcomeActivity.class);
            startActivity(intent);
        });

        // [구현사항 1번. 3개 이상 액티비티 사용]
        // 파트너 찾기 버튼 클릭 시 SearchingDetail 액티비티로 이동한다
        ImageButton imageButtonSearch = view.findViewById(R.id.partnersearch);
        imageButtonSearch.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), SearchingDetailActivity.class);
            startActivity(intent);
        });
    }

    // 멤버의 정보를 대화상자로 보여준다
    private void showMemberDialog(Member member) {

        // 대화상자 뷰를 inflate 한다
        View view = View.inflate(requireContext(), R.layout.dialog_member, null);
        TextView textViewName = view.findViewById(R.id.textViewMemberName);
        TextView textViewJob = view.findViewById(R.id.textViewMemberJob);
        TextView textViewYears = view.findViewById(R.id.textViewMemberYears);
        ImageView imageViewProfile = view.findViewById(R.id.imageViewMemberProfile);

        // 멤버 정보를 뷰에 입력한다
        textViewName.setText(member.getName());
        textViewJob.setText(member.getJob());

        String strYears = "경력 " + member.getYears() + "년";
        textViewYears.setText(strYears);

        imageViewProfile.setImageResource(member.getProfileId());

        // 대화상자를 띄운다
        new AlertDialog.Builder(requireContext())
                .setView(view)
                .setPositiveButton("확인", null)
                .show();
    }

}