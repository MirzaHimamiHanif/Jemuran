package org.pindad.jemuran.Status;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.History.HistoryActivity;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.Status.ModelStatus.ListSistem;
import org.pindad.jemuran.Status.ModelStatus.ListStatus;
import org.pindad.jemuran.R;

/**
 * Created by ASUS on 11/02/2018.
 */

public class StatusFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private TextView mAlarm, mAtap, mKipas;
    private Switch mSistemJemuran, mSistemAntiMaling;
    private ImageView lemari;
    private ListStatus mListStatus;
    private ListSistem mListSistem;
    private Long mBoolSistem;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    boolean cek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        mSistemJemuran = view.findViewById(R.id.switchSistemJemuran);
        mSistemAntiMaling = view.findViewById(R.id.switchSistemAntiMaling);
        mAlarm = view.findViewById(R.id.statusAlarm);
        mAtap = view.findViewById(R.id.statusAtap);
        mKipas = view.findViewById(R.id.statusKipas);
        mListStatus = new ListStatus();
        mListSistem = new ListSistem();
        cek=true;
        mAlarm.setClickable(false);
        lemari = view.findViewById(R.id.lemari);
        lemari.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                intent.putExtra("username", ((MainActivity)getActivity()).username);
                startActivity(intent);
            }
        });
        mSistemAntiMaling.setOnCheckedChangeListener(this);
        mSistemJemuran.setOnCheckedChangeListener(this);
        firebaseSetUp();
        return view ;
    }

    private void firebaseSetUp() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(((MainActivity)getActivity()).username).child("alat");
        myRef2 = database.getReference().child(((MainActivity)getActivity()).username).child("sistem");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    mListStatus = dataSnapshot.getValue(ListStatus.class);
                    setTextView(mAlarm, mListStatus.getAlarm());
                    setTextView(mAtap, mListStatus.getAtap());
                    setTextView(mKipas, mListStatus.getKipas());
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListSistem = dataSnapshot.getValue(ListSistem.class);
                mSistemJemuran.setChecked(mListSistem.getSistem_jemuran());
                mSistemAntiMaling.setChecked(mListSistem.getSistem_anti_maling());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private TextView setTextView(TextView textView, boolean status){
        if (status==false){
            textView.setText("Off");
        }else{
            textView.setText("On");
        }
        return textView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton==mSistemJemuran){
            myRef2.child("sistem_jemuran").setValue(b);
        }
        if (compoundButton==mSistemAntiMaling){
            myRef2.child("sistem_anti_maling").setValue(b);
        }
    }

}