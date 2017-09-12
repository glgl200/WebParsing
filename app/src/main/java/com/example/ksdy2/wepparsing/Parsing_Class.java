package com.example.ksdy2.wepparsing;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * Created by ksdy2 on 2017-09-04.
 */

public class Parsing_Class extends AppCompatActivity {
    private ImageView logo;
    private static String search_url = "";
    private EditText search_edit;
    private Button search_button;
    private int selec_number = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.parsing_page);

        Intent intent = getIntent();
        String str_name = intent.getStringExtra("name");
        set_view(str_name);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input_edit=search_edit.getText().toString();
                switch (selec_number){
                    case 1:
                        Timon_Parsing timon_parsing = new Timon_Parsing(input_edit);
                        timon_parsing.execute();
                        break;
                    case 2:
                        Coupang coupang = new Coupang(input_edit);
                        coupang.execute();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }
        });

    }

    public void set_view(String name) {
        logo = (ImageView) findViewById(R.id.logo_img);
        search_edit = (EditText) findViewById(R.id.searcg_txt);
        search_button = (Button) findViewById(R.id.search_btn);

        switch (name) {
            case "티몬":
                Glide.with(this).load("http://img2.tmon.kr/deals/banner/banner20_7527e.png").into(logo);

                selec_number = 1;
                break;
            case "쿠팡":
                search_url = "http://www.coupang.com/np/search?component=&q=";
                Glide.with(this).load("http://img2a.coupangcdn.com/image/coupang/common/logo_coupang_w350.png").into(logo);
                selec_number = 2;

                break;
            case "위메프":
                search_url = "http://search.wemakeprice.com/search?search_cate=top&search_keyword=";
                Glide.with(this).load("http://img.wemep.co.kr/banner/2017-09-04/32_1_301785ebaf5a6bdb5989678a4fc394312c0257f8.png").into(logo);
                selec_number = 3;

                break;
            case "기타":
                Glide.with(this).load("http://www.ddaily.co.kr/data/photos/20140623/art_1401928227.jpg").into(logo);
                selec_number = 4;

                break;
        }
    }
}

