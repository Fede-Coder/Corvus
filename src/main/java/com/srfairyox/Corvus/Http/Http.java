package com.srfairyox.Corvus.Http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class Http {
    public static String readResponse(HttpURLConnection conn) {
        try {
            InputStream inputStream;
            int responseCode = conn.getResponseCode();
            if (200 <= responseCode && responseCode <= 299) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            if (inputStream == null)
                return "ERR";
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String currentLine;
            while ((currentLine = in.readLine()) != null)
                response.append(currentLine);
            in.close();
            return response.toString();
        } catch (Exception e) {
            return "ERR";
        }
    }
}
