package com.edvardas.flickrbrowserjava;

import java.util.Objects;

public class Photo {
    private String title;
    private String author;
    private String authorId;
    private String link;
    private String tags;
    private String image;

    public Photo(String title, String author, String authorId, String link, String tags, String image) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        this.tags = tags;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getLink() {
        return link;
    }

    public String getTags() {
        return tags;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return Objects.equals(title, photo.title) &&
                Objects.equals(author, photo.author) &&
                Objects.equals(authorId, photo.authorId) &&
                Objects.equals(link, photo.link) &&
                Objects.equals(tags, photo.tags) &&
                Objects.equals(image, photo.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, authorId, link, tags, image);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", authorId='" + authorId + '\'' +
                ", link='" + link + '\'' +
                ", tags='" + tags + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
