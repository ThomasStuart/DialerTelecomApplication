package com.example.telecomapplication1;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.PhoneAccount;
import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MyConnectionService extends ConnectionService {



    Context mContext;
    TelecomManager mTelecomManager;
    PhoneAccountHandle mPhoneAccountHandle = new PhoneAccountHandle(new ComponentName(CONSTANTS.PACKAGE_NAME, CONSTANTS.COMPONENT_NAME), "1234");
    PhoneAccount mPhoneAccount;

    public MyConnectionService() {
        mContext = getApplicationContext();
        mTelecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        mPhoneAccount = new PhoneAccount.Builder(mPhoneAccountHandle, "PhoneAccountCharSeqLabel").build();
    }

    public MyConnectionService(Context context) {
        mContext = context;
        mTelecomManager = (TelecomManager) mContext.getSystemService(Context.TELECOM_SERVICE);
        mPhoneAccount = new PhoneAccount.Builder(mPhoneAccountHandle, "PhoneAccountCharSeqLabel").build();
    }


    @Override
    public Connection onCreateOutgoingHandoverConnection(PhoneAccountHandle
                                                                 fromPhoneAccountHandle, ConnectionRequest request) {
        MyConnection connection = new MyConnection();
        System.out.println("Call Was Placed Successfully. @ " + request.getAddress() );
        connection.setAddress(request.getAddress(), TelecomManager.PRESENTATION_ALLOWED);
        connection.setVideoState(request.getVideoState());

        return connection;
    }


    @Override
    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle connectionManagerPhoneAccount,
                                                 ConnectionRequest request) {
        try {
            throw new Exception("Connection Failed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public PhoneAccountHandle getPhoneAccountHandle(){
        return mPhoneAccountHandle;
    }



}
