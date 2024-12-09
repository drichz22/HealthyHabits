package id.ac.binus.healthyhabits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MenuPlanning extends AppCompatActivity {
    private CalendarView calendarView;
    private ListView plansListView;
    private Button addPlanButton;
    private UserDatabaseHelper dbHelper;
    private int currentUserId; // Ubah sesuai user yang login
    private String selectedDate;  // Format YYYY-MM-DD
    private ArrayList<String> plansList;
    private ArrayAdapter<String> adapter;
    private HashMap<String, Integer> planIdMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_planning);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userId", -1);
        calendarView = findViewById(R.id.calendarView);
        plansListView = findViewById(R.id.listPlan);
        addPlanButton = findViewById(R.id.addBtn);
        dbHelper = new UserDatabaseHelper(this);
        plansList = new ArrayList<>();
        planIdMap = new HashMap<>();
        BottomNavigationView navbar = findViewById(R.id.bottomNavigationView);
        navbar.setSelectedItemId(R.id.itemPlanning);

        navbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.itemBeranda) {
                    navigateTo(Beranda.class);
                    return true;
                } else if (item.getItemId() == R.id.itemPlanning) {
                    return true;
                } else if (item.getItemId() == R.id.itemBMIChecker) {
                    navigateTo(MenuBMIChecker.class);
                    return true;
                }
                return false;
            }
        });

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, plansList);
        plansListView.setAdapter(adapter);
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Format tanggal yang dipilih
            selectedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
            loadPlansForSelectedDate();
        });

        addPlanButton.setOnClickListener(v -> {
            if (selectedDate != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add Plan");
                final EditText input = new EditText(this);
                builder.setView(input);

                builder.setPositiveButton("Save", (dialog, which) -> {
                    String planDescription = input.getText().toString();
                    dbHelper.insertPlan(currentUserId, selectedDate, planDescription);
                    loadPlansForSelectedDate();
                });

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            }
        });

        plansListView.setOnItemClickListener((parent, view, position, id) -> {
            String planDescription = plansList.get(position);
            int planId = planIdMap.get(planDescription);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Plan")
                    .setMessage("Are you sure you want to delete this plan?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        dbHelper.deletePlanById(planId);
                        loadPlansForSelectedDate();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        selectedDate = getTodayDate();
        loadPlansForSelectedDate();
    }
    private void navigateTo(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
    private String getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        return String.format("%d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void loadPlansForSelectedDate() {
        plansList.clear();
        planIdMap.clear();

        Cursor cursor = dbHelper.getPlanByDate(currentUserId, selectedDate);
        while (cursor.moveToNext()) {
            int planId = cursor.getInt(cursor.getColumnIndexOrThrow("plan_id"));
            String description = cursor.getString(cursor.getColumnIndexOrThrow("plan_desc"));
            plansList.add(description);
            planIdMap.put(description, planId);
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }
}