package com.meanwhile.flatmates.init;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.meanwhile.flatmates.R;

/**
 * Created by mengujua on 10/12/17.
 */

class PersonInputView extends LinearLayout{
    private TextInputLayout mNameField;
    private TextInputLayout mEmailField;

    public PersonInputView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PersonInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PersonInputView(@NonNull Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.view_person_input, this, true);
        mNameField = findViewById(R.id.name);
        mEmailField = findViewById(R.id.email);
    }

    public Person getPerson(){
        return new Person(mNameField.getEditText().getText().toString(), mEmailField.getEditText().getText().toString());
    }

    public class Person {
        String name;
        String email;

        public Person(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

}
