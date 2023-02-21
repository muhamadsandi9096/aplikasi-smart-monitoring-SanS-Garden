package com.example.sansgarden;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
 * Use the {@link setting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class setting extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView mresetpasswordsetting, maboutus, mlogout, mUsernameSet, mEmailSet;
    FirebaseFirestore fstore;


    /*public setting() {
        // Required empty public constructor
    }*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment setting.
     */
    // TODO: Rename and change types and number of parameters
    public static setting newInstance(String param1, String param2) {
        setting fragment = new setting();
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
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        mresetpasswordsetting = (TextView) v.findViewById(R.id.resetpasswordsetting);
        mlogout = (TextView) v.findViewById(R.id.logout);
        maboutus = (TextView) v.findViewById(R.id.aboutus);
        mUsernameSet = (TextView) v.findViewById(R.id.username_set);
        mEmailSet = (TextView) v.findViewById(R.id.email_set);

        fstore = FirebaseFirestore.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();
        DocumentReference reference;


        reference = fstore.collection("users").document(currentid);

        reference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String nameResult = task.getResult().getString("fName");
                            mUsernameSet.setText(nameResult);

                            String emailResult = task.getResult().getString("email");
                            mEmailSet.setText(emailResult);
                        }else{
                            Intent intent = new Intent(getActivity(),Register.class);
                        }
                    }
                });

        mresetpasswordsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // setting.this.startActivity(new Intent(setting.this.getContext(), ResetPassword.class));
            }
        });
        mlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.this.startActivity(new Intent(setting.this.getContext(), MainActivity.class));
            }
        });
        maboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setting.this.startActivity(new Intent(setting.this.getContext(), AboutUs.class));
            }
        });
        return v;

    }


}