package com.example.ksdy2.wepparsing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button timon, coupang, wemape, ect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        set_view();


    }

    public void set_view() {
        timon = (Button) findViewById(R.id.timon);
        coupang = (Button) findViewById(R.id.coupang);
        wemape = (Button) findViewById(R.id.wemape);
        ect = (Button) findViewById(R.id.ect);

        timon.setOnClickListener(this);
        coupang.setOnClickListener(this);
        wemape.setOnClickListener(this);
        ect.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent(this, Parsing_Class.class);
        String name = "";
        switch (view.getId()) {
            case R.id.timon:
                name = timon.getText().toString();
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.coupang:
                name = coupang.getText().toString();
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.wemape:
                name = wemape.getText().toString();
                intent.putExtra("name", name);
                startActivity(intent);
                break;
            case R.id.ect:
                intent.putExtra("name", "기타");
                startActivity(intent);

        }
    }
}
