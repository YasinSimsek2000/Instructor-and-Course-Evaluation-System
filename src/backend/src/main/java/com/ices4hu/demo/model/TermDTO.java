package com.ices4hu.demo.model;

import com.ices4hu.demo.enums.TermType;
import lombok.Getter;

@Getter
public class TermDTO {
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    private long id;
    private String name;
    private String startDate;
    private String endDate;
    private TermType termType;
}
