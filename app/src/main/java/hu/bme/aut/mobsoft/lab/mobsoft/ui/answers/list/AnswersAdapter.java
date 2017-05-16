package hu.bme.aut.mobsoft.lab.mobsoft.ui.answers.list;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.answer.Answer;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {
    private List<Answer> answers;
    private View.OnClickListener listener;

    public AnswersAdapter(List<Answer> answers, View.OnClickListener listener) {
        this.answers = answers;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_answer, parent, false);
        v.setOnClickListener(listener);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Answer answer = answers.get(position);
        holder.title.setText(answer.getTitle());
        holder.description.setText(answer.getDescription());

        if (answer.getRating() != 0) {
            holder.rating.setText(String.valueOf(answer.getRating()));
        } else {
            holder.rating.setText("");
        }

        final int icon;
        if(answer.getRating() > 0) {
            icon = R.drawable.ic_done_black_24dp;
        } else if(answer.getRating() < 0) {
            icon = R.drawable.ic_clear_black_24dp;
        } else {
            icon = 0;
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.rating.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0, icon, 0);
        } else {
            holder.rating.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0, icon, 0);
        }

    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.answer_title) TextView title;
        @BindView(R.id.answer_description) TextView description;
        @BindView(R.id.answer_rating) TextView rating;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
