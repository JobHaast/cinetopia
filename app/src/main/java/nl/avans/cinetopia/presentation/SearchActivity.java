package nl.avans.cinetopia.presentation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import nl.avans.cinetopia.R;

public class SearchActivity extends AppCompatActivity {

    private androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
