package main.config;

import lombok.Getter;

@Getter
public class AppConstants {
    //pagination
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_SORT_BY = "id";
    public static final String DEFAULT_PAGE_SORT_DIR = "asc";
    public static final String[] IMAGE_EXTENSIONS = {"jpg", "jpeg","png","gif","bmp","svg"};
}
