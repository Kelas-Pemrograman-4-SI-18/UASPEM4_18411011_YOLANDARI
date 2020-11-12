package com.example.penjualanjilbab.server;

public class BaseURL {

    public static String baseUrl = "http://192.168.43.237:5050/";

    public static String login = baseUrl + "user/login";

    public static String register = baseUrl + "user/registrasi";

    //jilbab
    public static String lihatdatajilbab = baseUrl + "hijab/datajilbab";
    public static String updatehijab = baseUrl + "hijab/update/";
    public static String deletehijab = baseUrl + "hijab/delete/";
    public static String InputDataJilbab = baseUrl + "hijab/input";

}
