package com.example.sansgarden;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


public class MonitoringTerung extends AppCompatActivity {
    ImageButton mBackMonitoringTerung, monButtonWater, moffButtonWater,mautoButtonWater;
    ImageButton monButtonCahaya, moffButtonCahaya, mautoButtonCahaya;
    ImageButton monButtonPendinginan, moffButtonPendinginan, mautoButtonPendinginan;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch mswitchAutoButtonWater, mswitchAutoButtonPendinginan, mswitchAutoButtonCahaya;
    TextView    mKelembabanTanahValue, mCahayaValue, mSuhuValue,mKelembabanUdaravalue, mpHvalue,mCuacavalue,mTangkivalue;
    Firebase mRef;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = MonitoringTerung.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_terung);

        mBackMonitoringTerung = findViewById(R.id.backmonitoringterung);
        monButtonWater = findViewById(R.id.on_button_water);
        moffButtonWater = findViewById(R.id.off_button_water);
        mautoButtonWater = findViewById(R.id.auto_button_water);
        mswitchAutoButtonWater = findViewById(R.id.checkBox_auto_button_water);
        monButtonCahaya = findViewById(R.id.on_button_pencahayaan);
        moffButtonCahaya = findViewById(R.id.off_button_pencahayaan);
        mautoButtonCahaya = findViewById(R.id.auto_button_pencahayaan);
        mswitchAutoButtonCahaya = findViewById(R.id.checkBox_auto_button_pencahayaan);
        monButtonPendinginan = findViewById(R.id.on_button_udara);
        moffButtonPendinginan = findViewById(R.id.off_button_udara);
        mautoButtonPendinginan = findViewById(R.id.auto_button_udara);
        mswitchAutoButtonPendinginan = findViewById(R.id.checkBox_auto_button_pendingin);

        mKelembabanTanahValue = findViewById(R.id.kelembaban_tanah_value);
        mCahayaValue= findViewById(R.id.cahaya_value);
        mKelembabanUdaravalue = findViewById(R.id.kelembaban_udara_value);
        mSuhuValue = findViewById(R.id.suhu_udara_value);
        mpHvalue = findViewById(R.id.pH_value);
        mCuacavalue = findViewById(R.id.Cuaca_value);
        mTangkivalue = findViewById(R.id.tangki_value);

        mRef = new Firebase("https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/kebun/versi1");

        mBackMonitoringTerung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bna.class));
                MonitoringTerung.this.mRef.child("buttonWater").setValue(2);
                MonitoringTerung.this.mRef.child("buttonCahaya").setValue(2);
                MonitoringTerung.this.mRef.child("buttonPendinginan").setValue(2);
            }
        });

        monButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MonitoringTerung.this, "Pompa ON", Toast.LENGTH_SHORT).show();
                MonitoringTerung.this.monButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.moffButtonWater.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonWater").setValue(1);
            }
        });

        moffButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MonitoringTerung.this, "Pompa OFF", Toast.LENGTH_SHORT).show();
                MonitoringTerung.this.moffButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.monButtonWater.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonWater").setValue(0);
            }
        });

        mswitchAutoButtonWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTerung.this.moffButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonWater.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.mRef.child("buttonWater").setValue(0);

                }
                else {
                    MonitoringTerung.this.mRef.child("buttonWater").setValue(2);
                    MonitoringTerung.this.mautoButtonWater.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.moffButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonWater.setVisibility(View.INVISIBLE);
                }
            }
        });

        monButtonCahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MonitoringTerung.this, "Lampu ON", Toast.LENGTH_SHORT).show();
                MonitoringTerung.this.monButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.moffButtonCahaya.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonCahaya").setValue(1);
            }
        });

        moffButtonCahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MonitoringTerung.this, "Lampu OFF", Toast.LENGTH_SHORT).show();
                MonitoringTerung.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.monButtonCahaya.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonCahaya").setValue(0);
            }
        });

        mswitchAutoButtonCahaya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTerung.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonCahaya.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.mRef.child("buttonCahaya").setValue(0);

                }
                else {
                    MonitoringTerung.this.mRef.child("buttonCahaya").setValue(2);
                    MonitoringTerung.this.mautoButtonCahaya.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonCahaya.setVisibility(View.INVISIBLE);
                }
            }
        });


        monButtonPendinginan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonitoringTerung.this.monButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.moffButtonPendinginan.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonPendinginan").setValue(1);
            }
        });

        moffButtonPendinginan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonitoringTerung.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTerung.this.monButtonPendinginan.setVisibility(View.VISIBLE);
                MonitoringTerung.this.mRef.child("buttonPendinginan").setValue(0);
            }
        });

        mswitchAutoButtonPendinginan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTerung.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonPendinginan.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.mRef.child("buttonPendinginan").setValue(0);

                }
                else {
                    MonitoringTerung.this.mRef.child("buttonPendinginan").setValue(2);
                    MonitoringTerung.this.mautoButtonPendinginan.setVisibility(View.VISIBLE);
                    MonitoringTerung.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTerung.this.monButtonPendinginan.setVisibility(View.INVISIBLE);
                }
            }
        });


        mRef.child("kelembabanTanah").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kelembabantanahdata = dataSnapshot.getValue(String.class);
                mKelembabanTanahValue.setText(kelembabantanahdata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("cahaya").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cahayadata = dataSnapshot.getValue(String.class);
                mCahayaValue.setText(cahayadata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("suhu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String suhudata = dataSnapshot.getValue(String.class);
                mSuhuValue.setText(suhudata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("kelembabanUdara").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String kelembabanudaradata = dataSnapshot.getValue(String.class);
                mKelembabanUdaravalue.setText(kelembabanudaradata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef.child("pH").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pHdata = dataSnapshot.getValue(String.class);
                mpHvalue.setText(pHdata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("tangkiAir").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tangkidata = dataSnapshot.getValue(String.class);
                mTangkivalue.setText(tangkidata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef.child("Cuaca").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String cuacadata = dataSnapshot.getValue(String.class);
                mCuacavalue.setText(cuacadata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        /*Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
               @SuppressLint("SimpleDateFormat")
               DateFormat clockFormat = new SimpleDateFormat("HH:mm");

               @SuppressLint("SimpleDateFormat")
               DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMMM yyyy");
               handler.postDelayed(this,1000);

               String time = String.valueOf(clockFormat);

                if (time == "10:00" || time == "13:00"){
                    Map<String, Object> user = new HashMap<>();
                    user.put("kelembabanTanah", mKelembabanTanahValue);

                    // Add a new document with a generated ID
                    db.collection("datamonitoring")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }

            }
        });*/
    }
}