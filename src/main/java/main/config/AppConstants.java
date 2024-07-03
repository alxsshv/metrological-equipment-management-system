package main.config;

import lombok.Getter;

@Getter
public class AppConstants {

    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_SORT_BY = "id";
    public static final String DEFAULT_PAGE_SORT_DIR = "asc";
    public static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg","png","gif","bmp","svg"};
    public static final String HUMIDITY_UNIT = "%";
    public static final String PRESSURE_UNIT = "кПа";
    public static final String TEMPERATURE_UNIT = "°C";

}
