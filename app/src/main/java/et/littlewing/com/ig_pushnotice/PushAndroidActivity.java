package et.littlewing.com.ig_pushnotice;

/**
 * Created by nickfarrow on 6/26/15.
 */
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

public class PushAndroidActivity extends Activity {

    private String TAG = "** pushAndroidActivity **";
    private TextView mDisplay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
//        String aliasEmailUsedAtSignup = "vandung_17@yahoo.com";
//        Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
//
//        Context context = null;
//        registrationIntent.putExtra("app", PendingIntent.getBroadcast(context, 0, new Intent(), 0));
//
//        registrationIntent.putExtra("sender", aliasEmailUsedAtSignup);
//
//        context.startService(registrationIntent);

        InstanceID instanceID = InstanceID.getInstance(this);
        String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId),
                GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);


        super.onCreate(savedInstanceState);

        checkNotNull(CommonUtilities.SENDER_ID, "SENDER_ID");

        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);

        setContentView(R.layout.activity_main);
        mDisplay = (TextView) findViewById(R.id.display);

        final String regId = GCMRegistrar.getRegistrationId(this);
        Log.i(TAG, "registration id =====  "+regId);

        if (regId.equals("")) {
            GCMRegistrar.register(this, CommonUtilities.SENDER_ID);
        } else {
            Log.v(TAG, "Already registered");

        }

        mDisplay.setText("Reg id is--> "+ regId);
    }

    private void checkNotNull(Object reference, String name) {
        if (reference == null) {
            throw new NullPointerException(
                    getString(R.string.error_config, name));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GCMRegistrar.unregister(this);
    }
}