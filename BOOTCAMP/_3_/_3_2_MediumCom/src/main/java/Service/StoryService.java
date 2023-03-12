package Service;

import Model.BlogStatus;
import repository.StoryRepository;
import Model.Story;
import Model.Tag;

import java.util.List;

public class StoryService {
    private final StoryRepository storyRepository = new StoryRepository();

    public void createStory(Story story){
        storyRepository.createStory(story);
    }

    public List<Story> getAllStories(){
        return storyRepository.getAllStories();
    }

    public void addStoryToTag(Story story, Tag tag){
        storyRepository.addStoryToTag(story, tag);
    }

    public void removeStoryFromTag(Story story, Tag tag){
        storyRepository.removeStoryFromTag(story, tag);
    }

    public void deleteStory(Story story){
        storyRepository.setStoryStatus(story, BlogStatus.DELETED);
    }
}
