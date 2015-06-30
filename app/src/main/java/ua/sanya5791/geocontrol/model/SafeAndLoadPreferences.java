package ua.sanya5791.geocontrol.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;

import ua.sanya5791.geocontrol.R;

/**
 * Created by Sanya on 28.06.2015.
 */
public class SafeAndLoadPreferences {
    public static final String TAG = SafeAndLoadPreferences.class.getSimpleName();
    public static final String PREFS_NAME = "prefs";

    //SMS fields
    public static final String SMS_NAME_STR = "smsName";
    public static final String SMS_NUMBER_STR = "smsNumber";
    private static final int SMS_CAPACITY = 5;                  //capacity of numbers (this is only LinkedList restriction)
    //Facebook fields
    public static final String FB_NAME_STR = "fbName";
    public static final String FB_ID_STR = "FbId";
    private static final int FB_CAPACITY = 5;                  //capacity of numbers (this is only LinkedList restriction)
    //Sirena fields
    public static final String SIRENA_NUM_INT = "sirena";
    public static final int SIRENA_MALE = R.raw.begi_ili_srazhajsya;
    public static final int SIRENA_FEMALE = R.raw.best_friend;
    public static final int SIRENA_BELL = R.raw.bell_sms;


    private final Context context;

    private SharedPreferences mSettings;
    private LinkedList<HashMap<String, String>> mSms, mFb;

    public SafeAndLoadPreferences(Context context){
        this.context = context;
        mSms = new LinkedList<HashMap<String, String>>();
        mFb = new LinkedList<HashMap<String, String>>();

        loadSms();
        mSettings = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);

    }

    /**
     * safe contacts list from mSms to SharedPreferences
     */
    private void saveSms(){
        SharedPreferences.Editor editor = mSettings.edit();

        // Получаем набор элементов
        int i=0;
        ListIterator<HashMap<String, String>> iterator = mSms.listIterator();
        for (HashMap<String, String> entry : mSms) {
            for (String name : entry.keySet()) {
                editor.putString(SMS_NAME_STR + String.valueOf(i), name)                             //save name to preferences
                        .putString(SMS_NUMBER_STR + String.valueOf(i), entry.get(name).toString());   //save number to preferences
            }
            i++;
        }

        // Commit the edits!
        editor.commit();
    }

    /**
     * safe FB contacts list from mFb to SharedPreferences
     */
    private void saveFb(){
        SharedPreferences.Editor editor = mSettings.edit();

        // Получаем набор элементов
        int i=0;
        ListIterator<HashMap<String, String>> iterator = mFb.listIterator();
        for (HashMap<String, String> entry : mFb) {
            for (String name : entry.keySet()) {
                editor.putString(FB_NAME_STR + String.valueOf(i), name)                             //save Facebook name to preferences
                        .putString(FB_ID_STR + String.valueOf(i), entry.get(name).toString());   //save Facebook id to preferences
            }
            i++;
        }

        // Commit the edits!
        editor.commit();
    }

    /**
     * save in preferences number of the sirena the user's choosen
     * @param num
     */
    public void saveSirenaNum(int num){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(SIRENA_NUM_INT, num);

        editor.commit();
    }

    /**
     * load SMS contacts from preferences to mSms
     */
    private void loadSms(){
        String key, value;

        for(int i=0; i<mSms.size(); i++){
            key= mSettings.getString(SMS_NAME_STR + String.valueOf(i), "");
            value= mSettings.getString(SMS_NUMBER_STR +String.valueOf(i), "");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(key, value);
            mSms.add(map);
        }
    }

    /**
     * load Facebook contacts from preferences to mSms
     */
    private void loadFb(){
        String key, value;

        for(int i=0; i<mFb.size(); i++){
            key= mSettings.getString(FB_NAME_STR + String.valueOf(i), "");
            value= mSettings.getString(FB_ID_STR +String.valueOf(i), "");
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(key, value);
            mFb.add(map);
        }
    }


    /**
     *
     * @return sms contacts from preferences
     */
    public LinkedList<HashMap<String, String>> getSMS(){
        return mSms;
    }

    /**
     *
     * @return Facebook contacts from preferences
     */
    public LinkedList<HashMap<String, String>> getFb(){
        return mFb;
    }

    public void putSMSContact(String name, String num) {
        HashMap<String, String> map;
        Log.i(TAG, "putSMSContact():");

        if(mSms.size()<SMS_CAPACITY) {
            map = new HashMap<>();
            map.put(name, num);
            mSms.add(map);
        }else{
            ListIterator<HashMap<String, String>> iterator = mSms.listIterator();
            map = new HashMap<>();
            map.put(name, num);
            iterator.add(map);
        }
        saveSms();

    }

    public void putFbContact(String name, String fbId) {
        HashMap<String, String> map;
        Log.i(TAG, "putSMSContact():");

        if(mFb.size() < FB_CAPACITY) {
            map = new HashMap<>();
            map.put(name, fbId);
            mFb.add(map);
        }else{
            ListIterator<HashMap<String, String>> iterator = mFb.listIterator();
            map = new HashMap<>();
            map.put(name, fbId);
            iterator.add(map);
        }
        saveFb();

    }

    /**
     *
     * @return link to Shared preferences
     */
    public SharedPreferences getSettings(){
        return mSettings;
    }
}
