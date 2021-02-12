package com.shishkindenis.locationtracker_child.examples;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class GetDate {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
    }
}
