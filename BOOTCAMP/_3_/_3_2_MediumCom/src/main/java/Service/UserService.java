package Service;

import repository.UserRepository;
import Model.Story;
import Model.User;
import Model.Tag;

import java.util.List;
import java.util.Scanner;

public class UserService {
    private final UserRepository userRepository = new UserRepository();

    private static final Scanner SCANNER = new Scanner(System.in);

    public void createUser(User user){
        if (user.getPassword().length() < 5)
            System.out.println("The password length must be greater than 5 characters!");
        else
            userRepository.createUser(user);
    }

    public List<User> getAllUser(){
        return userRepository.getAllUsers();
    }

    public void printAllUsersBlogList(){
        getAllUser().forEach(user -> user.getStoryList().
                    forEach(blog -> System.out.println("Author Name: " + blog.getAuthor() + " | Story Title: " + blog.getTitle() + " | Story Status: " + blog.getBlogStatus())));
    }

    public void getUsersBlogList(User user){
        userRepository.getAllUsers().stream().
                filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())).
                forEach(user2 -> user2.getStoryList().forEach(blog -> System.out.println("Blog Title: " + blog.getTitle())));
    }

    public void followUser(User user, User followedUser){
        userRepository.setFollowers(user, followedUser);
    }

    public void removeFollowing(User user, User unfollowedUser){
        userRepository.removeFollow(user, unfollowedUser);
    }

    public void followTag(User user, Tag tag){
        userRepository.followTag(user, tag);
    }

    public void writeADraft(Story story, User user){
        System.out.print(user.getName() + " Would you like to publish your draft? (y / n): ");
        String answer = SCANNER.nextLine();
        userRepository.writeADraft(story, user, answer);
        System.out.println();
    }

    public void deleteAStory(Story story, User user){
        System.out.print(user.getName() + " Would you like to delete your story? (y / n): ");
        String answer = SCANNER.nextLine();
        userRepository.deleteAStory(story, user, answer);
        System.out.println();
    }

    public void publishAStory(Story story, User user){
        System.out.print(user.getName() + " Would you like to publish your draft? (y / n): ");
        String answer = SCANNER.nextLine();
        userRepository.publishAStory(story, user, answer);
    }
}
