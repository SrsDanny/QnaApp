package hu.bme.aut.mobsoft.lab.mobsoft.ui.questions.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import hu.bme.aut.mobsoft.lab.mobsoft.R;
import hu.bme.aut.mobsoft.lab.mobsoft.model.question.Question;

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<Question> questions;

    public QuestionsAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_question, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Question question = questions.get(position);
        holder.title.setText(question.getTitle());
        holder.description.setText(question.getDescription());
        holder.answersCount.setText(String.format(Locale.getDefault(), "%d", question.getNumberOfAnswers()));
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        TextView answersCount;

        ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            answersCount = (TextView) itemView.findViewById(R.id.answersCount);
        }
    }
}
