package com.medium.controller;

import Model.Tag;
import Service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping
    public Tag create(Tag tag){
        tagService.createTag(tag);
        return tag;
    }

    @DeleteMapping
    public void delete(Tag tag){
        tagService.removeTag(tag);
    }

    @GetMapping
    public List<Tag> getAll(){
        return tagService.getAllTag();
    }
}
