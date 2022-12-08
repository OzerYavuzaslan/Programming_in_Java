package Dao;

import Model.Tag;

import java.util.ArrayList;
import java.util.List;

public class TagDao {
    private final static List<Tag> TAG_LIST = new ArrayList<>();

    public void createTag(Tag tag){
        TAG_LIST.add(tag);
    }

    public List<Tag> getAllTags(){
        return TAG_LIST;
    }

    public void removeTag(Tag tag){
        if (!TAG_LIST.contains(tag))
            System.out.println("There is no such a tag in the tag list. Thus, " + tag.getTagName() + " can't be deleted.");
        else {
            TAG_LIST.remove(tag);
            System.out.println(tag.getTagName() + " has been removed from the tag list.");
        }
    }

    public void printAllTags(){
        getAllTags().forEach(tag -> System.out.println("Tag name: " + tag.getTagName()));
    }
}
