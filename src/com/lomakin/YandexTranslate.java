package com.lomakin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class YandexTranslate {

    public String translate(String lang, String input) throws IOException {

        String key = "trnsl.1.1.20180702T143300Z.cc8ba091b98300af.799672f9aff9d9fd7996b28b5c2e50a4567fa3e8";
        URL url = new URL("https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + key);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

        dataOutputStream.writeBytes("text=" + URLEncoder.encode(input, "UTF-8") + "&lang=" + lang);

        InputStream response = connection.getInputStream();
        String json = new java.util.Scanner(response).nextLine();
        int start = json.indexOf("[");
        int end = json.indexOf("]");

        return json.substring(start + 2, end - 1);
    }
}
