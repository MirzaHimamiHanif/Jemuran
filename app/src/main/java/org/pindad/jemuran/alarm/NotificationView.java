package org.pindad.jemuran.alarm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;
import org.pindad.jemuran.authentification.LoginActivity;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

public class NotificationView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_view);
        FirebaseDatabase database;
        final DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext()));

        AlertDialog.Builder builder = new AlertDialog.Builder(NotificationView.this);
        builder.setMessage("Sistem apa yang ingin di jalankan?")
                .setCancelable(false)
                .setPositiveButton("Otomatis", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myRef.child("sistem").child("sistem_jemuran").setValue(true);
                        finish();
                    }
                })
                .setNegativeButton("Forecast", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        myRef.child("sistem").child("sistem_jemuran").setValue(false);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
