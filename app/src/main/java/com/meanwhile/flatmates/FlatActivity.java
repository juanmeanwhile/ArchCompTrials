package com.meanwhile.flatmates;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.meanwhile.flatmates.feed.FeedFragment;
import com.meanwhile.flatmates.init.CreateGroupActivity;
import com.meanwhile.flatmates.mates.MatesFragment;
import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.task.TaskFragment;

import java.util.List;
import java.util.prefs.PreferencesFactory;

/**
 * Host the fragments showing information about the flat, like task, flatmate o history.
 */
public class FlatActivity extends AppCompatActivity {
    private static final int REQ_CREATE_GROUP = 10001;
    private static final String ARG_FLAT_ID = "flatId";
    private BottomNavigationView mBottomNav;
    private long mCurrentGroupId;
    private FlatViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNav = findViewById(R.id.bottom_navigation);
        mBottomNav.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_feed:
                                loadFragment(FeedFragment.newInstance());
                                break;
                            case R.id.action_people:
                                loadFragment(MatesFragment.newInstance());
                                break;
                            case R.id.action_tasks:
                                loadFragment(TaskFragment.newInstance());
                                break;
                        }
                        return true;
                    }
                });

        //TODO Replace by default
        //Currently one single group is supported at the same time. So, if there is not a group Id saved on preferences,
        //pen create group screen.

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(FlatViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();

        mViewModel.getFlats().observe(this, new Observer<List<Flat>>() {
            @Override
            public void onChanged(@Nullable List<Flat> flats) {
                if (flats.size() > 0) {
                    loadFlat();
                } else {
                    startActivityForResult(CreateGroupActivity.newIntent(FlatActivity.this), REQ_CREATE_GROUP);
                }
            }
        });
    }

    private void loadFlat() {
        mBottomNav.setVisibility(View.VISIBLE);
        mBottomNav.setSelectedItemId(R.id.action_tasks);

    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_CREATE_GROUP) {
                //Group created
                mViewModel.onGroupCreated(data.getLongExtra(CreateGroupActivity.RET_FLAT_ID, 0));
            }
        }
    }
}
