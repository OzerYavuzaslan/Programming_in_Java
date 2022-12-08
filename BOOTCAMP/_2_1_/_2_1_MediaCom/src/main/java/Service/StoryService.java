package Service;

import Dao.StoryDao;
import Model.Story;
import Model.Tag;

import java.util.List;

public class StoryService {
    private final StoryDao storyDao = new StoryDao();

    public void createStory(Story story){
        storyDao.createStory(story);
    }

    public List<Story> getAllStories(){
        return storyDao.getAllStories();
    }

    public void addStoryToTag(Story story, Tag tag){
        storyDao.addStoryToTag(story, tag);
    }

    public void removeStoryFromTag(Story story, Tag tag){
        storyDao.removeStoryFromTag(story, tag);
    }
}
