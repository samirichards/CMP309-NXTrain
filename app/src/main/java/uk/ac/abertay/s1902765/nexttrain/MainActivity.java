package uk.ac.abertay.s1902765.nexttrain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalDate currentDate = LocalDate.now();
        LocalDate storedDate;
        SharedPreferences settings = getSharedPreferences("appSettings", 0);
        if (!settings.contains("dbLastUpdated")){
            Intent buildDatabase = new Intent(getApplicationContext(), BuildDatabaseActivity.class);
            startActivity(buildDatabase);
        }
        else{
            storedDate = Instant.ofEpochMilli(settings.getLong("dbLastUpdated", (long)0)).atZone(ZoneId.systemDefault()).toLocalDate();
            if (storedDate.isBefore(currentDate.minusWeeks(4))){
                Intent buildDatabase = new Intent(getApplicationContext(), BuildDatabaseActivity.class);
                startActivity(buildDatabase);
            }
        }
        settings.edit().putLong("lastLaunched", (new Date(System.currentTimeMillis()).getTime())).apply();


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.homeNavHost);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        NavigationBarView homeBottomNavBar = findViewById(R.id.home_bottom_nav);
        NavigationUI.setupWithNavController(homeBottomNavBar, navController);
    }
}