package com.application.granthavikri.ui;

import static com.application.granthavikri.Utility.tag;
import static com.application.granthavikri.Utility.types;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.application.granthavikri.R;
import com.application.granthavikri.Utility;
import com.application.granthavikri.repository.EventRepository;
import com.application.granthavikri.repository.GroupRepository;
import com.application.granthavikri.repository.InventoryRepository;
import com.application.granthavikri.viewmodel.BookViewModel;
import com.application.granthavikri.viewmodel.CDViewModel;
import com.application.granthavikri.viewmodel.EventViewModel;
import com.application.granthavikri.viewmodel.FrameViewModel;
import com.application.granthavikri.viewmodel.GroupViewModel;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {


    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private Toolbar toolbar;


    private BookViewModel bookViewModel;

    private FrameViewModel frameViewModel;

    private CDViewModel cdViewModel;

    private EventViewModel eventViewModel;

    private ConstraintLayout gridLayout_for_menu;

    private FrameLayout container;

    private GroupViewModel groupViewModel;

    private CardView inventory_home, group_home, event_home, sale_home, report_home, exit_button;

    public GroupViewModel getGroupViewModel() {
        return groupViewModel;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.home_toolbar);

        setSupportActionBar(toolbar);

        gridLayout_for_menu = findViewById(R.id.gridlayout_for_menu);
        container = findViewById(R.id.container);

        inventory_home = findViewById(R.id.inventory_home_button);
        inventory_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        loadFragment(new InventoryFragment(), "inventoryFragment");
                    }
                }, 400);
            }
        });

        group_home = findViewById(R.id.group_home_button);
        group_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        loadFragment(new GroupFragment(), "groupFragment");
                    }
                }, 400);
            }
        });

        event_home = findViewById(R.id.event_home_button);
        event_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        loadFragment(new EventFragment(), "eventFragment");
                    }
                }, 400);
            }
        });

        sale_home = findViewById(R.id.sale_home_button);
        sale_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        loadFragment(new SaleFragment(), "saleFragment");
                    }
                }, 400);
            }
        });

        report_home = findViewById(R.id.report_home_button);
        report_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        loadFragment(new ReportFragment(), "reportFragment");
                    }
                }, 400);
            }
        });

        exit_button = findViewById(R.id.exit_home_button);
        exit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Open the next fragment here
                        setExit();
                    }
                }, 400);
            }
        });

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        cdViewModel = new ViewModelProvider(this).get(CDViewModel.class);
        frameViewModel = new ViewModelProvider(this).get(FrameViewModel.class);
        groupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        getSupportActionBar().setTitle(Utility.getKendra());

        drawerLayout.addDrawerListener(toggle);


        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if(id==R.id.inventory){
                    loadFragment(new InventoryFragment(), "inventoryFragment");
                }
                else if(id==R.id.group){
                    loadFragment(new GroupFragment(), "groupFragment");
                }
                else if(id==R.id.event){
                    loadFragment(new EventFragment(), "eventFragment");
                }
                else if(id==R.id.sale){
                    loadFragment(new SaleFragment(), "saleFragment");
                }
                else if(id==R.id.report){
                    loadFragment(new ReportFragment(), "reportFragment");
                }
                else {
                    setExit();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        Toolbar toolbar = findViewById(R.id.home_toolbar);

// Set navigation icon tint color
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    private void loadFragment(Fragment fragment, String tag){
        gridLayout_for_menu.setVisibility(View.GONE);
        container.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        int count = fragmentManager.getBackStackEntryCount();
        while(count>0){
            fragmentManager.popBackStack();
            count-=1;
        }

        Fragment fragment1 = fragmentManager.findFragmentById(R.id.container);

        if (fragment1 != null && fragment1.isAdded()) {
            fragmentTransaction.replace(R.id.container, fragment, tag);
        } else {
            // Fragment is not added to the layout
            fragmentTransaction.add(R.id.container, fragment, tag);
        }
        fragmentTransaction.commit();

    }

    private void setExit(){
        Utility.setKendra("");
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    public BookViewModel getBookViewModel() {
        return bookViewModel;
    }

    public FrameViewModel getFrameViewModel() {
        return frameViewModel;
    }

    public CDViewModel getCdViewModel() {
        return cdViewModel;
    }

    public EventViewModel getEventViewModel() {
        return eventViewModel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Clear the ViewModel
        getViewModelStore().clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(tag, "onresume executed");

        // Check if there are any fragments added to the container when the activity resumes
        checkIfContainerIsEmpty();
    }

    public void checkIfContainerIsEmpty() {
        // Find the container layout in the activity's layout XML file

        FragmentManager fragmentManager = getSupportFragmentManager(); // For support library
        Fragment fragment = fragmentManager.findFragmentById(R.id.container); // Replace R.id.fragment_container with your container ID
        if (fragment != null) {
            Log.d(tag, "Fragment is present in the container");
        } else {
            container.setVisibility(View.GONE);
            gridLayout_for_menu.setVisibility(View.VISIBLE);
            Log.d(tag, "No fragment is present in the container");
        }
    }

    public void switchScreens(){
        container.setVisibility(View.GONE);
        gridLayout_for_menu.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}