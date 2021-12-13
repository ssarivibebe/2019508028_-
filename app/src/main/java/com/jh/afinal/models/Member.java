package com.jh.afinal.models;

import java.util.Objects;

public class Member {                 // 회원 정보

    private final String name;        // 멤버 이름 (ex. louis)
    private final boolean isPerson;   // 개인/기업 여부 (true 이면 개인)
    private final String job;         // 업종명 (ex. 기업홍보)
    private final int years;          // 경력 (년)

    private final int profileId;      // 프로필 이미지 (ex. R.drawable.profile_louis)


    public Member(String name, boolean isPerson, String job, int years, int profileId) {
        this.name = name;
        this.isPerson = isPerson;
        this.job = job;
        this.years = years;
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public boolean isPerson() {
        return isPerson;
    }

    public String getJob() {
        return job;
    }

    public int getYears() {
        return years;
    }

    public int getProfileId() {
        return profileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return isPerson == member.isPerson && years == member.years && profileId == member.profileId && name.equals(member.name) && job.equals(member.job);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPerson, job, years, profileId);
    }

}
