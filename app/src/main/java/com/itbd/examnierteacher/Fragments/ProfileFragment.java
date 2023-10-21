package com.itbd.examnierteacher.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.itbd.examnierteacher.ChangePasswordActivity;
import com.itbd.examnierteacher.R;
import com.itbd.examnierteacher.SignInActivity;
import com.itbd.examnierteacher.DataMoldes.SignUpInfoModel;
import com.itbd.examnierteacher.PersonalInfoActivity;

public class ProfileFragment extends Fragment {

    private static final String U_DATA = "arg1";
    SignUpInfoModel signUpInfoModelData;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment getInstance(SignUpInfoModel uData) {
        ProfileFragment profileFragment = new ProfileFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(U_DATA, uData);

        profileFragment.setArguments(bundle);
        return profileFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, viewGroup, false);

        if (getArguments() != null) {
            signUpInfoModelData = (SignUpInfoModel) getArguments().getSerializable(U_DATA);
        }

        ImageButton btnLogOut = view.findViewById(R.id.btn_logout);
        ImageButton btnPersonalInfo = view.findViewById(R.id.personalinfo);
        ImageButton btnChangePassword = view.findViewById(R.id.changepassword);

        TextView teacherName = view.findViewById(R.id.teacher_name);

        teacherName.setText(signUpInfoModelData.getFullName());

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(requireActivity(), SignInActivity.class));
            }
        });
        btnPersonalInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), PersonalInfoActivity.class).putExtra("uData", signUpInfoModelData));
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(requireActivity(), ChangePasswordActivity.class));
            }
        });

        return view;
    }
}