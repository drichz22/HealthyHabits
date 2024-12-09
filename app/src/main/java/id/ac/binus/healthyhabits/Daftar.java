package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Daftar extends AppCompatActivity {

    EditText daftarUsername, daftarEmail, daftarPassword, confirmPassword;
    Button daftarBtn;
    TextView directToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        daftarUsername = findViewById(R.id.daftarUsername);
        daftarEmail = findViewById(R.id.daftarEmail);
        daftarPassword = findViewById(R.id.daftarPassword);
        confirmPassword = findViewById(R.id.daftarConfirmPassword);
        daftarBtn = findViewById(R.id.daftarBtn);
        directToLogin = findViewById(R.id.directToLogin);

        directToLogin.setText(Html.fromHtml("<u>Login</u>", Html.FROM_HTML_MODE_LEGACY));

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(Daftar.this);

        directToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Daftar.this, Login.class);
                startActivity(intent);
            }
        });

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = daftarUsername.getText().toString().trim();
                String email = daftarEmail.getText().toString().trim();
                String password = daftarPassword.getText().toString();
                String confirmPasswordText = confirmPassword.getText().toString();

                if(username.length() <= 5){
                    Toast.makeText(Daftar.this, "Username harus lebih dari 5 karakter!", Toast.LENGTH_SHORT).show();
                }
                else if (!dbHelper.isUsernameUnique(username)){
                    Toast.makeText(Daftar.this, "Username sudah diambil!", Toast.LENGTH_SHORT).show();
                }
                else if (email.length() <= 5 || !email.contains("@")){
                    Toast.makeText(Daftar.this, "Email tidak valid dan kurang dari 5 karakter!", Toast.LENGTH_SHORT).show();
                }
                else if (!dbHelper.isEmailUnique(email)){
                    Toast.makeText(Daftar.this, "Email sudah diambil!", Toast.LENGTH_SHORT).show();
                }
                else if (password.length() <= 5 || !password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")){
                    Toast.makeText(Daftar.this, "Password harus alphanumeric dan lebih dari 5 karakter!", Toast.LENGTH_SHORT).show();
                }
                else if (!confirmPasswordText.equals(password)){
                    Toast.makeText(Daftar.this, "Password tidak cocok!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isRegistered = dbHelper.daftar(username, email, password, -1);
                    if(isRegistered){
                        Toast.makeText(Daftar.this, "Daftar Berhasil!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Daftar.this, Login.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Daftar.this, "Daftar Gagal. Coba Lagi!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}