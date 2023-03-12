package Service;

import repository.TagRepository;
import Model.Tag;

import java.util.List;

public class TagService {
    private final TagRepository tagRepository = new TagRepository();

    public void createTag(Tag tag){
        tagRepository.createTag(tag);
    }

    public List<Tag> getAllTag(){
        return tagRepository.getAllTags();
    }

    public void removeTag(Tag tag){
        tagRepository.removeTag(tag);
    }

    public void printAllTags(){
        tagRepository.printAllTags();
    }
}
