package com.meanwhile.flatmates.init;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.meanwhile.flatmates.FlatActivity;
import com.meanwhile.flatmates.R;
import com.meanwhile.flatmates.ViewModelFactory;
import com.meanwhile.flatmates.repository.Resource;
import com.meanwhile.flatmates.repository.model.Flat;
import com.meanwhile.flatmates.repository.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Screen to create a new flat
 */
public class CreateGroupActivity extends AppCompatActivity {

    public static final String RET_FLAT_ID = "flatId";
    private TextInputLayout mNameField;
    private LinearLayout mPeopleContainer;
    private Button mAddPersonButton;
    private FloatingActionButton mFab;

    //holds references to PersonInputViews for easier value retrieval
    private List<PersonInputView> mPersonInputs;
    private CreateGroupViewModel mViewModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CreateGroupActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameField = findViewById(R.id.group_name);
        mPeopleContainer = findViewById(R.id.people_container);
        mPersonInputs = new ArrayList<>();
        mPersonInputs.add((PersonInputView) findViewById(R.id.flatmate_one));
        mPersonInputs.add((PersonInputView) findViewById(R.id.flatmate_two));

        mAddPersonButton = findViewById(R.id.add_person_button);
        mAddPersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add new view to the the layout
                mPeopleContainer.addView(new PersonInputView(getApplicationContext()));
            }
        });

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check args
                List<User> userList = new ArrayList<>();
                for (PersonInputView personInput : mPersonInputs) {
                    PersonInputView.Person person = personInput.getPerson();

                    if (TextUtils.isEmpty(person.getEmail()) && TextUtils.isEmpty(person.getName())){
                        //TODO show error
                        break;

                    } else {
                        userList.add(new User(person.getName(), person.getEmail()));
                    }
                }

                mViewModel.onCreateGroup(mNameField.getEditText().getText().toString(), userList).observe(CreateGroupActivity.this, new Observer<Resource<Flat>>() {
                    @Override
                    public void onChanged(@Nullable Resource<Flat> flatResource) {
                        switch (flatResource.status) {
                            case Resource.SUCCESS:
                                Toast.makeText(CreateGroupActivity.this, getString(R.string.createflat_flat_created), Toast.LENGTH_LONG).show();
                                //Move to flat screen
                                Intent data = new Intent();
                                data.putExtra(RET_FLAT_ID, flatResource.data.getId());
                                setResult(RESULT_OK, data );
                                finish();
                                break;
                            case Resource.ERROR:
                                Toast.makeText(CreateGroupActivity.this, getString(R.string.createflat_error), Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });
            }
        });

        ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        mViewModel = ViewModelProviders.of(this, factory).get(CreateGroupViewModel.class);
        mViewModel.init();
    }

}
