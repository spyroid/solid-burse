package com.burse.shared;


public class FeedDto {

    private int id;
    private String title;
    private boolean favorite;

    public FeedDto(String title, int id) {
        setTitle(title);
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

}
