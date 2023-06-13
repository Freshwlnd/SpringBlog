package com.raysmond.blog.services;

import com.raysmond.blog.Constants;
import com.raysmond.blog.error.NotFoundException;
import com.raysmond.blog.models.Post;
import com.raysmond.blog.models.SeoPostData;
import com.raysmond.blog.models.Tag;
import com.raysmond.blog.models.User;
import com.raysmond.blog.models.dto.PostIdTitleDTO;
import com.raysmond.blog.models.support.PostFormat;
import com.raysmond.blog.models.support.PostStatus;
import com.raysmond.blog.models.support.PostType;
import com.raysmond.blog.notificators.Notificator;
import com.raysmond.blog.repositories.PostRepository;
import com.raysmond.blog.repositories.SeoPostDataRepository;
import com.raysmond.blog.support.web.MarkdownService;
import org.apache.lucene.util.RamUsageEstimator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author Raysmond<i @ raysmond.com>.
 */
@Service
@RequestMapping("/PostService")
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;

    @Autowired
    private MarkdownService markdownService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private SeoPostDataRepository seoPostDataRepository;

    @Autowired
    private Notificator notificator;


    public static final String CACHE_NAME = "cache.post";
    public static final String CACHE_NAME_ARCHIVE = CACHE_NAME + ".archive";
    public static final String CACHE_NAME_PAGE = CACHE_NAME + ".page";
    public static final String CACHE_NAME_TAGS = CACHE_NAME + ".tag";
    public static final String CACHE_NAME_SEO_KEYWORDS = CACHE_NAME + ".seoKeyword";
    public static final String CACHE_NAME_COUNTS = CACHE_NAME + ".counts_tags";

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Cacheable(CACHE_NAME)
    public Post getPost(Long postId) {
        logger.debug("Get post " + postId);

        Post post = postRepository.findOne(postId);

        if (post == null || post.getDeleted()) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    @Cacheable(CACHE_NAME)
    public Post getPublishedPost(Long postId) {
        logger.debug("Get published post " + postId);

        Post post = this.postRepository.findByIdAndPostStatusAndDeleted(postId, PostStatus.PUBLISHED, false);

        if (post == null) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    @Cacheable(CACHE_NAME)
    public Post getPublishedPostByPermalink(String permalink) {
        logger.debug("Get post with permalink " + permalink);

        // TODO
        Post post = postRepository.findByPermalinkAndPostStatusAndDeleted(permalink, PostStatus.PUBLISHED, false);


        if (post == null) {
            throw new NotFoundException("Post with permalink '" + permalink + "' is not found.");
        }

        return post;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public Post createPost(Post post) {
        Post result = this.savePost(post);
        return result;
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME, key = "#post.permalink", condition = "#post.permalink != null"),
            @CacheEvict(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')"),
            @CacheEvict(value = CACHE_NAME_SEO_KEYWORDS, key = "#post.id.toString().concat('-seoKeywords')"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public Post updatePost(Post post) {
        return this.savePost(post);
    }


    private Post savePost(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
            post.setRenderedContent(String.format("<div class=\"markdown-post\">%s</div>", markdownService.renderToHtml(post.getContent())));
        } else {
            post.setRenderedContent(String.format("<div class=\"html-post\">%s</div>", post.getContent()));
        }
        this.saveSeoData(post);
        // TODO
        try {
            return postRepository.save(post);
        } catch (Exception e) {
            e.printStackTrace();
            return post;
        }
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME, key = "#post.permalink", condition = "#post.permalink != null"),
            @CacheEvict(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')"),
            @CacheEvict(value = CACHE_NAME_SEO_KEYWORDS, key = "#post.id.toString().concat('-seoKeywords')"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public void deletePost(Post post) {
        post.setDeleted(true);
        postRepository.save(post);
        //postRepository.delete(post);
    }

    @Caching(evict = {
            @CacheEvict(value = CACHE_NAME, key = "#post.id"),
            @CacheEvict(value = CACHE_NAME, key = "#post.permalink", condition = "#post.permalink != null"),
            @CacheEvict(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')"),
            @CacheEvict(value = CACHE_NAME_SEO_KEYWORDS, key = "#post.id.toString().concat('-seoKeywords')"),
            @CacheEvict(value = CACHE_NAME_ARCHIVE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_PAGE, allEntries = true),
            @CacheEvict(value = CACHE_NAME_COUNTS, allEntries = true)
    })
    public void deletePost(Long postId) {
        Post post = postRepository.findOne(postId);
//        post.setDeleted(true);
//        postRepository.save(post);
        postRepository.delete(post);
    }

    @Cacheable(value = CACHE_NAME_ARCHIVE, key = "#root.method.name")
    public List<Post> getArchivePosts() {
        logger.debug("Get all archive posts from database.");

        Iterable<Post> posts = postRepository.findAllByPostTypeAndPostStatusAndDeleted(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt"),
                false);

        List<Post> cachedPosts = new ArrayList<>();
        posts.forEach(post -> cachedPosts.add(extractPostMeta(post)));

        return cachedPosts;
    }

    @Cacheable(value = CACHE_NAME_TAGS, key = "#post.id.toString().concat('-tags')")
    public List<Tag> getPostTags(Post post) {
        logger.debug("Get tags of post " + post.getId());

        List<Tag> tags = new ArrayList<>();

        // Load the post first. If not, when the post is cached before while the tags not,
        // then the LAZY loading of post tags will cause an initialization error because
        // of not hibernate connection session
        postRepository.findOne(post.getId()).getTags().forEach(tags::add);
        return tags;
    }

    @Cacheable(value = CACHE_NAME_SEO_KEYWORDS, key = "#post.id.toString().concat('-seoKeywords')")
    public String getSeoKeywordsAsString(Post post) {
        logger.debug("Get seoKeywordsAsString of post " + post.getId());

        return post.getSeoKeywords();
    }

    private Post extractPostMeta(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatedAt(post.getCreatedAt());

        archivePost.setSympathyCount(this.likeService.getTotalLikesByPost(post));
        archivePost.setVisitsCount(this.visitService.getUniqueVisitsCount(post));

        return archivePost;
    }

    @Cacheable(value = CACHE_NAME_PAGE, key = "T(java.lang.String).valueOf(#page).concat('-').concat(#pageSize)")
    public Page<Post> getAllPublishedPostsByPage(Integer page, Integer pageSize) {
        logger.debug("Get posts by page " + page);

        Page<Post> posts = postRepository.findAllByPostTypeAndPostStatusAndDeleted(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"),
                false);

        posts.forEach(p -> {
            p.setSympathyCount(this.likeService.getTotalLikesByPost(p));
            p.setVisitsCount(this.visitService.getUniqueVisitsCount(p));
        });

        return posts;
    }

    public List<Post> getAllPublishedPosts() {
        logger.debug("Get all published posts");

        return this.postRepository.findAllByPostTypeAndPostStatusAndDeleted(PostType.POST, PostStatus.PUBLISHED, false);
    }

    public List<Post> getAllPosts() {
        return this.postRepository.findAll();
    }

    public Post createAboutPage() {
        logger.debug("Create default about page");

        Post post = new Post();
        post.setTitle(Constants.ABOUT_PAGE_PERMALINK);
        post.setContent(Constants.ABOUT_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.ABOUT_PAGE_PERMALINK);
        post.setUser(userService.getSuperUser());
        post.setPostFormat(PostFormat.MARKDOWN);

        return createPost(post);
    }

    // TODO
    public Post createProjectsPage(Boolean needStore) {
        logger.debug("Create default projects page");

        Post post = new Post();
        post.setTitle(Constants.PROJECTS_PAGE_PERMALINK);
        post.setContent(Constants.PROJECTS_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.PROJECTS_PAGE_PERMALINK);
        post.setUser(userService.getSuperUser());
        post.setPostFormat(PostFormat.MARKDOWN);

        SeoPostData seoData = new SeoPostData();
        seoData.setPost(post);
        seoData.prePersist();
        if (needStore == false) {
            seoData.setId(1L);
        }

        post.setSeoData(seoData);

        return createPost(post);
    }

    public Set<Tag> parseTagNames(String tagNames) {
        Set<Tag> tags = new HashSet<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names) {
                tags.add(tagService.findOrCreateByName(name));
            }
        }

        return tags;
    }

    public String getTagNames(Set<Tag> tags) {
        if (tags == null || tags.isEmpty())
            return "";

        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }

    // cache or not?
    public Page<Post> findPostsByTag(String tagName, int page, int pageSize) {
        return postRepository.findByTag(tagName, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    @Cacheable(value = CACHE_NAME_COUNTS, key = "#root.method.name")
    public List<Object[]> countPostsByTags() {
        logger.debug("Count posts group by tags.");

        List<Object[]> list = postRepository.countPostsByTags(PostStatus.PUBLISHED);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(list));

        return list;
    }

    //TODO
    public Post findPostByPermalink(String permalink) {
        Post post = null;

        //TODO
        Boolean needStore = false;

        try {
            post = this.getPublishedPostByPermalink(permalink);
        } catch (NotFoundException ex) {
            if (permalink.matches("\\d+")) {
                if (this.userService.isCurrentUserAdmin()) {
                    post = this.getPost(Long.valueOf(permalink));

                    // TODO
                    try {
                        this.getPublishedPost(Long.valueOf(permalink));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    permalink.toLowerCase().trim().equals(Constants.PROJECTS_PAGE_PERMALINK);
                    this.createProjectsPage(needStore);

                } else {
                    post = this.getPublishedPost(Long.valueOf(permalink));

                    // TODO
                    try {
                        this.getPost(Long.valueOf(permalink));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    permalink.toLowerCase().trim().equals(Constants.PROJECTS_PAGE_PERMALINK);
                    this.createProjectsPage(needStore);

                }
            } else if (permalink.toLowerCase().trim().equals(Constants.PROJECTS_PAGE_PERMALINK)) {
                needStore = true;
                post = this.createProjectsPage(needStore);

                // TODO
                this.userService.isCurrentUserAdmin();
                try {
                    this.getPost(1L);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    this.getPublishedPost(1L);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (post == null) {
                throw new NotFoundException("Post with permalink " + permalink + " is not found");
            }
            return post;
        }

        // TODO
        this.userService.isCurrentUserAdmin();
        if (permalink.matches("\\d+")) {
            try {
                this.getPost(Long.valueOf(permalink));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.getPublishedPost(Long.valueOf(permalink));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.getPost(1L);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                this.getPublishedPost(1L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        permalink.toLowerCase().trim().equals(Constants.PROJECTS_PAGE_PERMALINK);
        this.createProjectsPage(needStore);

        if (post == null) {
            throw new NotFoundException("Post with permalink " + permalink + " is not found");
        }

        return post;
    }

    // TODO
    private void saveSeoData(Post post) {
//        if (post.getSeoData() != null && post.getSeoData().getId() == null) {
        if (post.getSeoData() != null) {
            SeoPostData data = post.getSeoData();
            try {
                this.seoPostDataRepository.save(data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//            System.out.println("SeoPostData: ");
//            System.out.println(RamUsageEstimator.sizeOf(data));

        }
    }

    public List<PostIdTitleDTO> getPostsIdTitleList() {
        List<PostIdTitleDTO> result = new ArrayList<>();
        this.getAllPosts().forEach(p -> {
            result.add(new PostIdTitleDTO(p.getId(), p.getTitle()));
        });
        Collections.sort(result, new Comparator<PostIdTitleDTO>() {
            @Override
            public int compare(PostIdTitleDTO o1, PostIdTitleDTO o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });
        return result;
    }


    public Page<Post> findAllPosts(PageRequest pageRequest) {
        return postRepository.findAllByDeleted(pageRequest, false);
    }


    // TODO
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    Post post = null;

    private void init() {
        post = getPost(1L);
    }

    @RequestMapping(value = "testCPU", method = RequestMethod.GET)
    public String testCPU(@RequestParam(name = "method", defaultValue = "all") String method) {
        Long postId = 1L;
        int page = 1;
        int pageSize = 1;
        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt");
        String permalink = "permalink";
        String tagName = "tagName";
        String tagNames = "tagNames";
        Set<Tag> tags = new HashSet<>();

        if (post == null) {
            init();
        }

        switch (method) {
            case "all":
                for (int i = 0; i < 1; i++) {
                    countPostsByTags_test();
                }
                for (int i = 0; i < 1; i++) {
                    createAboutPage_test();
                }
                for (int i = 0; i < 5; i++) {
                    createPost_test(post);
                }
                for (int i = 0; i < 3; i++) {
                    createProjectsPage_test();
                }
                for (int i = 0; i < 1; i++) {
                    deletePost_test(postId);
                }
                for (int i = 0; i < 1; i++) {
                    extractPostMeta_test(post);
                }
                for (int i = 0; i < 1; i++) {
                    findAllPosts_test(pageRequest);
                }
                for (int i = 0; i < 3; i++) {
                    findPostByPermalink_test(permalink);
                }
                for (int i = 0; i < 1; i++) {
                    findPostsByTag_test(tagName, page, pageSize);
                }
                for (int i = 0; i < 1; i++) {
                    getAllPosts_test();
                }
                for (int i = 0; i < 1; i++) {
                    getAllPublishedPosts_test();
                }
                for (int i = 0; i < 1; i++) {
                    getAllPublishedPostsByPage_test(page, pageSize);
                }
                for (int i = 0; i < 1; i++) {
                    getArchivePosts_test();
                }
                for (int i = 0; i < 7; i++) {
                    getPost_test(postId);
                }
                for (int i = 0; i < 1; i++) {
                    getPostTags_test(post);
                }
                for (int i = 0; i < 1; i++) {
                    getPostsIdTitleList_test();
                }
                for (int i = 0; i < 3; i++) {
                    getPublishedPost_test(postId);
                }
                for (int i = 0; i < 4; i++) {
                    getPublishedPostByPermalink_test(permalink);
                }
                for (int i = 0; i < 1; i++) {
                    getSeoKeywordsAsString_test(post);
                }
                for (int i = 0; i < 2; i++) {
                    getTagNames_test(tags);
                }
                for (int i = 0; i < 2; i++) {
                    parseTagNames_test(tagNames);
                }
                for (int i = 0; i < 6; i++) {
                    savePost_test(post);
                }
                for (int i = 0; i < 6; i++) {
                    saveSeoData_test(post);
                }
                for (int i = 0; i < 1; i++) {
                    updatePost_test(post);
                }
                break;
            case "countPostsByTags":
                countPostsByTags_test();
                break;
            case "createAboutPage":
                createAboutPage_test();
                break;
            case "createPost":
                createPost_test(post);
                break;
            case "createProjectsPage":
                createProjectsPage_test();
                break;
            case "deletePost":
                deletePost_test(postId);
                break;
            case "extractPostMeta":
                extractPostMeta_test(post);
                break;
            case "findAllPosts":
                findAllPosts_test(pageRequest);
                break;
            case "findPostByPermalink":
                findPostByPermalink_test(permalink);
                break;
            case "findPostsByTag":
                findPostsByTag_test(tagName, page, pageSize);
                break;
            case "getAllPosts":
                getAllPosts_test();
                break;
            case "getAllPublishedPosts":
                getAllPublishedPosts_test();
                break;
            case "getAllPublishedPostsByPage":
                getAllPublishedPostsByPage_test(page, pageSize);
                break;
            case "getArchivePosts":
                getArchivePosts_test();
                break;
            case "getPost":
                getPost_test(postId);
                break;
            case "getPostTags":
                getPostTags_test(post);
                break;
            case "getPostsIdTitleList":
                getPostsIdTitleList_test();
                break;
            case "getPublishedPost":
                getPublishedPost_test(postId);
                break;
            case "getPublishedPostByPermalink":
                getPublishedPostByPermalink_test(permalink);
                break;
            case "getSeoKeywordsAsString":
                getSeoKeywordsAsString_test(post);
                break;
            case "getTagNames":
                getTagNames_test(tags);
                break;
            case "parseTagNames":
                parseTagNames_test(tagNames);
                break;
            case "savePost":
                savePost_test(post);
                break;
            case "saveSeoData":
                saveSeoData_test(post);
                break;
            case "updatePost":
                updatePost_test(post);
                break;
        }

        return "test";
    }

    List<Object[]> countPostsByTags_test() {
        logger.debug("Count posts group by tags.");

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(new ArrayList<>()));

//        return postRepository.countPostsByTags(PostStatus.PUBLISHED);
        return new ArrayList<>();
    }

    Post createAboutPage_test() {
        logger.debug("Create default about page");

        Post post = new Post();
        post.setTitle(Constants.ABOUT_PAGE_PERMALINK);
        post.setContent(Constants.ABOUT_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.ABOUT_PAGE_PERMALINK);
        post.setUser(userService.getSuperUser());
        post.setPostFormat(PostFormat.MARKDOWN);

//        return createPost(post);
        return post;
    }

    Post createPost_test(Post post) {
//        Post result = this.savePost(post);
        Post result = post;
        return result;
    }

    Post createProjectsPage_test() {
        logger.debug("Create default projects page");

        Post post = new Post();
        post.setTitle(Constants.PROJECTS_PAGE_PERMALINK);
        post.setContent(Constants.PROJECTS_PAGE_PERMALINK.toLowerCase());
        post.setPermalink(Constants.PROJECTS_PAGE_PERMALINK);
//        post.setUser(userService.getSuperUser());
        post.setUser(new User());
        post.setPostFormat(PostFormat.MARKDOWN);

        SeoPostData seoData = new SeoPostData();
        seoData.setPost(post);
        seoData.prePersist();
        seoData.setId(1L);
        post.setSeoData(seoData);

//        return createPost(post);
        return post;
    }

    void deletePost_test(Long postId) {
//        Post post = postRepository.findOne(postId);
        Post post = new Post();
//        post.setDeleted(true);
//        postRepository.save(post);
//        postRepository.delete(post);
    }

    Post extractPostMeta_test(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatedAt(post.getCreatedAt());

//        archivePost.setSympathyCount(this.likeService.getTotalLikesByPost(post));
        archivePost.setSympathyCount(1);
//        archivePost.setVisitsCount(this.visitService.getUniqueVisitsCount(post));
        archivePost.setVisitsCount(1L);

        return archivePost;
    }

    Page<Post> findAllPosts_test(PageRequest pageRequest) {

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(false));

//        return postRepository.findAllByDeleted(pageRequest, false);
        return new PageImpl<>(new ArrayList<>());
    }

    Post findPostByPermalink_test(String permalink) {
        Post post = null;

        //TODO
        Boolean needStore = false;

        permalink.matches("\\d+");
        permalink.toLowerCase().trim().equals(Constants.PROJECTS_PAGE_PERMALINK);

        if (post == null) {
//            throw new NotFoundException("Post with permalink " + permalink + " is not found");
        }
        return post;

    }

    // cache or not?
    Page<Post> findPostsByTag_test(String tagName, int page, int pageSize) {
        new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt");
        return new PageImpl<>(new ArrayList<>());
//        return postRepository.findByTag(tagName, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    List<Post> getAllPosts_test() {
//        return this.postRepository.findAll();
        return new ArrayList<>();
    }

    List<Post> getAllPublishedPosts_test() {
        logger.debug("Get all published posts");


        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(PostType.POST));
//        System.out.println(RamUsageEstimator.sizeOf(PostStatus.PUBLISHED));


//        return this.postRepository.findAllByPostTypeAndPostStatusAndDeleted(PostType.POST, PostStatus.PUBLISHED, false);
        return new ArrayList<>();
    }

    Page<Post> getAllPublishedPostsByPage_test(Integer page, Integer pageSize) {
        logger.debug("Get posts by page " + page);

        // TODO：增加数据大小获取功能（https://www.cnblogs.com/huaweiyun/p/16416147.html）
//        System.out.println(RamUsageEstimator.sizeOf(page));

        Page<Post> posts = new PageImpl<>(new ArrayList<>());
        new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt");
//        Page<Post> posts = postRepository.findAllByPostTypeAndPostStatusAndDeleted(
//                PostType.POST,
//                PostStatus.PUBLISHED,
//                new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"),
//                false);

        posts.forEach(p -> {
//            p.setSympathyCount(this.likeService.getTotalLikesByPost(p));
            p.setSympathyCount(1);
//            p.setVisitsCount(this.visitService.getUniqueVisitsCount(p));
            p.setVisitsCount(1L);
        });

        return posts;
    }

    List<Post> getArchivePosts_test() {
        logger.debug("Get all archive posts from database.");

        Iterable<Post> posts = new PageImpl<>(new ArrayList<>());
        new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt");
//        Iterable<Post> posts = postRepository.findAllByPostTypeAndPostStatusAndDeleted(
//                PostType.POST,
//                PostStatus.PUBLISHED,
//                new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt"),
//                false);

        List<Post> cachedPosts = new ArrayList<>();
//        posts.forEach(post -> cachedPosts.add(extractPostMeta(post)));
        posts.forEach(post -> cachedPosts.add(post));

        return cachedPosts;
    }

    Post getPost_test(Long postId) {
        logger.debug("Get post " + postId);

//        Post post = postRepository.findOne(postId);
        Post post = new Post();

        if (post == null || post.getDeleted()) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    List<Tag> getPostTags_test(Post post) {
        logger.debug("Get tags of post " + post.getId());

        List<Tag> tags = new ArrayList<>();

        // Load the post first. If not, when the post is cached before while the tags not,
        // then the LAZY loading of post tags will cause an initialization error because
        // of not hibernate connection session
//        postRepository.findOne(post.getId()).getTags().forEach(tags::add);
        return tags;
    }

    List<PostIdTitleDTO> getPostsIdTitleList_test() {
        List<PostIdTitleDTO> result = new ArrayList<>();
        List<Post> posts = new ArrayList<>();
//        this.getAllPosts().forEach(p -> {
        posts.forEach(p -> {
            result.add(new PostIdTitleDTO(p.getId(), p.getTitle()));
        });
        Collections.sort(result, new Comparator<PostIdTitleDTO>() {
            @Override
            public int compare(PostIdTitleDTO o1, PostIdTitleDTO o2) {
                return Long.compare(o2.getId(), o1.getId());
            }
        });
        return result;
    }

    Post getPublishedPost_test(Long postId) {
        logger.debug("Get published post " + postId);

//        Post post = this.postRepository.findByIdAndPostStatusAndDeleted(postId, PostStatus.PUBLISHED, false);
        Post post = new Post();

        if (post == null) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }

    Post getPublishedPostByPermalink_test(String permalink) {
        logger.debug("Get post with permalink " + permalink);

        // TODO
//        Post post = postRepository.findByPermalinkAndPostStatusAndDeleted(permalink, PostStatus.PUBLISHED, false);
        Post post = new Post();

        if (post == null) {
            throw new NotFoundException("Post with permalink '" + permalink + "' is not found.");
        }

        return post;
    }

    String getSeoKeywordsAsString_test(Post post) {
        logger.debug("Get seoKeywordsAsString of post " + post.getId());

        return post.getSeoKeywords();
    }

    String getTagNames_test(Set<Tag> tags) {
        if (tags == null || tags.isEmpty())
            return "";

        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }

    Set<Tag> parseTagNames_test(String tagNames) {
        Set<Tag> tags = new HashSet<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names) {
//                tags.add(tagService.findOrCreateByName(name));
                tags.add(new Tag(name));
            }
        }

        return tags;
    }

    Post savePost_test(Post post) {
        if (post.getPostFormat() == PostFormat.MARKDOWN) {
//            post.setRenderedContent(String.format("<div class=\"markdown-post\">%s</div>", markdownService.renderToHtml(post.getContent())));
            post.setRenderedContent(String.format("<div class=\"markdown-post\">%s</div>", post.getContent()));
        } else {
            post.setRenderedContent(String.format("<div class=\"html-post\">%s</div>", post.getContent()));
        }
//        this.saveSeoData(post);
//        return postRepository.save(post);
        return post;
    }

    void saveSeoData_test(Post post) {
        if (post.getSeoData() != null) {
            SeoPostData data = post.getSeoData();
//            this.seoPostDataRepository.save(data);
        }
    }

    Post updatePost_test(Post post) {
//        return this.savePost(post);
        return new Post();
    }

}
