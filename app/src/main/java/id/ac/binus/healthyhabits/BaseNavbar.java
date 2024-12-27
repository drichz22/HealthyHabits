        package id.ac.binus.healthyhabits;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.MenuItem;

        import androidx.activity.EdgeToEdge;
        import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.graphics.Insets;
        import androidx.core.view.ViewCompat;
        import androidx.core.view.WindowInsetsCompat;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.google.android.material.navigation.NavigationBarView;

        public class BaseNavbar extends AppCompatActivity {
//            protected BottomNavigationView navbar;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_base_navbar);
                ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                });

//                navbar = findViewById(R.id.bottomNavigationView);
//                navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        if (item.getItemId() == R.id.itemBeranda) {
//                            navigateTo(Beranda.class);
//                            return true;
//                        } else if (item.getItemId() == R.id.itemPlanning) {
//                            navigateTo(MenuPlanning.class);
//                            return true;
//                        } else if (item.getItemId() == R.id.itemBMIChecker) {
//                            navigateTo(MenuBMIChecker.class);
//                            return true;
//                        } else if (item.getItemId() == R.id.itemProfile) {
//                            navigateTo(EditProfile.class);
//                            return true;
//                        }
//                        return false;
//                    }
//                });
            }

//            private void navigateTo(Class<?> activityClass) {
//                Intent intent = new Intent(this, activityClass);
//                startActivity(intent);
//                finish(); // Jika ingin mengganti aktivitas dengan navbar, gunakan finish()
//            }
        }