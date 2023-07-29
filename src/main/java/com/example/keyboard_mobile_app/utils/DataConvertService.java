package com.example.keyboard_mobile_app.utils;

import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class DataConvertService {

    // Define the date format that matches the incoming String
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public Date convertStringToDate(String dateString) throws java.text.ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(dateString);
    }
}
