package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Beranda extends AppCompatActivity {

    private LinearLayout MenuGiziMakanan, MenuAktivitasSehat, MenuRumahSehat, MenuPlanning, MenuBMIChecker;
    private ImageView navigationEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navbar.setSelectedItemId(R.id.itemBeranda);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itemBeranda) {
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

        MenuGiziMakanan = findViewById(R.id.MenuGiziMakanan);
        MenuAktivitasSehat = findViewById(R.id.MenuAktivitasSehat);
        MenuRumahSehat = findViewById(R.id.MenuRumahSehat);
        navigationEditProfile = findViewById(R.id.MenuEditProfile);

        MenuGiziMakanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, MenuGiziMakanan.class);
                startActivity(intent);
            }
        });

        MenuAktivitasSehat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, MenuAktivitasSehat.class);
                startActivity(intent);
            }
        });

        MenuRumahSehat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, MenuRumahSehat.class);
                startActivity(intent);
            }
        });

        navigationEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, EditProfile.class);
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