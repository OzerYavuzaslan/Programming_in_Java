package com.medium;

import Model.Story;
import Model.User;
import Model.Tag;
import Service.StoryService;
import Service.TagService;
import Service.UserService;

public class Main {
    public static void main(String[] args) {
        StoryService storyService = new StoryService();
        TagService tagService = new TagService();
        UserService userService = new UserService();

        User ozer = new User("Özer", "ozeryavuzaslan@yahoo.com", "123123");
        User sami = new User("Sami", "samisezgin@yahoo.com", "321321");
        Story story1 = new Story("Title1", "There is no limit to improve yourself", ozer);
        Story story2 = new Story("Title2", "If you like to be successful, you must work hard!!!!!!!", sami);

        storyService.createStory(story1);
        storyService.createStory(story2);

        Tag tag = new Tag("Self Improvement");

        userService.createUser(ozer);
        userService.createUser(sami);

        story1.setTagList(tag);
        story2.setTagList(tag);

        tagService.createTag(tag);
        tagService.printAllTags();

        userService.followUser(ozer, sami);
        userService.followUser(sami, ozer);

        userService.followTag(ozer, tag);
        userService.followTag(sami, tag);

        userService.writeADraft(story1, ozer);
        userService.writeADraft(story2, sami);

        userService.printAllUsersBlogList();

        userService.removeFollowing(sami, ozer);

        userService.publishAStory(story1, ozer);

        userService.deleteAStory(story2, sami);

        userService.printAllUsersBlogList();

        Story story3 = new Story("Title3", "Elif bootcampin en katılımcı asistanı.", ozer);
        storyService.createStory(story3);
        ozer.getStoryList().add(story3);

        userService.getUsersBlogList(ozer);
    }
}