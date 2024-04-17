package com.likelion.oegaein.domain.member.entity.review;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Semester {
    _18_1("2018년 1학기"),
    _18_2("2018년 2학기"),
    _19_1("2019년 1학기"),
    _19_2("2019년 2학기"),
    _20_1("2020년 1학기"),
    _20_2("2020년 2학기"),
    _21_1("2021년 1학기"),
    _21_2("2021년 2학기"),
    _22_1("2022년 1학기"),
    _22_2("2022년 2학기"),
    _23_1("2023년 1학기"),
    _23_2("2023년 2학기"),
    _24_1("2024년 1학기");

    private final String value;
}
