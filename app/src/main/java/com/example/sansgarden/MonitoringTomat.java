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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.firestore.FirebaseFirestore;


public class MonitoringTomat extends AppCompatActivity {

    ImageButton mBackMonitoringTerung, monButtonWater, moffButtonWater,mautoButtonWater;
    ImageButton monButtonCahaya, moffButtonCahaya, mautoButtonCahaya;
    ImageButton monButtonPendinginan, moffButtonPendinginan, mautoButtonPendinginan;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch mswitchAutoButtonWater, mswitchAutoButtonPendinginan, mswitchAutoButtonCahaya;
    ImageButton mBackMonitoringTomat;
    TextView mKelembabanTanahValue, mCahayaValue, mSuhuValue,mKelembabanUdaravalue, mpHvalue,mCuacavalue,mTangkivalue;
    Firebase mRef;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_tomat);

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

        mBackMonitoringTomat = findViewById(R.id.backmonitoringtomat);
        mKelembabanTanahValue = findViewById(R.id.kelembaban_tanah_value);
        mCahayaValue= findViewById(R.id.cahaya_value);
        mKelembabanUdaravalue = findViewById(R.id.kelembaban_udara_value);
        mSuhuValue = findViewById(R.id.suhu_udara_value);
        mpHvalue = findViewById(R.id.pH_value);
        mCuacavalue = findViewById(R.id.Cuaca_value);
        mTangkivalue = findViewById(R.id.tangki_value);

        mRef = new Firebase("https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/kebun/versi2");

        mBackMonitoringTomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),bna.class));
                MonitoringTomat.this.mRef.child("buttonWater").setValue(2);
                MonitoringTomat.this.mRef.child("buttonCahaya").setValue(2);
                MonitoringTomat.this.mRef.child("buttonPendinginan").setValue(2);
            }
        });

        monButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MonitoringTomat.this, "Pompa ON", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.monButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.moffButtonWater.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonWater").setValue(1);
            }
        });

        moffButtonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MonitoringTomat.this, "Pompa OFF", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.moffButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.monButtonWater.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonWater").setValue(0);
            }
        });

        mswitchAutoButtonWater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTomat.this.moffButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonWater.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.mautoButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.mRef.child("buttonWater").setValue(0);

                }
                else {
                    MonitoringTomat.this.mRef.child("buttonWater").setValue(2);
                    MonitoringTomat.this.mautoButtonWater.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.moffButtonWater.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonWater.setVisibility(View.INVISIBLE);
                }
            }
        });

        monButtonCahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MonitoringTerung.this, "Pompa ON", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.monButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.moffButtonCahaya.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonCahaya").setValue(1);
            }
        });

        moffButtonCahaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MonitoringTerung.this, "Pompa OFF", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.monButtonCahaya.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonCahaya").setValue(0);
            }
        });

        mswitchAutoButtonCahaya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTomat.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonCahaya.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.mautoButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.mRef.child("buttonCahaya").setValue(0);

                }
                else {
                    MonitoringTomat.this.mRef.child("buttonCahaya").setValue(2);
                    MonitoringTomat.this.mautoButtonCahaya.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.moffButtonCahaya.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonCahaya.setVisibility(View.INVISIBLE);
                }
            }
        });


        monButtonPendinginan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(MonitoringTerung.this, "Pompa ON", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.monButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.moffButtonPendinginan.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonPendinginan").setValue(1);
            }
        });

        moffButtonPendinginan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MonitoringTerung.this, "Pompa OFF", Toast.LENGTH_SHORT).show();
                MonitoringTomat.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                MonitoringTomat.this.monButtonPendinginan.setVisibility(View.VISIBLE);
                MonitoringTomat.this.mRef.child("buttonPendinginan").setValue(0);
            }
        });

        mswitchAutoButtonPendinginan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    MonitoringTomat.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonPendinginan.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.mautoButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.mRef.child("buttonPendinginan").setValue(0);

                }
                else {
                    MonitoringTomat.this.mRef.child("buttonPendinginan").setValue(2);
                    MonitoringTomat.this.mautoButtonPendinginan.setVisibility(View.VISIBLE);
                    MonitoringTomat.this.moffButtonPendinginan.setVisibility(View.INVISIBLE);
                    MonitoringTomat.this.monButtonPendinginan.setVisibility(View.INVISIBLE);
                }
            }
        });


        mRef.child("kelembabanTanah").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String kelembabantanahdata = dataSnapshot.getValue(String.class);
                mKelembabanTanahValue.setText(kelembabantanahdata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("cahaya").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String cahayadata = dataSnapshot.getValue(String.class);
                mCahayaValue.setText(cahayadata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("suhu").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String suhudata = dataSnapshot.getValue(String.class);
                mSuhuValue.setText(suhudata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("kelembabanUdara").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String kelembabanudaradata = dataSnapshot.getValue(String.class);
                mKelembabanUdaravalue.setText(kelembabanudaradata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef.child("pH").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String pHdata = dataSnapshot.getValue(String.class);
                mpHvalue.setText(pHdata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef.child("tangkiAir").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String tangkidata = dataSnapshot.getValue(String.class);
                mTangkivalue.setText(tangkidata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef.child("Cuaca").addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String cuacadata = dataSnapshot.getValue(String.class);
                mCuacavalue.setText(cuacadata);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
}