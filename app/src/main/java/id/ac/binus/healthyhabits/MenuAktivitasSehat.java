package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuAktivitasSehat extends AppCompatActivity {
    private ImageView navigationEditProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_aktivitas_sehat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navigationEditProfile = findViewById(R.id.MenuEditProfile);

        //Navigation Bar
        navbar.setSelectedItemId(R.id.MenuAktivitasSehat);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itemBeranda) {
                    navigateTo(Beranda.class);
                    return true;
                } else if (item.getItemId() == R.id.itemPlanning) {
                    navigateTo(MenuPlanning.class);
                    return true;
                } else if (item.getItemId() == R.id.itemBMIChecker) {
                    navigateTo(MenuBMIChecker.class);
                    return true;
                }
                return false;
            }
        });

        navigationEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAktivitasSehat.this, EditProfile.class);
                startActivity(intent);
            }
        });
    }
    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}