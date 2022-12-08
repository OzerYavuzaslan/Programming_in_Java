package Service;

import Dao.TagDao;
import Model.Tag;

import java.util.List;

public class TagService {
    private final TagDao tagDao = new TagDao();

    public void createTag(Tag tag){
        tagDao.createTag(tag);
    }

    public List<Tag> getAllTag(){
        return tagDao.getAllTags();
    }

    public void removeTag(Tag tag){
        tagDao.removeTag(tag);
    }

    public void printAllTags(){
        tagDao.printAllTags();
    }
}
