package com.main.kids.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.main.kids.DisplayVideoActivity;
import com.main.kids.NavActivity;
import com.main.kids.R;
import com.main.kids.adapter.CourseAdapter;
import com.main.kids.databinding.FragmentHomeBinding;
import com.main.kids.model.Course;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(
                            @NonNull LayoutInflater inflater,
                            ViewGroup container,
                            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView coursesList = (ListView) view.findViewById(R.id.list_view);

        CourseAdapter courseAdapter = new CourseAdapter(getActivity(),((NavActivity) getActivity()).courses);
        coursesList.setAdapter(courseAdapter);

        //Handling click Events in ListView
        coursesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Course data = (Course) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getActivity(), DisplayVideoActivity.class);
                intent.putExtra("course",data);

                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
