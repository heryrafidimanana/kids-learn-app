package com.main.kids.ui.gallery;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.main.kids.NavActivity;
import com.main.kids.R;
import com.main.kids.databinding.FragmentGalleryBinding;
import com.main.kids.model.Evaluation;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        createNotificationChannel();

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initEval(root);
        listenValidation(root);
        listenNext(root);
        listenQuit(root);
        listenEncore(root);

        return root;
    }

    private Evaluation evaluation;

    private void initEval(View root){
        evaluation=new Evaluation();
        ((LinearLayout)root.findViewById(R.id.quizzon)).setVisibility(View.VISIBLE);
        ((LinearLayout)root.findViewById(R.id.quizzoff)).setVisibility(View.GONE);
        next(root);
    }

    private void next(View root) {
        ((TextView)root.findViewById(R.id.numero)).setText("Question n°"+evaluation.getNumeroQuestion());
        int max=getResources().getInteger(R.integer.max_question);
        if (evaluation.getNumeroQuestion() == max+1) {
            int id=getResources().getIdentifier(evaluation.getMention(max), "string", getActivity().getPackageName());
            String appreciation=getResources().getString(id);
            ((TextView)root.findViewById(R.id.score)).setText("score :"+evaluation.getScore());
            ((TextView)root.findViewById(R.id.appreciation)).setText(appreciation);
            ((LinearLayout)root.findViewById(R.id.quizzon)).setVisibility(View.GONE);
            ((LinearLayout)root.findViewById(R.id.quizzoff)).setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), getResources().getString(R.string.qtermine), Toast.LENGTH_LONG).show();

            NotificationCompat.Builder builder = new NotificationCompat.Builder( getActivity(), "my_channel_01")
                    .setSmallIcon(R.drawable.ic_alert)
                    .setContentTitle("Quiz finished")
                    .setContentText("Congratulation, you finish the quiz with "+evaluation.getScore()+" points :)")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
            notificationManager.notify(0, builder.build());
        } else {
            evaluation.nextQuestion();
            ((TextView) root.findViewById(R.id.question)).setText(this.evaluation.getQuestion());
        }
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "kids";
            String description = "Congratulation, you finish the quiz :)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("my_channel_01", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void listenValidation(View root){
        ((Button)root.findViewById(R.id.validation)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String message=getResources().getString(R.string.qbadrep)+' '+evaluation.getReponse();
                EditText rep=((EditText) root.findViewById(R.id.reponse));
                if(rep.getText().length()==0){

                    Toast.makeText(getActivity(), getResources().getString(R.string.qrequis), Toast.LENGTH_LONG).show();
                    return;
                }
                if(evaluation.estBonneReponse(Double.valueOf(rep.getText().toString()))){
                    message=getResources().getString(R.string.qgoodrep);
                }
                rep.getText().clear();
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                next(root);
            }
        });
    }

    private void listenNext(View root){
        ((Button)root.findViewById(R.id.suivante)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                next(root);
            }
        });
    }

    private void listenQuit(View root){
        ((Button)root.findViewById(R.id.quit)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), NavActivity.class);
                startActivity(intent);
            }
        });
    }

    private void listenEncore(View root){
        ((Button)root.findViewById(R.id.encore)).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                initEval(root);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}