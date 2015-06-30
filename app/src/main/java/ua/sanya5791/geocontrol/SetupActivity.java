package ua.sanya5791.geocontrol;

import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;

import ua.sanya5791.geocontrol.model.SafeAndLoadPreferences;


public class SetupActivity extends ActionBarActivity
                                implements AddNewItemFragmentDialog.GetText{

    private ImageButton ibSirenaMale, ibSirenaFemale, ibSirenaBell;

    //Contact names
    private ImageView ivContacts;
    private TextView tvContact1, tvContact2, tvContact3, tvContact4, tvContact0;
    private TextView tvContactNum1, tvContactNum2,tvContactNum3,tvContactNum4, tvContactNum0;
    //use it to know that FragmentDialog was called to add a contact
    private boolean mAddContactPressed;

    //Facebook contacts
    private ImageView ivFbContacts;
    private TextView tvFbContact0, tvFbContact1, tvFbContact2, tvFbContact3, tvFbContact4;
    private TextView tvFbContactId0, tvFbContactId1, tvFbContactId2,tvFbContactId3,tvFbContactId4;
    //use it to know that FragmentDialog was called to add a contact
    private boolean mAddFbContactPressed;

    private SafeAndLoadPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        preferences = new SafeAndLoadPreferences(this);

        setupIbSirenas();
        setupSMS();
        setupFb();
    }

    /**
     * setup Facebook Views
     */
    private void setupFb() {
        String contact, id;

        tvFbContact0 = (TextView) findViewById(R.id.tv_fb0);
        tvFbContact1 = (TextView) findViewById(R.id.tv_fb1);
        tvFbContact2 = (TextView) findViewById(R.id.tv_fb2);
        tvFbContact3 = (TextView) findViewById(R.id.tv_fb3);
        tvFbContact4 = (TextView) findViewById(R.id.tv_fb4);

        tvFbContactId0 = (TextView) findViewById(R.id.tv_fb_id0);
        tvFbContactId1 = (TextView) findViewById(R.id.tv_fb_id1);
        tvFbContactId2 = (TextView) findViewById(R.id.tv_fb_id2);
        tvFbContactId3 = (TextView) findViewById(R.id.tv_fb_id3);
        tvFbContactId4 = (TextView) findViewById(R.id.tv_fb_id4);


        //get sms contacts from preferences
        LinkedList<HashMap<String, String>> fb = preferences.getFb();

        //set all TextViews
        SharedPreferences settings = preferences.getSettings();

        contact = settings.getString(preferences.FB_NAME_STR + 0, "");
        id = settings.getString(preferences.FB_ID_STR + 0, "");
        tvFbContact0.setText(contact);
        tvFbContactId0.setText(id);


        contact = settings.getString(preferences.FB_NAME_STR + 1, "");
        id = settings.getString(preferences.FB_ID_STR + 1, "");
        tvFbContact1.setText(contact);
        tvFbContactId1.setText(id);

        contact = settings.getString(preferences.FB_NAME_STR + 2, "");
        id = settings.getString(preferences.FB_ID_STR + 2, "");
        tvFbContact2.setText(contact);
        tvFbContactId2.setText(id);

        contact = settings.getString(preferences.FB_NAME_STR + 3, "");
        id = settings.getString(preferences.FB_ID_STR + 3, "");
        tvFbContact3.setText(contact);
        tvFbContactId3.setText(id);

        contact = settings.getString(preferences.FB_NAME_STR + 4, "");
        id = settings.getString(preferences.FB_ID_STR + 4, "");
        tvFbContact4.setText(contact);
        tvFbContactId4.setText(id);

        final Context context = this.getApplicationContext();
        ivFbContacts = (ImageView) findViewById(R.id.iv_facebook);
        ivFbContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the dialog fragment and show it
                mAddFbContactPressed = true;
                DialogFragment dialog = new AddNewItemFragmentDialog();
                dialog.show(getFragmentManager(), "AddNewItemFragmentDialog");
            }
        });
    }

    private void setupSMS() {

        tvContact0 = (TextView) findViewById(R.id.tv_contact0);
        tvContact1 = (TextView) findViewById(R.id.tv_contact1);
        tvContact2 = (TextView) findViewById(R.id.tv_contact2);
        tvContact3 = (TextView) findViewById(R.id.tv_contact3);
        tvContact4 = (TextView) findViewById(R.id.tv_contact4);

        tvContactNum0 = (TextView) findViewById(R.id.tv_contact_num0);
        tvContactNum1 = (TextView) findViewById(R.id.tv_contact_num1);
        tvContactNum2 = (TextView) findViewById(R.id.tv_contact_num2);
        tvContactNum3 = (TextView) findViewById(R.id.tv_contact_num3);
        tvContactNum4 = (TextView) findViewById(R.id.tv_contact_num4);

        ivContacts = (ImageView) findViewById(R.id.iv_contacts);
        ivContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an instance of the dialog fragment and show it
                mAddContactPressed = true;
                DialogFragment dialog = new AddNewItemFragmentDialog();
                dialog.show(getFragmentManager(), "AddNewItemFragmentDialog");
            }
        });

        inflateTvSMS();
