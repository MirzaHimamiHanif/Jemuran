package org.pindad.jemuran.history.datahistory;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.history.modelhistory.ListDataTanggal;
import org.pindad.jemuran.history.modelhistory.ListHistory;
import org.pindad.jemuran.sensor.modelsensor.ListSensor;
import org.pindad.jemuran.util.Interactor;
import org.pindad.jemuran.util.MyApplication;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetHistoryData {
    private Interactor interactor;

    public void syncHistory(){
        FirebaseDatabase database;
        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(MyApplication.getAppContext())).child("history");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ListHistory> listHistory = new ArrayList<>();
                ArrayList<ListDataTanggal> listDataTanggal = new ArrayList<>();
                int i = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    try {
                        listHistory.add(snapshot.getValue(ListHistory.class));
                        listDataTanggal
                                .add(new ListDataTanggal(
                                        getHari(listHistory.get(i).getWaktu_awal()),
                                        getHour(listHistory.get(i).getWaktu_awal()),
                                        getHour(listHistory.get(i).getWaktu_akhir()),
                                        String.valueOf(listHistory.get(i).getWaktu_rata())));
                        i++;
                    }catch (Exception e){

                    }
                }
                interactor.onSyncArrayHistory(listDataTanggal);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }

    public String getHour(long tanggal){
        Date date = new Date(tanggal*1000);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }
    public String getHari(long tanggal){
        Date date = new Date(tanggal*1000);
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }
    public void registerInteractot(Interactor networkInteractor){
        this.interactor = networkInteractor;
    }
}
