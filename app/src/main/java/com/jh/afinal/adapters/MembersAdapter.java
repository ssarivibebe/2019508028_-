package com.jh.afinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.jh.afinal.R;
import com.jh.afinal.models.Member;

import java.util.Locale;

// 회원 정보 리사이클러 뷰 (activity_main 의 리사이클러 뷰) 를 위한 어댑터

public class MembersAdapter extends ListAdapter<Member, MembersAdapter.MemberViewHolder> {

    class MemberViewHolder extends RecyclerView.ViewHolder {

        // 아이템 뷰에 포함된 위젯
        private final ImageView imageViewProfile;
        private final TextView textViewName;
        private final TextView textViewInformation;

        public MemberViewHolder(View itemView) {
            super(itemView);

            // 위젯을 획득한다
            imageViewProfile = itemView.findViewById(R.id.imageViewMemberProfile);
            textViewName = itemView.findViewById(R.id.textViewMemberName);
            textViewInformation = itemView.findViewById(R.id.textViewMemberInformation);

            // 클릭 리스너를 구현한다
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(position);
                }
            });
        }

        public void bind(Member model) {

            // 위젯에 멤버의 정보를 입력한다

            // 멤버 이름 입력
            textViewName.setText(model.getName());

            // 멤버 정보 입력 (ex. 개인 / 프로덕트 매니저)
            String strInformation = String.format(Locale.getDefault(), "%s / %s",
                    model.isPerson() ? "개인" : "기업",
                    model.getJob()
            );
            textViewInformation.setText(strInformation);

            // 멤버 프로필 이미지 입력
            imageViewProfile.setImageResource(model.getProfileId());
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(int position);
    }

    private OnItemSelectedListener onItemSelectedListener;


    public MembersAdapter() {
        super(new DiffUtilCallback());
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.onItemSelectedListener = listener;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // 아이템 뷰를 inflate 하여 생성한다
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.member_item, parent, false);

        // 뷰 홀더를 리턴한다
        return new MemberViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    static class DiffUtilCallback extends DiffUtil.ItemCallback<Member> {

        @Override
        public boolean areItemsTheSame(@NonNull Member oldItem, @NonNull Member newItem) {
            // 두 멤버의 이름이 같은지 판별한다
            return oldItem.getName().equals(newItem.getName());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Member oldItem, @NonNull Member newItem) {
            // 두 멤버의 모든 정보가 같은지 판별한다
            return oldItem.equals(newItem);
        }
    }

}