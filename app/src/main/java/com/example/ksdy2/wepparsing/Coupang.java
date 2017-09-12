package com.example.ksdy2.wepparsing;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ksdy2 on 2017-09-09.
 */

public class Coupang extends AsyncTask<Void, Void, Void> {
    private int total_item = 0;
    private String search_url = "http://www.coupang.com/np/search?q=";
    private String keword = "";
    private int page_count=0;
    Coupang(String input_edit) {
        keword = input_edit;
        this.search_url += keword;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Connection.Response response = Jsoup.connect(search_url)
                    .method(Connection.Method.GET)
                    .execute();
            Document googleDocument = response.parse();
            Elements elements = googleDocument.select("strong");

            String total_count= elements.text().toString().replace("(","").replace(")","").replace(",","");
            total_item=Integer.parseInt(total_count);
            page_count=total_item / 36+1;

            Log.i("페이지", "" + page_count);

            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
