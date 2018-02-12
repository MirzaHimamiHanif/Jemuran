package org.pindad.jemuran.Jemuran;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.Jemuran.ModulJemuran.ListJemuran;
import org.pindad.jemuran.R;
import org.pindad.jemuran.Status.ModelStatus.ListStatus;

/**
 * Created by ASUS on 11/02/2018.
 */

public class JemuranFragment extends Fragment {
    private Switch mAlarm, mAtap, mKipas;
    private ListJemuran mListJemuran;
    FirebaseDatabase database;
    DatabaseReference myRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jemuran, container, false);
        mAlarm = view.findViewById(R.id.switchAlarm);
        mAtap = view.findViewById(R.id.switchAtap);
        mKipas = view.findViewById(R.id.switchKipas);
        mListJemuran = new ListJemuran();

        firebaseSetUp();
        return view ;
    }

    private void firebaseSetUp() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("alat");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    mListJemuran = dataSnapshot.getValue(ListJemuran.class);
                    setSwitch(mAlarm, mListJemuran.getAlarm());
                    setSwitch(mAtap, mListJemuran.getAtap());
                    setSwitch(mKipas, mListJemuran.getKipas());
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }

        });

    }

    private Switch setSwitch(Switch mSwitch, long status){
        if (status==0){
            mSwitch.setChecked(false);
        }else{
            mSwitch.setChecked(true);
        }
        return mSwitch;
    }
}