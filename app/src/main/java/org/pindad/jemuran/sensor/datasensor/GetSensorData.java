package org.pindad.jemuran.sensor.datasensor;

import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.R;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

public class GetSensorData {
    private Interactor interactor;

    public void syncSensor(){
        FirebaseDatabase database;
        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext())).child("sensor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    interactor.onSyncData(dataSnapshot.getValue(ListSensor.class));
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
    public void registerInteractot(Interactor networkInteractor){
        this.interactor = networkInteractor;
    }
}
