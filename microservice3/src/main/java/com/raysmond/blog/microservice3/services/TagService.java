package com.raysmond.blog.microservice3.services;

import com.raysmond.blog.microservice3.models.Tag;
import com.raysmond.blog.microservice3.repositories.TagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * @author Raysmond<i @ raysmond.com>.
 */
@Service
@RequestMapping("/TagService")
public class TagService {
    private TagRepository tagRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public static final String CACHE_NAME = "cache.tag";
    public static final String CACHE_NAME_TAGS = "cache.tag.all";

    public static final String CACHE_TYPE = "'_Tag_'";
    public static final String CACHE_KEY = CACHE_TYPE + " + #tagName";
    public static final String CACHE_TAG_KEY = CACHE_TYPE + " + #tag.name";

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findOrCreateByName(String name) {
        Tag tag = tagRepository.findByName(name);
        if (tag == null) {
            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }

    @Cacheable(value = CACHE_NAME, key = CACHE_KEY)
    public Tag getTag(String tagName) {
        return tagRepository.findByName(tagName);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = CACHE_TAG_KEY),
            @CacheEvict(value = CACHE_NAME_TAGS, allEntries = true)
    })
    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    @Cacheable(value = CACHE_NAME_TAGS, key = "#root.method.name")
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        String name = "name";
        String tagName = "tagName";

        switch (method) {
            case "all":
                for (int i = 0; i < 2; i++) {
                    findOrCreateByName_test(name);
                }
                for (int i = 0; i < 1; i++) {
                    getTag_test(tagName);
                }
                break;
            case "findOrCreateByName":
                findOrCreateByName_test(name);
                break;
            case "getTag":
                getTag_test(tagName);
                break;
        }

        return "test";
    }

    public Tag findOrCreateByName_test(String name) {
//        Tag tag = tagRepository.findByName(name);
        Tag tag = new Tag(name);
        if (tag == null) {
//            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }

    public Tag getTag_test(String tagName) {
//        return tagRepository.findByName(tagName);
        return new Tag();
    }

}
