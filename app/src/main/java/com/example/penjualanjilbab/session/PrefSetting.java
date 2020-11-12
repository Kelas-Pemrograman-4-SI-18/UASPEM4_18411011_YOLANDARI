package com.example.penjualanjilbab.session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.se.omapi.Session;

import com.example.penjualanjilbab.admin.HomeAdminActivity;
import com.example.penjualanjilbab.pembeli.HomePembeli;

public class PrefSetting {

    public static String _id;
    public static String userName;
    public static String namaLengkap;
    public static String email;
    public static String noHp;
    public static String role;
    Activity activity;

    public PrefSetting(Activity activity){
        this.activity = activity;
    }
    public SharedPreferences getSharedPreferences(){
        SharedPreferences preferences = activity.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        return preferences;
    }
    public void isLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        if(session.isLoggedIn()){
            pref = getSharedPreferences();
            _id = pref.getString("_id", "");
            userName = pref.getString("userName", "");
            namaLengkap = pref.getString("namaLengkap", "");
            email = pref.getString("email", "");
            noHp = pref.getString("noHp", "");
            role = pref.getString("role", "");
        } else {
            session.setLogin(false);
            session.setSessid(0);
            Intent i = new Intent(activity, activity.getClass());
            activity.startActivity(i);
            activity.finish();
        }
    }
    public void checkLogin(SessionManager session, SharedPreferences pref){
        session = new SessionManager(activity);
        _id = pref.getString("_id", "");
        userName = pref.getString("userName", "");
        namaLengkap = pref.getString("namaLengkap", "");
        email = pref.getString("email", "");
        noHp = pref.getString("noHp", "");
        role = pref.getString("role", "");
        if (session.isLoggedIn()){
            if (role.equals("1")){
                Intent i = new Intent(activity, HomeAdminActivity.class);
                activity.startActivity(i);
                activity.finish();
            }else {
                Intent i = new Intent(activity, HomePembeli.class);
                activity.startActivity(i);
                activity.finish();
            }
        }
    }
    public void storeRegIdSharedPreferences(Context context, String _id, String userName,
                                            String namaLengkap, String email, String noHp, String role, SharedPreferences prefs){

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("_id", _id);
        editor.putString("userName", userName);
        editor.putString("namaLengkap", namaLengkap);
        editor.putString("email", email);
        editor.putString("noHp", noHp);
        editor.putString("role", role);
        editor.commit();

    }
}
