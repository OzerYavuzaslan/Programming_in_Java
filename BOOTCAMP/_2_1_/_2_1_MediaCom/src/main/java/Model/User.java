package Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private List<User> followedUsersList;
    private List<User> followingUsersList;
    private List<Story> storyList;
    private List<Tag> followingTagList;

    public User(){
    }

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setFollowingTagList(new ArrayList<>());
        setStoryList(new ArrayList<>());
        setFollowedUsersList(new ArrayList<>());
        setFollowingUsersList(new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getFollowedUsersList() {
        return followedUsersList;
    }

    public void setFollowedUsersList(List<User> followedUsersList) {
        this.followedUsersList = followedUsersList;
    }

    public List<User> getFollowingUsersList() {
        return followingUsersList;
    }

    public void setFollowingUsersList(List<User> followingUsersList) {
        this.followingUsersList = followingUsersList;
    }

    public List<Story> getStoryList() {
        return storyList;
    }

    public void setStoryList(List<Story> storyList) {
        this.storyList = storyList;
    }

    public List<Tag> getFollowingTagList() {
        return followingTagList;
    }

    public void setFollowingTagList(List<Tag> followingTagList) {
        this.followingTagList = followingTagList;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                '}';
    }
}
