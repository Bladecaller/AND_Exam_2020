package model.adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.retrofit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;
import model.retrofitData.ReviewedParticipant;
import views.UpdateDataActivity;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    List<ReviewedParticipant> list;

    public ReviewAdapter(ArrayList<ReviewedParticipant> list){
        this.list = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_single_item, parent, false);

        return new ViewHolder(view);
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final ReviewedParticipant reviewedParticipant = this.list.get(position);
        viewHolder.name.setText("Summoner: "+list.get(position).getName());
        viewHolder.rating.setText("Comment: " +list.get(position).getRating());

        viewHolder.delete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewHolder.delete.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewHolder.delete.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.custom_button));
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Participants").child(reviewedParticipant.getId());
                    ref.removeValue();
                }
                return true;
            }
        });

        viewHolder.update.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    viewHolder.update.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    viewHolder.update.setBackgroundDrawable(v.getContext().getResources().getDrawable(R.drawable.custom_button));
                    Intent i = new Intent(v.getContext(), UpdateDataActivity.class);
                    i.putExtra("id", reviewedParticipant.getId());
                    i.putExtra("name", reviewedParticipant.getName());
                    i.putExtra("rating", reviewedParticipant.getRating());
                    v.getContext().startActivity(i);
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
        TextView rating;
        Button update;
        Button delete;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.review_item_name);
            rating = itemView.findViewById(R.id.review_item_review);
            update = itemView.findViewById(R.id.review_item_update);
            delete = itemView.findViewById(R.id.review_item_delete);

        }

    }
}