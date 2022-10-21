package com.felipe.todolist.domain.util;

import com.felipe.todolist.domain.model.ToDoList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isNullOrBlank(String value){
        return value == null  || value.isBlank();
    }

    public static boolean isNotNullOrBlank(String value){
        return !(value == null  || value.isBlank());
    }

    public static Boolean isNotEmail(String email) {
        String reg = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(reg);
        Matcher mather = pattern.matcher(email);
        return !mather.find();
    }
}
