package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Beranda extends AppCompatActivity {

    LinearLayout MenuGiziMakanan, MenuAktivitasSehat, MenuRumahSehat, MenuPlanning, MenuBMIChecker;
    ImageView navigationEditProfile;

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

        MenuGiziMakanan = findViewById(R.id.MenuGiziMakanan);
        MenuAktivitasSehat = findViewById(R.id.MenuAktivitasSehat);
        MenuRumahSehat = findViewById(R.id.MenuRumahSehat);
        MenuPlanning = findViewById(R.id.MenuPlanning);
        MenuBMIChecker = findViewById(R.id.MenuBMIChecker);
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

        MenuPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, MenuPlanning.class);
                startActivity(intent);
            }
        });

        MenuBMIChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Beranda.this, MenuBMIChecker.class);
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
}