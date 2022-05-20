package com.main.kids.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.main.kids.LoginActivity;
import com.main.kids.NavActivity;
import com.main.kids.R;
import com.main.kids.adapter.CourseAdapter;
import com.main.kids.databinding.FragmentHomeBinding;
import com.main.kids.model.Course;
import com.main.kids.model.ResObj;
import com.main.kids.remote.ApiUtils;
import com.main.kids.remote.CourseService;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    public View onCreateView(
                            @NonNull LayoutInflater inflater,
                            ViewGroup container,
                            Bundle savedInstanceState) {

//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ListView coursesList = (ListView) view.findViewById(R.id.list_view);

        List<Course> courses = new ArrayList<Course>();
        courses.add(new Course(
                        "Alphabet",
                    "Système d'écriture constitué d'un ensemble de symboles dont chacun représente, par exemple, un des phonèmes d’une langue.",
                        "png",
                "http",
                "fr"));

        CourseAdapter courseAdapter = new CourseAdapter(getActivity(), courses);
        coursesList.setAdapter(courseAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}