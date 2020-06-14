package views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.retrofit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import model.retrofitData.ReviewedParticipant;

public class UpdateDataActivity extends AppCompatActivity {
    EditText tempName;
    EditText tempReview;
    Button button;
    String id, name, rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        tempName = findViewById(R.id.name_edit_update);
        tempReview = findViewById(R.id.comment_edit_update);
        button = findViewById(R.id.update_update);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        name = intent.getStringExtra("name");
        rating = intent.getStringExtra("rating");

        tempName.setText(name);
        tempReview.setText(rating);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Participants").child(id);
                    String name, review;
                    name = tempName.getText().toString();
                    review = tempReview.getText().toString();
                    ReviewedParticipant rp = new ReviewedParticipant(id,name,review);
                    ref.setValue(rp);
                    Intent intent = new Intent(v.getContext(), ReviewActivity.class);
                    v.getContext().startActivity(intent);
                }
                return true;
            }
        });
    }
}
