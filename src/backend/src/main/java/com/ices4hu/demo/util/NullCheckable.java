package com.ices4hu.demo.util;

import java.util.NoSuchElementException;

public interface NullCheckable {
    void nullCheck() throws NullPointerException, NoSuchElementException;
}
