package com.main.kids.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.kids.R;
import com.main.kids.model.Course;

import java.util.List;

import androidx.annotation.NonNull;

public class CourseAdapter extends ArrayAdapter<Course> {

    private Context context;
    private List<Course> courses = null;

    public CourseAdapter(Context context, List<Course> courses) {
        super(context, 0, courses);
        this.context = context;
        this.courses = courses;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        Course course = courses.get(position);

        if( view  == null )
            view = LayoutInflater.from(context).inflate(R.layout.item_course_list, null);

        ImageView courseImage = (ImageView) view.findViewById(R.id.course_img);
        courseImage.setImageResource(/*course.getImg()*/ 0);

        TextView courseDescription = (TextView) view.findViewById(R.id.course_description);
        courseDescription.setText(course.getDescription());

        return view;
    }

}
