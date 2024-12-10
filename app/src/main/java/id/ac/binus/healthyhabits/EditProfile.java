package id.ac.binus.healthyhabits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.net.URI;

public class EditProfile extends AppCompatActivity {

    private Switch nightModeSwitch;
    private boolean nightMode;
    private ImageView berandaNavigation, editProfPicImg;
    private TextView editUsername, editEmail;
    private Button signOutBtn;
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private LinearLayout editProfPicBtn;

    private SharedPreferences modePreferences;
    private SharedPreferences.Editor modeEditor;

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
        editProfPicBtn = findViewById(R.id.editProfPicBtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(EditProfile.this, gso);
        nightModeSwitch = findViewById(R.id.darkModeSwitcher);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(EditProfile.this);
        if (acct != null) {
            String googleName = acct.getDisplayName();
            String googleEmail = acct.getEmail();
            Uri googleImg = acct.getPhotoUrl();

            if (googleImg != null) {
                Glide.with(this).load(googleImg).into(editProfPicImg);
            } else {
                editProfPicImg.setImageResource(R.drawable.default_profpic);
            }

            editUsername.setText(googleName);
            editEmail.setText(googleEmail);
        } else {
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
            String username = sharedPreferences.getString("username", null);
            String email = sharedPreferences.getString("email", null);
            int userImage = sharedPreferences.getInt("userImage", R.drawable.default_profpic);

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

        editProfPicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(EditProfile.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
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

        modePreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = modePreferences.getBoolean("night", false);

        if(nightMode){
            nightModeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        nightModeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nightMode){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    modeEditor = modePreferences.edit();
                    modeEditor.putBoolean("night", false);
                }
                else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    modeEditor = modePreferences.edit();
                    modeEditor.putBoolean("night", true);
                }
                modeEditor.apply();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        editProfPicImg.setImageURI(uri);
    }

}