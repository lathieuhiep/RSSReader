package com.rss;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RSSParse {
    private ArrayList<ItemNews> listNews = new ArrayList<>();

    public void download(String link, String savePath) {
        try {
            URLConnection conn = new URL(link).openConnection();
            int fileSize = conn.getContentLength();
            InputStream in = conn.getInputStream();

            // dat ten file
            String fileName = "trangchu.rss";

            // tao thu muc
            new File(savePath).mkdirs();

            File f2 = new File(savePath + "/" + fileName);
            FileOutputStream out = new FileOutputStream(f2);

            byte[] gioByte = new  byte[1024];
            int slByte = in.read(gioByte);
            int downloadedSize = 0;
            int lastPercent = 0;

            while ( slByte > 0 ) {
                // ghi luon file moi
                out.write(gioByte, 0, slByte);

                // tính toán phần trăm tải
                downloadedSize += slByte;
                int percent = (int) (((double) downloadedSize / fileSize) * 100);

                while (lastPercent < percent) {
                    lastPercent++;
                    System.out.print("Download Progress: " + lastPercent + "%\r");

                    Thread.sleep(10);
                }

                slByte = in.read(gioByte);
            }

            in.close();
            out.close();
            System.out.println("Download thanh cong");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(String path) {
        File file = new File(path);
        StringBuilder text = new StringBuilder();

        try {
            if ( !file.exists() ) {
                System.out.println("File không tồn tại");
                return;
            }

            FileInputStream in = new FileInputStream(file);
            byte[] gioByte = new byte[1024];
            int soLuongByte = in.read(gioByte);

            while (soLuongByte > 0) {
                String chuoi = new String(gioByte, 0, soLuongByte);
                text.append(chuoi);

                soLuongByte = in.read(gioByte);
            }

            ArrayList<ItemNews> newsList = new ArrayList<>();
            if ( !text.isEmpty() ) {
                ArrayList<String> items = getItems(text);

                if ( !items.isEmpty() ) {
                    String title = "";
                    String tagDescription = "";
                    String linkImg = "";
                    String description = "";
                    String linkPost = "";
                    String date = "";

                   for ( String item : items ) {
                       if (item.contains("<title>") && item.contains("</title>")) {
                           title = item.substring(item.indexOf("<title>") + 7, item.indexOf("</title>"));
                       }

                       if (item.contains("<description>") && item.contains("</description>")) {
                           tagDescription = item.substring(item.indexOf("<description>") + 13, item.indexOf("</description>"));

                           // Lấy linkImg từ tagDescription
                           if (tagDescription.contains("<img src=\"")) {
                               int imgStartIndex = tagDescription.indexOf("<img src=\"") + 10;
                               int imgEndIndex = tagDescription.indexOf("\"", imgStartIndex);

                               if (imgStartIndex > 9 && imgEndIndex > imgStartIndex) {
                                   linkImg = tagDescription.substring(imgStartIndex, imgEndIndex);
                               }
                           }

                           // Lấy phần mô tả sau thẻ img
                           if (tagDescription.contains("/>")) {
                               description = tagDescription.substring(tagDescription.indexOf("/>") + 2).trim();
                           }
                       }

                       // Kiểm tra và lấy linkPost
                       if (item.contains("<link>") && item.contains("</link>")) {
                           linkPost = item.substring(item.indexOf("<link>") + 6, item.indexOf("</link>"));
                       }

                       // Kiểm tra và lấy date
                       if (item.contains("<pubDate>") && item.contains("</pubDate>")) {
                           date = item.substring(item.indexOf("<pubDate>") + 9, item.indexOf("</pubDate>"));
                       }

                       newsList.add( new ItemNews(title, linkImg, description, linkPost, date) );
                   }
                }
            }

            if ( !newsList.isEmpty() ) {
                System.out.println("-----------------------------------");

                for (ItemNews itemNews : newsList) {
                    System.out.println(itemNews);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> getItems(StringBuilder text) {
        ArrayList<String> items = new ArrayList<>();

        String textToString = text.toString();
        String itemTagStart = "<item>";
        String itemTagEnd = "</item>";

        int start = 0;
        while ( (start = textToString.indexOf(itemTagStart, start)) != -1 ) {
            int end = textToString.indexOf(itemTagEnd, start) + itemTagEnd.length();

            String item = textToString.substring(start, end).trim();

            String content = item.substring(itemTagStart.length(), item.length() - itemTagEnd.length()).trim();

            items.add(content);

            start = end;
        }

        return items;
    }
}
