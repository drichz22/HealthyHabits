package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Login extends AppCompatActivity {

    EditText loginUsername, loginPassword;
    TextView directToDaftar;
    Button loginBtn;
    LinearLayout signInGoogleBtn;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(Login.this, gso);

        UserDatabaseHelper dbHelper = new UserDatabaseHelper(Login.this);
        directToDaftar = findViewById(R.id.directToDaftar);

        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);

        loginBtn = findViewById(R.id.loginBtn);
        signInGoogleBtn = findViewById(R.id.signInGoogleBtn);

        directToDaftar.setText(Html.fromHtml("<u>Daftar</u>", Html.FROM_HTML_MODE_LEGACY));

        directToDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Daftar.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = loginUsername.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();

                if(username.isEmpty()){
                    Toast.makeText(Login.this, "Username cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(Login.this, "Password cannot be empty!", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean isLoggedIn = dbHelper.login(username, password);
                    if(isLoggedIn){
                        Toast.makeText(Login.this, "Login berhasil!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        String email = dbHelper.getEmailbyUsername(username);
                        if (email == null) email = "";

                        int userImage = dbHelper.getImagebyUsername(username);

                        editor.putString("username", username);
                        editor.putString("email", email);
                        editor.putInt("userImage", userImage);
                        editor.apply();

                        Intent intent = new Intent(Login.this, Beranda.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(Login.this, "Username/Password invalid!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signInGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = gsc.getSignInIntent();
                startActivityForResult(signIntent, 1000);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                Intent intent = new Intent(Login.this, Beranda.class);
                startActivity(intent);
            } catch (ApiException e) {
                Toast.makeText(Login.this, "Gagal untuk sign-in dengan Google!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}