package com.edri.ron.easyenglish.DialogFragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edri.ron.easyenglish.MainActivity;
import com.edri.ron.easyenglish.R;

/**
 * Created by Ron on 06/04/2018.
 */

public class DeleteAllDialogFragment extends DialogFragment {

    public static DeleteAllDialogFragment newInstance(String questionText) {
        DeleteAllDialogFragment deleteAllDialogFragment = new DeleteAllDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("questionText", questionText);
        deleteAllDialogFragment.setArguments(bundle);
        return deleteAllDialogFragment;
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
                ((MainActivity)getActivity()).yes();
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
