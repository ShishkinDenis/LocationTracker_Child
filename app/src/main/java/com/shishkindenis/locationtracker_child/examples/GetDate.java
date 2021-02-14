package com.shishkindenis.locationtracker_child.examples;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
//    public static Date getDateWithoutTimeUsingFormat()
//            throws ParseException {
//        SimpleDateFormat formatter = new SimpleDateFormat(
//                "MM/dd/yyyy");
//        return formatter.format();
//    }

    public static void main(String[] args) throws ParseException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        System.out.println(dateFormat.format(new Date()));
    }
}
