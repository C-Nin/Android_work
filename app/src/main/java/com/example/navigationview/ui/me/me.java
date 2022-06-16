package com.example.navigationview.ui.me;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.navigationview.R;
import com.example.navigationview.ui.viewpaper.ViewPaperViewModel;

public class me extends Fragment {

    private MeViewModel mViewModel;

    public static me newInstance() {
        return new me();
    }

    private ViewPaperViewModel viewPaperViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewPaperViewModel =
                ViewModelProviders.of(this).get(ViewPaperViewModel.class);
        View root = inflater.inflate(R.layout.me_fragment, container, false);

//        Button exit=getActivity().findViewById(R.id.button);
//        exit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            }
//        });
        Button exit=root.findViewById(R.id.buttons);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code
                NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);
                navController.navigate(R.id.login);

            }
        });
        return root;
    }
}

