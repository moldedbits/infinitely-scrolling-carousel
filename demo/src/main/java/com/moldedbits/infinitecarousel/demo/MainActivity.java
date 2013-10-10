package com.moldedbits.infinitecarousel.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showFragment(new SimpleDemoFragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.first_stage:
                showFragment(new FirstStageFragment());
                break;
            case R.id.second_stage:
                showFragment(new SecondStageFragment());
                break;
            case R.id.demo_simple:
                showFragment(new SimpleDemoFragment());
                break;
            case R.id.demo_click:
                showFragment(new ClickCarouselFragment());
                break;
            case R.id.demo_image:
                showFragment(new ImageDemoFragment());
                break;
            case R.id.demo_carousel:
                showFragment(new CarouselFragment());
                break;
            case R.id.demo_extended_carousel:
                showFragment(new ExtendedCarouselFragment());
                break;
        }
        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
