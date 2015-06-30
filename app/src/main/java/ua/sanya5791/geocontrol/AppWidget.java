package ua.sanya5791.geocontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import ua.sanya5791.geocontrol.model.SafeAndLoadPreferences;


/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {
    public static final String TAG = AppWidget.class.getSimpleName();

    // there are names of Intent's Actions
    public static final String ACTION_WIDGET_SOS_OFF = "ActionWidgetSOSOff";
    public static final String ACTION_WIDGET_SOS_READY = "ActionWidgetSOSReady";
    public static final String ACTION_WIDGET_SOS_ON = "ActionWidgetSOSOn";
    private static final String ACTION_WIDGET_SIRENA_PRESSED = "ActionWidgetSirena";    //is used when sirena button was clicked

    //    private static String buttonSOSState = ACTION_WIDGET_SOS_OFF;
    //how many times SOS button was pressed
    private static int  buttonSOSCount = 0;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Log.i(TAG, "updateAppWidget(): ");


        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

//        setupSOSButton(context, views);

        //start SetupActivity on button pressed
        Intent startSetupActivity = new Intent(context, SetupActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, startSetupActivity, 0);
        views.setOnClickPendingIntent(R.id.ib_setup, configPendingIntent);

        setupSirenaButton(context, views);

        setupSOSButton(context, views);

        // Instruct the widget manager to update the widget
//        Log.i(TAG, "Instruct the widget manager to update the widget: appWidgetManager.updateAppWidget(appWidgetId, views)");
//        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void setupSirenaButton(Context context, RemoteViews views) {
        //Подготавливаем Intent для Broadcast
        Intent intent = new Intent(context, AppWidget.class);
        intent.setAction(ACTION_WIDGET_SIRENA_PRESSED);
        //создаем наше событие
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.ib_alarm, actionPendingIntent);
    }

    /**
     * Setup SOS Button;
     * create individual PendingIntent for every next step user presses the wodget sos button.
     * @param context
     * @param views
     */
    private static void setupSOSButton(Context context, RemoteViews views) {

        Log.i(TAG, "setupSOSButton(): ");

        //Подготавливаем Intent для Broadcast
        Intent intentSOSOff = new Intent(context, AppWidget.class);
        intentSOSOff.setAction(ACTION_WIDGET_SOS_OFF);
        //создаем наше событие
        PendingIntent actionPendingIntentOff = PendingIntent.getBroadcast(context, 0, intentSOSOff, 0);

        Intent intentSOSReady = new Intent(context, AppWidget.class);
        intentSOSReady.setAction(ACTION_WIDGET_SOS_READY);
        PendingIntent actionPendingIntentReady = PendingIntent.getBroadcast(context, 0, intentSOSReady, 0);

        Intent intentSOSOn = new Intent(context, AppWidget.class);
        intentSOSOn.setAction(ACTION_WIDGET_SOS_ON);
        PendingIntent actionPendingIntentOn = PendingIntent.getBroadcast(context, 0, intentSOSOn, 0);

        //регистрируем наше событие
        if(buttonSOSCount == 0) {
            views.setOnClickPendingIntent(R.id.ib_sos, actionPendingIntentReady);
            buttonSOSCount = 1;                 //this will be next action
        }else if(buttonSOSCount == 1){
            views.setOnClickPendingIntent(R.id.ib_sos, actionPendingIntentOn);
            buttonSOSCount = 2;                 //this will be next action
        }else if(buttonSOSCount == 2){
            views.setOnClickPendingIntent(R.id.ib_sos, actionPendingIntentOff);
            buttonSOSCount = 0;                 //this will be next action
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        // Instruct the widget manager to update the widget
//        Log.i(TAG, "Instruct the widget manager to update the widget: appWidgetManager.updateAppWidget(appWidgetId, views)");
        appWidgetManager.updateAppWidget(new ComponentName(context, AppWidget.class), views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.i(TAG, "onReceive(): ");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        AppWidgetManager appWidgetManager = AppWidgetManager
                .getInstance(context);

        //Ловим наш Broadcast
        final String action = intent.getAction();
        Log.i(TAG, "action = '" + action + "'");
        if (action.equals(ACTION_WIDGET_SOS_OFF)) {
            views.setImageViewResource(R.id.ib_sos, R.drawable.sos1_button);
            Toast.makeText(context, "SOS mode is ready", Toast.LENGTH_LONG).show();
            setupSOSButton(context, views);
        }else if (action.equals(ACTION_WIDGET_SOS_READY)) {
            views.setImageViewResource(R.id.ib_sos, R.drawable.sos2_button);
            Toast.makeText(context, "SOS mode is turned On", Toast.LENGTH_LONG).show();
            startSOSService(context);
            setupSOSButton(context, views);
        }else if (action.equals(ACTION_WIDGET_SOS_ON)) {
            views.setImageViewResource(R.id.ib_sos, R.drawable.sos0_button);
            Toast.makeText(context, "SOS mode is turned Off", Toast.LENGTH_LONG).show();
            setupSOSButton(context, views);
        }else if (action.equals(ACTION_WIDGET_SIRENA_PRESSED)) {
            sirenaPlay(context);
//            views.setImageViewResource(R.id.ib_sos, R.drawable.sos1_button);
//            Toast.makeText(context, "Turn On", Toast.LENGTH_LONG).show();
//            setupSOSButton(context, views);
        }
    }

    private void startSOSService(Context context) {
        MyIntentService.startActionFoo(context, "param1", "param2");
    }

    private void sirenaPlay(Context context) {
        SharedPreferences preferences = context.
                getSharedPreferences(SafeAndLoadPreferences.PREFS_NAME, Context.MODE_PRIVATE);

        int sirenaId = preferences.getInt(SafeAndLoadPreferences.SIRENA_NUM_INT, 0);

        //if sirena was nat set in setup screen
        if(sirenaId == 0){
            Toast.makeText(context, "Choose a sirena sound in setup screen", Toast.LENGTH_LONG).show();
            return;
        }

        MediaPlayer mediaPlayer = MediaPlayer.create(context, sirenaId);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you

    }
}

