package Dao;

import Model.Story;
import Model.Tag;

import java.util.ArrayList;
import java.util.List;

public class StoryDao {
    private final static List<Story> STORY_LIST = new ArrayList<>();
    private final TagDao tagDao = new TagDao();

    public void createStory(Story story){
        STORY_LIST.add(story);
    }

    public List<Story> getAllStories(){
        return STORY_LIST;
    }

    public void addStoryToTag(Story story, Tag tag){
        List<Tag> tagList = tagDao.getAllTags();

        if (!tagList.contains(tag))
            System.out.println("There is no such a tag in the TagList. " + tag.getTagName());
        else{
            tag.getStoryList().add(story);
            System.out.println(story.getTag() + " has been added to the " + tag.getTagName());
        }
    }

    public void removeStoryFromTag(Story story, Tag tag){
        List<Tag> tagList = tagDao.getAllTags();

        if (!tagList.contains(tag)){
            System.out.println("There is no " + tag.getTagName() + " in the tag list!");
            return;
        }

        if (!STORY_LIST.contains(story)){
            System.out.println("There is no such a story in the story list " + story.getTitle() + " | " + story.getAuthor());
            return;
        }

        tagList.remove(story.getTag());
    }
}
