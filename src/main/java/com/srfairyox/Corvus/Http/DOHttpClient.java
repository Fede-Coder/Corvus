package com.srfairyox.Corvus.Http;

import java.net.*;

public class DOHttpClient {

    public void initialize() {
        CookieManager cookies = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
        CookieHandler.setDefault(cookies);
    }

    public HttpURLConnection getConnection(String url, String sid) throws Exception {
        HttpURLConnection conn = (HttpURLConnection)(new URL(url)).openConnection();
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("Cookie", "dosid=" + sid);
        return conn;
    }

    public HttpURLConnection getConnection2(String url) throws Exception {
        HttpURLConnection conn = (HttpURLConnection)(new URL(url)).openConnection();
        conn.setInstanceFollowRedirects(false);
        return conn;
    }
}
