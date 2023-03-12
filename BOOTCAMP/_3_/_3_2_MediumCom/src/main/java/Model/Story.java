package Model;

public class Story {
    private Tag tag;
    private String title;
    private String content;
    private User author;
    private BlogStatus blogStatus = BlogStatus.DRAFT;

    public Story(){

    }

    public Story(String title, String content, User author) {
        setTitle(title);
        setContent(content);
        setAuthor(author);
    }

    public BlogStatus getBlogStatus() {
        return blogStatus;
    }

    public void setBlogStatus(BlogStatus blogStatus) {
        this.blogStatus = blogStatus;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTagList(Tag tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
