package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button mbutton_monitoring_terung;
    Button mbutton_monitoring_tomat;
    Button mbutton_monitoring_Maribelajar;
    Button mLihat_Artikel;
    TextView mnamaHallo;


    FirebaseFirestore fstore;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

   /* public Home() {
        // Required empty public constructor
    }*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        mbutton_monitoring_terung = (Button) v.findViewById(R.id.MonitoringButtonTerung);
        mbutton_monitoring_tomat = (Button) v.findViewById(R.id.MonitoringButtonTomat);
        mbutton_monitoring_Maribelajar  = (Button) v.findViewById(R.id.MariBelajar);
        mLihat_Artikel = (Button) v.findViewById(R.id.buttonlihatartikel);
        mnamaHallo = (TextView) v.findViewById(R.id.namahallo);
        fstore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();
        DocumentReference reference;


        reference = fstore.collection("users").document(currentid);

       reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (!task.getResult().exists()) {
                                    Intent i = new Intent(getActivity(),Register.class);
                                } else {
                                    String nameResult = task.getResult().getString("fName");
                                    mnamaHallo.setText(nameResult);
                                }
                            }
                        });




        mLihat_Artikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Fitur Belum Tersedia", Toast.LENGTH_SHORT).show();
                Home.this.startActivity(new Intent(Home.this.getContext(), ReferensiArtikel.class));
            }
        });

       mbutton_monitoring_terung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.this.startActivity(new Intent(Home.this.getContext(), MonitoringTerung.class));
            }
        });
        mbutton_monitoring_tomat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.this.startActivity(new Intent(Home.this.getContext(), MonitoringTomat.class));
            }
        });

        mbutton_monitoring_Maribelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Home.this.startActivity(new Intent(Home.this.getContext(), Maribelajar.class));
            }
        });
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}