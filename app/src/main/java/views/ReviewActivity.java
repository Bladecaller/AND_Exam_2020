package views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.retrofit.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import model.retrofitData.ReviewedParticipant;
import model.adapters.ReviewAdapter;

public class ReviewActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener  {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    SearchView searchView;
    Button addReviewButton;
    EditText comment;
    EditText summonerName;
    RecyclerView recyclerView;
    ReviewAdapter adapter;
    ArrayList<ReviewedParticipant> reviewedParticipantList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reviewedParticipants = database.getReference("Participants");
    static String PLAYER_TO_RATE = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Bundle bundle = getIntent().getExtras();
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.NavView);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        drawerLayout = findViewById(R.id.drawer_layoutid);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        searchView = findViewById(R.id.search_view);
        comment = findViewById(R.id.comment_review);
        summonerName = findViewById(R.id.summoner_name_review);
        addReviewButton = findViewById(R.id.add_review);

        if (bundle != null && bundle.containsKey(MainActivity.PLAYER_TO_RATE)) {
            String playerName = bundle.getString(MainActivity.PLAYER_TO_RATE);
            summonerName.setText(playerName);
        }
        if (bundle != null && bundle.containsKey(ReviewActivity.PLAYER_TO_RATE)) {
            String playerName = bundle.getString(ReviewActivity.PLAYER_TO_RATE);
            summonerName.setText(playerName);
        }

        reviewedParticipantList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_review);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewAdapter(reviewedParticipantList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        addReviewButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    addReviewButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    addReviewButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    addRatedPlayer(v);
                    adapter.notifyDataSetChanged();
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        reviewedParticipants.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                reviewedParticipantList.clear();
                for(DataSnapshot ratedParticipantSnapshot:dataSnapshot.getChildren()){
                    ReviewedParticipant participant = ratedParticipantSnapshot.getValue(ReviewedParticipant.class);
                    reviewedParticipantList.add(participant);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
            if (searchView!=null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }
    }

    private void search(String str){
        ArrayList<ReviewedParticipant> mylist = new ArrayList<>();
        for(ReviewedParticipant participant: reviewedParticipantList){
            if(participant.getName().toLowerCase().contains(str.toLowerCase())){
                mylist.add(participant);
            }
        }
        ReviewAdapter RA = new ReviewAdapter(mylist);
        recyclerView.setAdapter(RA);
    }

    public void addRatedPlayer(View view){
        if (TextUtils.isEmpty(summonerName.getText().toString())){
            Toast.makeText(this,"You must enter a summoner name ", Toast.LENGTH_LONG).show();
        }else
        if (TextUtils.isEmpty(comment.getText().toString())){
            Toast.makeText(this,"You must enter a comment ", Toast.LENGTH_LONG).show();
        }else {
            String name = summonerName.getText().toString().trim();
            String rating = comment.getText().toString().trim();
                String id = reviewedParticipants.push().getKey();
                ReviewedParticipant participant = new ReviewedParticipant(id, name, rating);
                reviewedParticipants.child(id).setValue(participant);
            Toast.makeText(this,"Review for "+ name +" was added", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.Home){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.ProBuilds){
            Intent intent = new Intent(this, ProBuildsActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.Guides) {
            Intent intent = new Intent(this, GuidesActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.TierList){
            Intent intent = new Intent(this, TierListActivity.class);
            startActivity(intent);
        }
        return false;
    }
}