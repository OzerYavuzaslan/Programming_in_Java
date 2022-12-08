package Model;

import java.util.ArrayList;
import java.util.List;

public class Tag {
    private String tagName;
    private List<Story> storyList;

    public Tag(String tagName) {
        setTagName(tagName);
        setStoryList(new ArrayList<>());
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }
}
