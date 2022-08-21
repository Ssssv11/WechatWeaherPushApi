package com.ninn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    String wd_night;
    String date;
    String high;
    String week;
    String text_night;
    String wd_day;
    String low;
    String wc_night;
    String text_day;
    String wc_day;
    String text_now;
    String temp;
    String wind_class;
    String wind_dir;
}
