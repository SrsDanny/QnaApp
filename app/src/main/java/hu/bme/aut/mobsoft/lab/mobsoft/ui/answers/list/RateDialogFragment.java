package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Rating;

public class RateDialogFragment extends DialogFragment {

    public static final String ANSWER_ID_KEY = "answerId";

    interface RateDialogListener {
        void ratingReceived(Rating rating);
    }

    private RateDialogListener listener;
    private long answerId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        answerId = getArguments().getLong(ANSWER_ID_KEY);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Rate the answer!")
                .setPositiveButton("UP", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.ratingReceived(new Rating(Rating.VoteType.UPVOTE, answerId));
                    }
                })
                .setNegativeButton("DOWN", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.ratingReceived(new Rating(Rating.VoteType.DOWNVOTE, answerId));
                    }
                })
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (RateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + " must implement RateDialogListener");
        }
    }
}
