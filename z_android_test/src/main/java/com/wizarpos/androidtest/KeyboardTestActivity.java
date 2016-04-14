package com.wizarpos.androidtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.wizarpos.androidtest.view.KeyboardView;

public class KeyboardTestActivity extends AppCompatActivity {
    private EditText test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KeyboardView kbv = (KeyboardView) findViewById(R.id.kb);
        test = (EditText) findViewById(R.id.edt_test);
        kbv.setOnKeyBoardInput(new KeyboardView.OnKeyBoardInput() {

            @Override
            public void onKeyClick(String value) {
                test.setText(value);
            }
        });
    }
}