//        ivContacts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), ContactsListActivity.class);
//                startActivity(i);
//            }
//        });
    }

    private void inflateTvSMS() {
        String contact, number;

        //get sms contacts from preferences
//        LinkedList<HashMap<String, String>> sms = preferences.getSMS();

        //set all TextViews
        SharedPreferences settings = preferences.getSettings();

        contact = settings.getString(preferences.SMS_NAME_STR + 0, "");
        number = settings.getString(preferences.SMS_NUMBER_STR + 0, "");
        tvContact0.setText(contact);
        tvContactNum0.setText(number);


        contact = settings.getString(preferences.SMS_NAME_STR + 1, "");
        number = settings.getString(preferences.SMS_NUMBER_STR + 1, "");
        tvContact1.setText(contact);
        tvContactNum1.setText(number);

        contact = settings.getString(preferences.SMS_NAME_STR + 2, "");
        number = settings.getString(preferences.SMS_NUMBER_STR + 2, "");
        tvContact2.setText(contact);
        tvContactNum2.setText(number);

        contact = settings.getString(preferences.SMS_NAME_STR + 3, "");
        number = settings.getString(preferences.SMS_NUMBER_STR + 3, "");
        tvContact3.setText(contact);
        tvContactNum3.setText(number);

        contact = settings.getString(preferences.SMS_NAME_STR + 4, "");
        number = settings.getString(preferences.SMS_NUMBER_STR + 4, "");
        tvContact4.setText(contact);
        tvContactNum4.setText(number);
    }

    private void setupIbSirenas() {
        ibSirenaMale = (ImageButton) findViewById(R.id.ib_male);
        ibSirenaFemale = (ImageButton) findViewById(R.id.ib_female);
        ibSirenaBell = (ImageButton) findViewById(R.id.ib_bell);

        SharedPreferences settings = preferences.getSettings();
        int sirenaNum = settings.getInt(preferences.SIRENA_NUM_INT, 100);
        //if user never chosen a sirena
        if(sirenaNum != 100){
            if(sirenaNum == preferences.SIRENA_MALE){
                ibSirenaMale.setImageResource(R.drawable.male_on);
            }else if(sirenaNum == preferences.SIRENA_FEMALE){
                ibSirenaFemale.setImageResource(R.drawable.female_on);
            }else if(sirenaNum == preferences.SIRENA_BELL){
                ibSirenaBell.setImageResource(R.drawable.bell_on);
            }
        }

        ibSirenaMale.setOnClickListener(onIbClickListener);
        ibSirenaFemale.setOnClickListener(onIbClickListener);
        ibSirenaBell.setOnClickListener(onIbClickListener);
     }

    View.OnClickListener onIbClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sirenaIbSwitcher(v);
        }
    };

    private void sirenaIbSwitcher(View view){
        MediaPlayer mediaPlayer;
//        Button b = (Button) view;
        int ibPressed = view.getId();

        ibSirenaMale.setImageResource(R.drawable.male_off);
        ibSirenaFemale.setImageResource(R.drawable.female_off);
        ibSirenaBell.setImageResource(R.drawable.bell_off);

        switch (ibPressed){
            case R.id.ib_male:
                ibSirenaMale.setImageResource(R.drawable.male_on);
                mediaPlayer = MediaPlayer.create(this, R.raw.begi_ili_srazhajsya);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
                preferences.saveSirenaNum(preferences.SIRENA_MALE);

                return;
            case R.id.ib_female:
                ibSirenaFemale.setImageResource(R.drawable.female_on);
                mediaPlayer = MediaPlayer.create(this, R.raw.best_friend);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
                preferences.saveSirenaNum(preferences.SIRENA_FEMALE);

                return;
            case R.id.ib_bell:
                ibSirenaBell.setImageResource(R.drawable.bell_on);
                mediaPlayer = MediaPlayer.create(this, R.raw.bell_sms);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
                preferences.saveSirenaNum(preferences.SIRENA_BELL);

                return;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getNameAndNum(String name, String num) {
        if(mAddContactPressed){
            mAddContactPressed = false;
            preferences.putSMSContact(name, num);
            inflateTvSMS();
        }

        if(mAddFbContactPressed){
            mAddFbContactPressed = false;
            preferences.putFbContact(name, num);
            setupFb();
        }
    }
}

