package com.edri.ron.easyenglish.DialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edri.ron.easyenglish.Classes.Word;
import com.edri.ron.easyenglish.MainActivity;
import com.edri.ron.easyenglish.R;

/**
 * Created by Ron on 05/04/2018.
 */

public class QuestionDialogFragment extends DialogFragment {

    public static Word word;

    public static QuestionDialogFragment newInstance(String questionText, Word w) {
        word = w;
        QuestionDialogFragment questionDialogFragment = new QuestionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("questionText", questionText);
        questionDialogFragment.setArguments(bundle);
        return questionDialogFragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get controls:
        LinearLayout linearLayoutRoot = (LinearLayout)inflater.inflate(R.layout.fragment_question_dialog, container, false);
        TextView textViewQuestion = (TextView)linearLayoutRoot.findViewById(R.id.textViewQuestion);
        Button buttonYes = (Button)linearLayoutRoot.findViewById(R.id.buttonYes);
        Button buttonNo = (Button)linearLayoutRoot.findViewById(R.id.buttonNo);

        // Set data:
        String questionText = getArguments().getString("questionText");
        textViewQuestion.setText(questionText);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                ((MainActivity)getActivity()).yes(word);
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
                ((MainActivity)getActivity()).no();
            }
        });

        setCancelable(false);

        return linearLayoutRoot;
    }
}