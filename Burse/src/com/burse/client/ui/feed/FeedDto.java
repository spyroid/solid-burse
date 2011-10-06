package com.burse.client.ui.feed;

import com.google.gwt.view.client.ProvidesKey;

public class FeedDto implements Comparable<FeedDto> {

    private int id;
    private String title;

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


    @Override
    public boolean equals(Object o) {
        if (o instanceof FeedDto) {
            return getId() == ((FeedDto) o).getId();
        }
        return false;
    }

    /**
     * The key provider that provides the unique ID of a contact.
     */
    public static final ProvidesKey<FeedDto> KEY_PROVIDER = new ProvidesKey<FeedDto>() {
        public Object getKey(FeedDto item) {
            return item == null ? null : item.getId();
        }
    };

    public int compareTo(FeedDto o) {
        return (o == null || o.title == null) ? -1 : -o.title.compareTo(title);
    }
}
