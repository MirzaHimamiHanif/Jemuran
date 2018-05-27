package org.pindad.jemuran.authentification;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.pindad.jemuran.MainActivity;
import org.pindad.jemuran.R;

/**
 * Created by ASUS on 22/03/2018.
 */

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mUsername, mPassword;
    private Button mLogin;
    private ProgressBar progressBar;
    private String username, password;
    FirebaseDatabase database;
    DatabaseReference myRef, myRef2;
    ProgressDialog progress_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mUsername = (EditText) findViewById(R.id.username);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin =(Button) findViewById(R.id.btn_login);
        progress_spinner = new ProgressDialog(LoginActivity.this);
        progress_spinner.setMessage("Loading...");
        progress_spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress_spinner.setCancelable(false);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                progress_spinner.show();
                signIn();
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        SharedPreferences sharedPref = getSharedPreferences("Shared Preference", Context.MODE_PRIVATE);
        if (sharedPref.getString("username", null)!=null){
            updateUI();
        }
    }

    private void updateUI() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
    private void signIn(){

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(username);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    String checkPassword = dataSnapshot.child("password").getValue(String.class);
                    if (checkPassword.equals(password)){
                        Context context = getApplicationContext();
                        SharedPreferences sharedPref = context.getSharedPreferences(
                                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(getString(R.string.username), username);
                        editor.commit();
                        updateUI();
                    }else {
                        Toast.makeText(getApplicationContext(), "Password Salah", Toast.LENGTH_LONG).show();
                        progress_spinner.dismiss();
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Username Tidak Ada", Toast.LENGTH_LONG).show();
                    progress_spinner.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                progress_spinner.dismiss();
            }
        });
    }
}
