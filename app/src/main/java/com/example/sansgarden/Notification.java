package com.example.sansgarden;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sansgarden.databinding.FragmentNotificationBinding;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notification extends Fragment {
    private static final String TAG = Notification.class.getSimpleName();
    Firebase mRef1,mRef2;
    DocumentReference reference;
    RecyclerView mRecyclerView;
    TextView mterungstatuspompa, mtomatstatuspompa, mtomatstatuslampu;
    TextView mstatusnyiramterung, mstatusnyiramtomat, mstatuslamputomat;
    FragmentNotificationBinding binding;

    final ArrayList<String> messageModels = new ArrayList<String>();
    //ChatAdapter chatAdapter = new ChatAdapter(messageModels,this);
    FirebaseFirestore fstore;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // public Notification() {
   //     // Required empty public constructor
   // }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notification.
     */
    // TODO: Rename and change types and number of parameters
    public static Notification newInstance(String param1, String param2) {
        Notification fragment = new Notification();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentNotificationBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_notification,container,false);
        mterungstatuspompa = (TextView) v.findViewById(R.id.terungstatuspompatext);
        mtomatstatuspompa = (TextView) v.findViewById(R.id.tomatstatuspompatext);
        mtomatstatuslampu = (TextView) v.findViewById(R.id.tomatstatuslamputext);
        mstatusnyiramterung = (TextView) v.findViewById(R.id.statusnyiramterungtext);
        mstatusnyiramtomat = (TextView) v.findViewById(R.id.tomatstatusnyiramtext);
        mstatuslamputomat = (TextView) v.findViewById(R.id.statuslamputomattext);
        mRef1 = new Firebase("https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/kebun/versi1");
        mRef2 = new Firebase("https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/kebun/versi2");

        mRef1.child("statusPompa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pompastatusterung = dataSnapshot.getValue(String.class);
                mterungstatuspompa.setText(pompastatusterung);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef1.child("statusPenyiraman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statussiramterung = dataSnapshot.getValue(String.class);
                mstatusnyiramterung.setText(statussiramterung);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mRef2.child("statusPompa").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pompastatustomat = dataSnapshot.getValue(String.class);
                mtomatstatuspompa.setText(pompastatustomat);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef2.child("statusPenyiraman").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statussiramtomat = dataSnapshot.getValue(String.class);
                mstatusnyiramtomat.setText(statussiramtomat);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef2.child("statusLampu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String lampustatustomat = dataSnapshot.getValue(String.class);
                mtomatstatuslampu.setText(lampustatustomat);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef2.child("statusKondisiLampu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String statuslamputomat = dataSnapshot.getValue(String.class);
                mstatuslamputomat.setText(statuslamputomat);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

          return v;
        }




}