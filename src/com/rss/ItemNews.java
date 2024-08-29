package com.rss;

public class ItemNews {
    private String title, linkImg, description, linkPost, date;

    public ItemNews(String title, String linkImg, String description, String linkPost, String date) {
        this.title = title;
        this.linkImg = linkImg;
        this.description = description;
        this.linkPost = linkPost;
        this.date = date;
    }

    public String toString() {
        return "Tiêu đề: " + title + "\n"
                + "Link ảnh: " + linkImg + "\n"
                + "Mô tả: " + description + "\n"
                + "Link bài viết: " + linkPost + "\n"
                + "Ngày tháng: " + date + "\n";
    }
}
