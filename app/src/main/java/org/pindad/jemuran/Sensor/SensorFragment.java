package org.pindad.jemuran.Sensor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;
import org.pindad.jemuran.Sensor.ModelSensor.ListSensor;
import org.pindad.jemuran.Status.ModelStatus.ListStatus;

/**
 * Created by ASUS on 11/02/2018.
 */

public class SensorFragment extends Fragment {
    ListSensor mListSensor;
    private ListStatus mListStatus;
    TextView mStatusGetar, mStatusHujan;
    ImageView iStatusGetar, iStatusHujan;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sensor, container, false);
        mStatusGetar = view.findViewById(R.id.statusGetar);
        mStatusHujan = view.findViewById(R.id.statusHujan);

        mListSensor = new ListSensor();

        firebaseSetUp();
        return view;
    }

    private void firebaseSetUp() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(((MainActivity)getActivity()).username).child("sensor");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    mListSensor = dataSnapshot.getValue(ListSensor.class);
                    setTextView(mStatusGetar,mListSensor.getSensor_getar());
                    setTextView(mStatusHujan,mListSensor.getSensor_hujan());

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

    public TextView setTextView(TextView textView, long status){
        if (status==0){
            textView.setText("OFF");
        }else{
            textView.setText("ON");
        }
        return textView;
    }
}