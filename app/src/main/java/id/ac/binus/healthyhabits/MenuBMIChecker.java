package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MenuBMIChecker extends AppCompatActivity {

    private EditText inputHeight, inputWeight;
    private Button submitBtn;
    private TextView result1, result2, result3;
    private ImageView navigationEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_bmichecker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        inputHeight = findViewById(R.id.inputHeight);
        inputWeight = findViewById(R.id.inputWeight);
        submitBtn = findViewById(R.id.submitBtn);
        result1 = findViewById(R.id.resultText1);
        result2 = findViewById(R.id.resultText2);
        result3 = findViewById(R.id.resultText3);
        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navigationEditProfile = findViewById(R.id.MenuEditProfile);

        //Navigation Bar
        navbar.setSelectedItemId(R.id.itemBMIChecker);

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
                    return true;
                }
                return false;
            }
        });

        navigationEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuBMIChecker.this, EditProfile.class);
                startActivity(intent);
            }
        });

        //Submit
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String height = inputHeight.getText().toString();
                String weight = inputWeight.getText().toString();

                if(!height.isEmpty() && !weight.isEmpty()){
                    double heightDouble = Double.parseDouble(height) / 100; // Ubah ke meter
                    double weightDouble = Double.parseDouble(weight);

                    double bmi = weightDouble / (heightDouble * heightDouble);
                    String category;
                    if (bmi < 18.5) {
                        category = "UNDERWEIGHT";
                        result1.setText("Your BMI is: " + String.format("%.2f", bmi) + "\nCategory: " + category);
                        result2.setText("Baca dan ikuti tips-tips yang diberikan");
                        result3.setText("SEMANGAT!!");
                    } else if (bmi >= 18.5 && bmi < 24.9) {
                        category = "NORMAL WEIGHT";
                        result1.setText("Your BMI is: " + String.format("%.2f", bmi) + "\nCategory: " + category);
                        result2.setText("Berat dan tinggi badanmu ideal");
                        result3.setText("Pertahankan!!");
                    } else if (bmi >= 25 && bmi < 29.9) {
                        category = "OVERWEIGHT";
                        result1.setText("Your BMI is: " + String.format("%.2f", bmi) + "\nCategory: " + category);
                        result2.setText("Baca dan ikuti tips-tips yang diberikan");
                        result3.setText("SEMANGAT!!");
                    } else {
                        category = "OBESE";
                        result1.setText("Your BMI is: " + String.format("%.2f", bmi) + "\nCategory: " + category);
                        result2.setText("Baca dan ikuti tips-tips yang diberikan");
                        result3.setText("SEMANGAT!!");
                    }

                }
            }
        });
    }
    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}