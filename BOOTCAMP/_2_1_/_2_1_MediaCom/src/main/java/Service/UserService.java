package Service;

import Dao.UserDao;
import Model.Story;
import Model.User;
import Model.Tag;

import java.util.List;
import java.util.Scanner;

public class UserService {
    private final UserDao userDao = new UserDao();

    private static final Scanner SCANNER = new Scanner(System.in);

    public void createUser(User user){
        if (user.getPassword().length() < 5)
            System.out.println("The password length must be greater than 5 characters!");
        else
            userDao.createUser(user);
    }

    private List<User> getAllUser(){
        return userDao.getAllUsers();
    }

    public void printAllUsersBlogList(){
        getAllUser().forEach(user -> user.getStoryList().
                    forEach(blog -> System.out.println("Author Name: " + blog.getAuthor() + " | Story Title: " + blog.getTitle() + " | Story Status: " + blog.getBlogStatus())));
    }

    public void getUsersBlogList(User user){
        userDao.getAllUsers().stream().
                filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())).
                forEach(user2 -> user2.getStoryList().forEach(blog -> System.out.println("Blog Title: " + blog.getTitle())));
    }

    public void followUser(User user, User followedUser){
        userDao.setFollowers(user, followedUser);
    }

    public void removeFollowing(User user, User unfollowedUser){
        userDao.removeFollow(user, unfollowedUser);
    }

    public void followTag(User user, Tag tag){
        userDao.followTag(user, tag);
    }

    public void writeADraft(Story story, User user){
        System.out.print(user.getName() + " Would you like to publish your draft? (y / n): ");
        String answer = SCANNER.nextLine();
        userDao.writeADraft(story, user, answer);
        System.out.println();
    }

    public void deleteAStory(Story story, User user){
        System.out.print(user.getName() + " Would you like to delete your story? (y / n): ");
        String answer = SCANNER.nextLine();
        userDao.deleteAStory(story, user, answer);
        System.out.println();
    }

    public void publishAStory(Story story, User user){
        System.out.print(user.getName() + " Would you like to publish your draft? (y / n): ");
        String answer = SCANNER.nextLine();
        userDao.publishAStory(story, user, answer);
    }
}
