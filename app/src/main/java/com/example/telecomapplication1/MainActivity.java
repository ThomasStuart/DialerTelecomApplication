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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

        // init backspace button
        ImageButton backSpaceButton = (ImageButton) findViewById(R.id.imageButton);
        backSpaceButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try{
                    deleteLastChar();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        initDialPad();
    }

    public void initDialPad(){

        Button zeroButton = (Button) findViewById(R.id.Key0);
        zeroButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.ZERO);
            }
        });

        Button oneButton = (Button) findViewById(R.id.Key1);
        oneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.ONE);
            }
        });

        Button twoButton = (Button) findViewById(R.id.Key2);
        twoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.TWO);
            }
        });

        Button threeButton = (Button) findViewById(R.id.Key3);
        threeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.THREE);
            }
        });


        Button fourButton = (Button) findViewById(R.id.Key4);
        fourButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.FOUR);
            }
        });

        Button fiveButton = (Button) findViewById(R.id.Key5);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.FIVE);
            }
        });

        Button sixButton = (Button) findViewById(R.id.Key6);
        sixButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.SIX);
            }
        });

        Button sevenButton = (Button) findViewById(R.id.Key7);
        sevenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.SEVEN);
            }
        });

        Button eightButton = (Button) findViewById(R.id.Key8);
        eightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.EIGHT);
            }
        });

        Button nineButton = (Button) findViewById(R.id.Key9);
        nineButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addDigitToDisplay(Digits.NINE);
            }
        });
    }


    public void addDigitToDisplay(Digits digitsEnum){
        Character value = ' ';

        switch (digitsEnum){
            case ZERO:
                value = '0';
                break;
            case ONE:
                value = '1';
                break;
            case TWO:
                value = '2';
                break;
            case THREE:
                value = '3';
                break;
            case FOUR:
                value = '4';
                break;
            case FIVE:
                value = '5';
                break;
            case SIX:
                value = '6';
                break;
            case SEVEN:
                value = '7';
                break;
            case EIGHT:
                value = '8';
                break;
            case NINE:
                value = '9';
                break;

        }
        EditText editText  = findViewById(R.id.editTextNumber);
        String finalText = editText.getText().toString() + value;

        editText.setText(finalText, TextView.BufferType.EDITABLE);

    }

    public void deleteLastChar(){
        EditText editText  = findViewById(R.id.editTextNumber);
        String currentText = editText.getText().toString();
        if( currentText.length() > 0) {
            String finalText = currentText.substring(0, currentText.length() - 1);
            editText.setText(finalText, TextView.BufferType.EDITABLE);
        }
    }

    public String getPhoneNumberInput(){
        EditText editText = findViewById(R.id.editTextNumber);
        return editText.getText().toString();
    }

    public void startPhoneCall() {
        Uri uri = Uri.fromParts("tel", getPhoneNumberInput(), null);
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