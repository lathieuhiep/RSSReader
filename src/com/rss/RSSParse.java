package com.rss;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RSSParse {
    private ArrayList<ItemNews> listNews = new ArrayList<>();

    public void download(String link, String savePath) {
        try {
            URLConnection conn = new URL(link).openConnection();
            InputStream in = conn.getInputStream();

            // lay ten file
            String fileName = new File(link).getName();

            // tao thu muc
            new File(savePath).mkdirs();

            File f2 = new File(savePath + "/" + fileName);
            FileOutputStream out = new FileOutputStream(f2);

            byte[] gioByte = new  byte[1024];

            int slByte = in.read(gioByte);
            while ( slByte > 0 ) {
                // ghi luon file moi
                out.write(gioByte, 0, slByte);
                slByte = in.read(gioByte);
            }

            in.close();
            out.close();
            System.out.println("Download thanh cong");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
