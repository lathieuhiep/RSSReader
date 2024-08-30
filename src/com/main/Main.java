package com.main;

import com.rss.RSSParse;

public class Main {
    public static void main(String[] args) {
        RSSParse rssParse = new RSSParse();

        String link = "https://drive.usercontent.google.com/download?id=1OVsBLrL4b3k_Wxgm_miRiH7RNx6rzVDp&export=download&authuser=0";
        String savePath = "D:/java-exercises/bai-tap-buoi-19/downloads";

        rssParse.download(link, savePath);
        rssParse.parse(savePath + "/trangchu.rss");
    }
}