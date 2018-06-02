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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.pindad.jemuran.cuaca.datacuaca.GetCuacaData;
import org.pindad.jemuran.history.HistoryActivity;
import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.home.cek.CekViewModel;
import org.pindad.jemuran.home.sistem.SistemViewModel;
import org.pindad.jemuran.home.sistem.datasistem.GetSistemData;
import org.pindad.jemuran.home.sistem.modelsistem.ListSistem;
import org.pindad.jemuran.home.status.modelstatus.ListStatus;
import org.pindad.jemuran.R;
import org.pindad.jemuran.home.status.StatusViewModel;
import org.pindad.jemuran.util.sharedpreference.SaveSharedPreference;

/**
 * Created by ASUS on 11/02/2018.
 */

public class HomeFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    private TextView mAtap, mKipas;
    private Switch mSistemJemuran;
    private ImageButton lemari;
    boolean cek;
    private StatusViewModel statusViewModel;
    private SistemViewModel sistemViewModel;
    private CekViewModel cekViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_status, container, false);
        mSistemJemuran = view.findViewById(R.id.switchSistemJemuran);
        mAtap = view.findViewById(R.id.statusAtap);
        mKipas = view.findViewById(R.id.statusKipas);
        cek=true;
        statusViewModel = ViewModelProviders.of(getActivity()).get(StatusViewModel.class);
        sistemViewModel = ViewModelProviders.of(getActivity()).get(SistemViewModel.class);
        cekViewModel = ViewModelProviders.of(getActivity()).get(CekViewModel.class);

        lemari = view.findViewById(R.id.lemari);
        lemari.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        mSistemJemuran.setOnCheckedChangeListener(this);
        firebaseSetUp();
        return view ;
    }

    private void firebaseSetUp(){
        statusViewModel.getListStatusMutableLiveData().observe(getActivity(), new Observer<ListStatus>() {
            @Override
            public void onChanged(@Nullable ListStatus listStatus) {
                setTextView(mAtap, listStatus.isAtap());
                setTextView(mKipas, listStatus.isKipas());
            }
        });
        sistemViewModel.getListSistemMutableLiveData().observe(getActivity(), new Observer<ListSistem>() {
            @Override
            public void onChanged(@Nullable ListSistem listSistem) {
                mSistemJemuran.setChecked(listSistem.isSistem_jemuran());
            }
        });
        cekViewModel.getCekMutableLiveData().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                mSistemJemuran.setChecked(aBoolean);
            }
        });
    }

    private TextView setTextView(TextView textView, boolean status){
        if (!status){
            textView.setText("Off");
        }else{
            textView.setText("On");
        }
        return textView;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        GetSistemData getSistemData = new GetSistemData();
         if (compoundButton==mSistemJemuran){
             getSistemData.pushSistem(b, "sistem_jemuran");
        }
    }

}