package com.example.telecomapplication1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.view.View;
import android.widget.Button;

@RequiresApi(api = Build.VERSION_CODES.S)
public class MainActivity extends AppCompatActivity {

    MyConnectionService mConnectionService;
    MyConnection mConnection;

    Context mContext;
    TelecomManager mTelecomManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        mConnectionService = new MyConnectionService(getApplicationContext());

        // init start button
        Button startCallButton = (Button) findViewById(R.id.StartCallButton);
        startCallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    System.out.println("Clicked Start Call Button");
                    startPhoneCall();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // init end button
        Button endCallButton = (Button) findViewById(R.id.EndCallButton);
        endCallButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                endPhoneCall();
            }
        });
    }

    public void startPhoneCall() {
        Uri uri = Uri.fromParts("tel", CONSTANTS.PHONE_NUMBER, null);
        Bundle extras = new Bundle();

        mTelecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions( this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
        }

        mTelecomManager.placeCall(uri, extras);
        System.out.println("startPhoneCall finished");
    }

    public void endPhoneCall(){
        if (mConnection != null ) {
            DisconnectCause disconnectCause = new DisconnectCause(DisconnectCause.LOCAL);
            mConnection.setDisconnected(disconnectCause);
            mConnection.destroy();
            System.out.println("Deleted success");
        }
        else{
            System.out.println("mConnection was null so deletion not done");
        }
    }
}