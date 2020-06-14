package model.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.retrofit.R;
import java.util.ArrayList;
import java.util.List;
import model.retrofitData.Participants;
import views.ReviewActivity;

public class SummonerAdapter extends RecyclerView.Adapter<SummonerAdapter.ViewHolder> {
    List<Participants> list;
    static String PLAYER_TO_RATE = null;
    public SummonerAdapter(ArrayList<Participants> list){
        this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_summoner_layout, parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.name.setText(list.get(position).getSummonerName());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewHolder.button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewHolder.button.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewHolder.button.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.ic_go_to_rating));
                    Intent intent = new Intent(v.getContext(), ReviewActivity.class);
                    intent.putExtra(PLAYER_TO_RATE, viewHolder.name.getText().toString());
                    v.getContext().startActivity(intent);
                }
                return true;
            }
        });
    }

    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        Button button;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.single_summoner_name);
            button= itemView.findViewById(R.id.button);
        }
    }
}