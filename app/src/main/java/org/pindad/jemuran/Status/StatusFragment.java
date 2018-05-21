package org.pindad.jemuran.Status;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.Authentification.LoginActivity;
import org.pindad.jemuran.HistoryActivity;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.Status.ModelStatus.ListStatus;
import org.pindad.jemuran.R;

/**
 * Created by ASUS on 11/02/2018.
 */

public class StatusFragment extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    private TextView mAlarm, mAtap, mKipas, mSistem;
    private ImageView lemari;
    private ListStatus mListStatus;
    private Long mBoolSistem;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    boolean cek;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        mSistem = view.findViewById(R.id.switchSistem);
        mAlarm = view.findViewById(R.id.statusAlarm);
        mAtap = view.findViewById(R.id.statusAtap);
        mKipas = view.findViewById(R.id.statusKipas);
        mListStatus = new ListStatus();
        mSistem.setOnClickListener(this);
        cek=true;
        mAlarm.setClickable(false);
        lemari = view.findViewById(R.id.lemari);
        lemari.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        firebaseSetUp();
        return view ;
    }

    private void firebaseSetUp() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(((MainActivity)getActivity()).username).child("alat");
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
    }

    private TextView setTextView(TextView textView, long status){
        if (status==0){
            textView.setText("Off");
        }else{
            textView.setText("On");
        }
        return textView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


    }

    @Override
    public void onClick(View view) {
        if (view==mSistem){

        }
    }
}