package com.main;

import com.rss.RSSParse;

public class Main {
    public static void main(String[] args) {
        RSSParse rssParse = new RSSParse();

        String link = "https://drive.google.com/file/d/1OVsBLrL4b3k_Wxgm_miRiH7RNx6rzVDp/view?usp=share_link";
        String savePath = "D:/java-exercises/bai-tap-buoi-19/downloads";

        rssParse.download(link, savePath);
    }
}