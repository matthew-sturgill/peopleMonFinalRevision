package mattsturgill.peoplemonfinal.Network;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

import mattsturgill.peoplemonfinal.Constants.Constants;
import mattsturgill.peoplemonfinal.PeoplemonApplication;

/**
 * Created by matthewsturgill on 11/26/16.
 */

public class UserStore {
    private static UserStore ourInstance = new UserStore();

    public static UserStore getInstance() {
        return ourInstance;
    }

    private SharedPreferences sharedPreferences = PeoplemonApplication.getInstance()
            .getSharedPreferences("PeoplemonPrefs", Context.MODE_PRIVATE);

    public String getToken() { //
        String theToken = sharedPreferences.getString(Constants.token, null);
        return theToken;
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.token, token);
        editor.apply();
    }

    public Date getTokenExpiration() {
        Long expiration = sharedPreferences.getLong(Constants.tokenExpiration, 0);
        Date date = new Date(expiration);
        if (date.before(new Date())) {
            return null;
        }
        return date;
    }

    public void setTokenExpiration(Date expiration) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(Constants.tokenExpiration, expiration.getTime());
        editor.apply();
    }
}
