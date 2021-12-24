package be.scryper.sos.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import be.scryper.sos.R;

public class SessionManager  {

    private SharedPreferences prefs;

    public SessionManager(Context context){
        prefs = context.getSharedPreferences(context.getString(R.string.app_name),Context.MODE_PRIVATE);
    }


    public static String USER_TOKEN = "user_token";

    public void saveAuthToken(String token){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(USER_TOKEN,token);
        editor.apply();
    }

    public String fetchAuthToken(){
        return prefs.getString(USER_TOKEN,null);
    }

}
