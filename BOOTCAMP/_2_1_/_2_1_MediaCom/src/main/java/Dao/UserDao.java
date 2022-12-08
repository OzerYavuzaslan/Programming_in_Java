package Dao;

import Model.Story;
import Model.Tag;
import Model.User;
import Model.BlogStatus;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final List<User> USER_LIST = new ArrayList<>();
    private final TagDao tagDao = new TagDao();
    public void createUser(User user){
        USER_LIST.add(user);
    }

    public List<User> getAllUsers(){
        return USER_LIST;
    }

    public void setFollowers(User user, User followedUser){
        if(USER_LIST.contains(user) && USER_LIST.contains(followedUser)) {
            user.getFollowingUsersList().add(followedUser);
            followedUser.getFollowedUsersList().add(user);
        }
        else {
            if (!USER_LIST.contains(user))
                System.out.println("There is no such a user in the Database. " + user.getName());

            if (!USER_LIST.contains(followedUser))
                System.out.println("There is no such a user to follow in Database. " + followedUser.getName());
        }
    }

    public void removeFollow(User user, User unfollowedUser){
        if(USER_LIST.contains(user) && USER_LIST.contains(unfollowedUser)) {
            user.getFollowedUsersList().remove(unfollowedUser);
            unfollowedUser.getFollowingUsersList().remove(user);
            System.out.println(user.getName() + " has stopped following " + unfollowedUser.getName());
        }
        else {
            if (!USER_LIST.contains(user))
                System.out.println("There is no such a user in the Database to remove. " + user.getName());

            if (!USER_LIST.contains(unfollowedUser))
                System.out.println("There is no such a user to follow in Database to remove. " + unfollowedUser.getName());
        }
    }

    public void followTag(User user, Tag tag){
        List<Tag> tagList = tagDao.getAllTags();

        if (!tagList.contains(tag))
            System.out.println("There is no such a tag! Thus you can't follow this tag (" + tag.getTagName() + ").");
        else {
            user.getFollowingTagList().add(tag);
            System.out.println(user.getName() + " has been following " + tag.getTagName());
        }
    }

    public void writeADraft(Story story, User user, String answer){
        user.getStoryList().add(story);

        if (answer.equalsIgnoreCase("y")) {
            story.setBlogStatus(BlogStatus.PUBLISHED);
            System.out.println("The story that you've written is published now." + story.getTitle());
        }
    }

    public void deleteAStory(Story story, User user, String answer){
        if (answer.equalsIgnoreCase("y")) {
            if (user.getStoryList().contains(story)) {
                user.getStoryList().remove(story);
                System.out.println("Your story has been deleted. " + story.getTitle());
            }
            else
                System.out.println(story.getTitle() + " not found.");
        }
    }

    public void publishAStory(Story story, User user, String answer){
        if (answer.equalsIgnoreCase("y")){
            if (user.getStoryList().contains(story)) {
                if (story.getBlogStatus() != BlogStatus.PUBLISHED) {
                    story.setBlogStatus(BlogStatus.PUBLISHED);
                    System.out.println("Your story has been published." + story.getTitle());
                }
                else
                    System.out.println(story.getTitle() + " is already published!");
            }
            else
                System.out.println(story.getTitle() + " not found.");
        }
    }
}
