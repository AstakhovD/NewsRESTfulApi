package spring.task.news_application.model;

import java.util.Objects;

public class Article {

    private String author;
    private String source;
    private String title;
    private String url;
    private String description;
    private String imageToUrl;
    private String publishedAt;

    public Article(String author, String source, String title, String url, String description, String imageToUrl, String  publishedAt) {
        this.author = author;
        this.source = source;
        this.title = title;
        this.url = url;
        this.description = description;
        this.imageToUrl = imageToUrl;
        this.publishedAt = publishedAt;
    }

    public Article() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
    return url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageToUrl() {
        return imageToUrl;
    }

    public void setImageToUrl(String imageToUrl) {
        this.imageToUrl = imageToUrl;
    }

    public String  getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String  publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) && Objects.equals(source, article.source) &&
                Objects.equals(title, article.title) && Objects.equals(url, article.url)
                && Objects.equals(description, article.description) && Objects.equals(imageToUrl, article.imageToUrl)
                && Objects.equals(publishedAt, article.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, source, title, url, description, imageToUrl, publishedAt);
    }

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", imageToUrl='" + imageToUrl + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
