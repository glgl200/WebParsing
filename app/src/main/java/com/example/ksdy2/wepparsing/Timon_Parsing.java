package com.example.ksdy2.wepparsing;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by ksdy2 on 2017-09-09.
 */

public class Timon_Parsing extends AsyncTask<Void, Void, Void> {
    private String search_url = "http://search.ticketmonster.co.kr/search/?keyword=";
    private int total_list = 0; // 검색어를 통한 결과상품 총 아이템 개수
    private int curr_itemcount = 0; //현재 리스트에 추가하고있는 아이템
    int nCurrentRendingDealCnt = 24; // 초기페이지 25개 ,뉴 페이지 24개의 아이템씩 로드
    private String keword = "";

    Timon_Parsing(String input_edit) {
        keword = input_edit;
        this.search_url += keword;
    }

    @Override
    protected void onPreExecute() {

        Log.i("검색할 주소", "" + search_url);
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Document googleDocument = Jsoup.connect(search_url).get();
            Element btnK = googleDocument.select("span[class=res_cnt]").first();
            Log.e("아이템개수", btnK.text().toString());
            String temp_result = btnK.text().toString().trim();
            temp_result = temp_result.replace(",", "");
            total_list = Integer.parseInt(temp_result);


        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            int page_cont = total_list / nCurrentRendingDealCnt + 1;
            Log.e("총 페이지수", "" + page_cont);
            for (int i = 1; i <= page_cont; i++) {
                URL url = new URL("http://search.ticketmonster.co.kr/api/search/deals?page=" + i + "&keyword=" + keword);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                if (conn != null) {
                    conn.setConnectTimeout(5000);
                    String result = readStream(conn.getInputStream());
                    Log.i("페이지 넘버 //여기까진 성공", "" + i);
                    conn.disconnect();
                    showResult(result.trim());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }


    private String readStream(InputStream in) throws IOException {
        StringBuilder jsonHtml = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = null;

        while ((line = reader.readLine()) != null)
            jsonHtml.append(line);

        reader.close();
        return jsonHtml.toString();
    }

    private void showResult(String result) {


        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonObject1.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String url = "https://ticketmonster.co.kr/deal/" + item.getString("dealSrl");
                Log.e("url", "" + url);
                curr_itemcount++;
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements script = doc.select("script");
                    String sp =script.toString().substring(script.toString().indexOf("vendor_info"), script.toString().indexOf(",\"zoom\""));

                    Log.i("제이슨",""+sp);

                    //   String str ="{\"list\":"+  + "}]}";
                    /*if(str!=null) {
                        JSONObject object = new JSONObject(str);
                        JSONArray array = object.getJSONArray("list");

                        Log.i("제목", "" + array.getJSONObject(0).getString("name"));
                        Log.i("주소", "" + array.getJSONObject(0).getString("phone"));
                        Log.i("연락처", "" + array.getJSONObject(0).getString("address"));
                    }
                    else{
                        continue;

                    }
*/

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            Log.d(TAG, "showResult : ", e);
        }
    }
}

