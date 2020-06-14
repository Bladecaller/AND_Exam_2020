package views;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.retrofit.R;
import com.google.android.material.navigation.NavigationView;

public class ProBuildsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    NavigationView navigationView;
    Button button;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.probuilds_layout);
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
        editText = findViewById(R.id.editTextProBuilds);

        button = findViewById(R.id.buttonProBuilds);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_pressed));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    button.setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_button));
                    String action = Intent.ACTION_VIEW;
                    Uri uri = Uri.parse("https://u.gg/lol/champions/"+editText.getText().toString()+"/pro-build");

                    Intent intent = new Intent(action, uri);
                    startActivity(intent);
                }
                return true;
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.Home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (item.getItemId()==R.id.Guides){
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