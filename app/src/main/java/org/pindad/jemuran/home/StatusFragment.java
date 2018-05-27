package org.pindad.jemuran.home;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.history.HistoryActivity;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.home.sistem.SistemViewModel;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.R;
import org.pindad.jemuran.home.status.StatusViewModel;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

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
    boolean cek;
    private StatusViewModel statusViewModel;
    private SistemViewModel sistemViewModel;

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
        statusViewModel = ViewModelProviders.of(getActivity()).get(StatusViewModel.class);
        sistemViewModel = ViewModelProviders.of(getActivity()).get(SistemViewModel.class);
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

    private void firebaseSetUp(){
        statusViewModel.getListStatusMutableLiveData().observe(getActivity(), new Observer<ListStatus>() {
            @Override
            public void onChanged(@Nullable ListStatus listStatus) {
                setTextView(mAlarm, listStatus.getAlarm());
                setTextView(mAtap, listStatus.getAtap());
                setTextView(mKipas, listStatus.getKipas());
            }
        });
        sistemViewModel.getListSistemMutableLiveData().observe(getActivity(), new Observer<ListSistem>() {
            @Override
            public void onChanged(@Nullable ListSistem listSistem) {
                mSistemJemuran.setChecked(mListSistem.getSistem_jemuran());
                mSistemAntiMaling.setChecked(mListSistem.getSistem_anti_maling());
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
        FirebaseDatabase database;
        DatabaseReference myRef;
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(SaveSharedPreference.getUserName(getContext())).child("sistem");
        if (compoundButton==mSistemJemuran){
            myRef.child("sistem_jemuran").setValue(b);
        }
        if (compoundButton==mSistemAntiMaling){
            myRef.child("sistem_anti_maling").setValue(b);
        }
    }

}