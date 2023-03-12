package com.medium.controller;

import Model.BlogStatus;
import Model.Story;
import Service.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/blogs")
public class StoryController {
    @Autowired
    private StoryService storyService;

    @PostMapping
    public Story create(Story story){
        storyService.createStory(story);
        return story;
    }

    @DeleteMapping
    private void delete(Story story){
        storyService.deleteStory(story);
    }

    @GetMapping
    public List<Story> getAll(){
        return storyService.getAllStories();
    }

    @PutMapping
    public Story update(BlogStatus blogStatus, Story story){
        Story tmpStory = null;

        if (storyService.getAllStories().stream().anyMatch(story1 -> story1.getTitle().equalsIgnoreCase(story.getTitle()))) {
            tmpStory = storyService.getAllStories().stream().filter(story1 -> story1.getTitle().equalsIgnoreCase(story.getTitle())).findFirst().get();

            if (Objects.nonNull(tmpStory))
                tmpStory.setBlogStatus(blogStatus);
        }

        return tmpStory;
    }
}
