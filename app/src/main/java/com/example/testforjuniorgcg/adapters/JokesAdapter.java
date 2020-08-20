package com.example.testforjuniorgcg.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.testforjuniorgcg.JokesResponse;
import com.example.testforjuniorgcg.R;

import java.util.List;

public class JokesAdapter extends RecyclerView.Adapter<JokesAdapter.ViewHolder> {

    private List<JokesResponse.Value> rData;
    private Context context;

    public JokesAdapter(Context context, List<JokesResponse.Value> data) {
        this.rData = data;
        this.context = context;
    }

    // Method that creates view for the first time
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.jokes_adapter, viewGroup, false); //find layout
        return new ViewHolder(view);//create a cell
    }

    // method for scrolling view update
    // ViewHolder holder - object that contains view
    // int position
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final JokesResponse.Value value = rData.get(position);//take the data
        holder.jokeNumber.setText("Joke #" + value.getId());//Set text for joke number
        holder.jokeText.setText(value.getJoke());//set text for joke itself

        if (value.getCategories().size() == 0) {
            holder.jokeTag.setText("No tags");
        } else {
            for (int i = 0; i < value.getCategories().size(); i++) { //set text for joke tag
                holder.jokeTag.setText("Tags: " + value.getCategories().get(i));
            }
        }

        if (position % 2 == 1) {
            holder.jokeText.setBackgroundColor(Color.parseColor("#DDDDDD"));
            holder.jokeNumber.setBackgroundColor(Color.parseColor("#DDDDDD"));
            holder.jokeTag.setBackgroundColor(Color.parseColor("#DDDDDD"));
        } else {
            holder.jokeText.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.jokeNumber.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.jokeTag.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
    }

    //method that returns amount of elements in the list
    @Override
    public int getItemCount() {
        return rData.size();
    }

    //Class that has views
    public static class ViewHolder extends RecyclerView.ViewHolder { //класс для ячейку
        private TextView jokeNumber;
        private TextView jokeText;
        private TextView jokeTag;

        ViewHolder(View itemView) {
            super(itemView);
            jokeNumber = itemView.findViewById(R.id.joke_number);
            jokeText = itemView.findViewById(R.id.joke_text);
            jokeTag = itemView.findViewById(R.id.joke_tag);
        }
    }

}

