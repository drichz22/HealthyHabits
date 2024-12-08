package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.URI;

public class EditProfile extends AppCompatActivity {

    ImageView berandaNavigation, editProfPicImg;
    TextView editUsername, editEmail;
    Button signOutBtn;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        berandaNavigation = findViewById(R.id.MenuBeranda);
        editProfPicImg = findViewById(R.id.editProfPicImg);
        editUsername = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        signOutBtn = findViewById(R.id.signOutBtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(EditProfile.this, gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(EditProfile.this);
        if (acct != null) {
            String googleName = acct.getDisplayName();
            String googleEmail = acct.getEmail();
            Uri googleImg = acct.getPhotoUrl();

            if (googleImg != null) {
                editProfPicImg.setImageURI(googleImg);
            }
            editUsername.setText(googleName);
            editEmail.setText(googleEmail);
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            String email = sharedPreferences.getString("email", null);
            int userImage = sharedPreferences.getInt("userImage", -2);

            editProfPicImg.setImageResource(userImage);
            editUsername.setText(username);
            editEmail.setText(email);
        }

        berandaNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, Beranda.class);
                startActivity(intent);
            }
        });

        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
                if (sharedPreferences != null) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }

                GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(EditProfile.this, GoogleSignInOptions.DEFAULT_SIGN_IN);
                googleSignInClient.signOut()
                        .addOnCompleteListener(EditProfile.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(EditProfile.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}