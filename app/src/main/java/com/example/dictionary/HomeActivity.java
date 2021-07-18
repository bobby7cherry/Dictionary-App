package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import static com.example.dictionary.Items.*;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    SearchView searchView;
    ListView listView;


    public void initViews(){
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        searchView = (SearchView)findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
    }


    protected void onCreate(Bundle savedInstanceState, CharSequence newText) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        toggle = new ActionBarDrawerToggle(this , drawerLayout , R.string.Open , R.string.close);
        toggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("😄 New Update Available");


        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1 , synonym){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                final TextView textView = (TextView) view.findViewById(android.R.id.text1);

                textView.setTextSize(22);

                return view;
            }
        };
        listView.setAdapter(arrayAdapter);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayAdapter.getFilter().filter(newText);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), Items.synonym[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu , menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        if(menuItem.getGroupId() == R.id.dashboard){
            drawerLayout.closeDrawers();
        }
        else if(menuItem.getItemId() == R.id.shareapp){
            ShareApp();
        }
        else if(menuItem.getItemId() == R.id.Exit){
            finish();
        }
        return true;
    }

    public void ShareApp () {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id= " + getPackageName());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}