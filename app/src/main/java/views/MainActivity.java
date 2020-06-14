package views;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.retrofit.R;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import model.retrofitData.Match;
import model.retrofitData.Participants;
import model.retrofitData.Summoner;
import model.adapters.SummonerAdapter;
import viewmodel.MyViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView recyclerView;
    SummonerAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    EditText enterSummonerName;
    TextView summonerLevelText;
    TextView matchView;
    EditText enterSummonerNameForReview;
    Toolbar toolbar;
    NavigationView navigationView;
    MyViewModel viewModel;
    String matchId;
    String gameId;
    Button buttonLockIn;
    Button buttonGoToReview;
    Button buttonLastGame;
    Button buttonGeneratePlayers;
    ArrayList<Participants> participantsList;
    static String PLAYER_TO_RATE = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);

        enterSummonerName = findViewById(R.id.enter_summoner_name_main);
        enterSummonerNameForReview = findViewById(R.id.summoner_for_a_review_main);

        navigationView = findViewById(R.id.NavView);
        drawerLayout = findViewById(R.id.drawer_layoutid);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        recyclerView = findViewById(R.id.recycler_view_main);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        participantsList = new ArrayList<>();
        adapter = new SummonerAdapter(participantsList);
        recyclerView.setAdapter(adapter);

        matchView = findViewById(R.id.match_view_main);
        summonerLevelText = findViewById(R.id.text_view_summoner_level_main);
        navigationView.setNavigationItemSelectedListener(this);

        buttonLockIn = findViewById(R.id.lock_in_button_main);
        buttonLockIn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   buttonLockIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonLockIn.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    updateSummoner(v);
                }
                return true;
            }
        });

        buttonGoToReview = findViewById(R.id.go_to_review_button_main);
        buttonGoToReview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonGoToReview.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonGoToReview.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    Intent intent = new Intent(MainActivity.this, ReviewActivity.class);
                    intent.putExtra(PLAYER_TO_RATE, enterSummonerNameForReview.getText().toString());
                    startActivity(intent);
                }
                return true;
            }
        });

        buttonLastGame = findViewById(R.id.last_game_button_main);
        buttonLastGame.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonLastGame.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonLastGame.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    updateID(v);
                }
                return true;
            }
        });

        buttonGeneratePlayers = findViewById(R.id.generate_players_main);
        buttonGeneratePlayers.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    buttonGeneratePlayers.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    buttonGeneratePlayers.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    updateGameID(v);
                }
                return true;
            }
        });

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        viewModel.getSummoner().observe(this, new Observer<Summoner>() {
            @Override
            public void onChanged(Summoner summoner) {
                summonerLevelText.setText("Summoner level: "+ summoner.getSummonerLevel());
                matchId = summoner.getID();
            }
        });
        viewModel.getMatch().observe(this, new Observer<Match>(){
            @Override
            public void onChanged(Match matches) {
                matchView.setText("Last match found !");
                gameId = matches.getGameID();
            }
        });
        viewModel.getParticipants().observe(this, new Observer<List<Participants>>(){
            @Override
            public void onChanged(List<Participants> participants) {
                participantsList.clear();
                for(int i = 0;i<participants.size();i++){
                    participantsList.add(participants.get(i));
                    adapter.notifyDataSetChanged();
                    matchView.setText("Last game status");
               }
            }
        });
    }

    public void updateSummoner(View view) {
        if (TextUtils.isEmpty(enterSummonerName.getText().toString())){
            Toast.makeText(this,"You must enter a summoner name ", Toast.LENGTH_LONG).show();
        }
        viewModel.requestSummoner(enterSummonerName.getText().toString());
    }

    public void updateID(View view){
        viewModel.requestMatch(matchId);
    }

    public void updateGameID(View view){
        viewModel.requestParticipants(gameId);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.ProBuilds){
        Intent intent = new Intent(this, ProBuildsActivity.class);
        participantsList.clear();
        startActivity(intent);
        }
        if (item.getItemId()==R.id.Guides){
            Intent intent = new Intent(this, GuidesActivity.class);
            participantsList.clear();
            startActivity(intent);
        }
        if (item.getItemId()==R.id.TierList){
            Intent intent = new Intent(this, TierListActivity.class);
            participantsList.clear();
            startActivity(intent);
        }
        return false;
    }
}