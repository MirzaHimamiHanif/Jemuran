package org.pindad.jemuran.home.sistem.datasistem;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.R;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

public class GetSistemData {
    private Interactor interactor;

    public void syncSistem(){
        FirebaseDatabase database;
        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext())).child("sistem");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    interactor.onSyncData(dataSnapshot.getValue(ListSistem.class));
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
