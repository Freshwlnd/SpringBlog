package com.raysmond.blog.common.forms;

import com.raysmond.blog.common.models.Post;
import com.raysmond.blog.common.models.SeoPostData;
import com.raysmond.blog.common.models.support.OgLocale;
import com.raysmond.blog.common.models.support.OgType;
import com.raysmond.blog.common.models.support.PostFormat;
import com.raysmond.blog.common.models.support.PostStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author Raysmond<i@raysmond.com>
 */
@Data
public class PostForm {

    @NotNull
    private Boolean deleted;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;


    private String announcement;

    @NotNull
    private PostFormat postFormat;

    @NotNull
    private PostStatus postStatus;

    @NotNull
    private String permalink;

    @NotNull
    private String postTags;

    @NotNull
    private String seoKeywords;

    @NotNull
    private String seoDescription;


//    @NotNull
//    private String seoOgTitle;

    @NotNull
    private OgType seoOgType;

    @NotNull
    private String seoOgImage;

    @NotNull
    private String seoOgVideo;

    @NotNull
    private OgLocale seoOgLocale;

    public void init() {
        this.setTitle("title");
        this.setPermalink("permalink");
        this.setContent("content");
        this.setPostTags("tag");
        this.setPostStatus(PostStatus.DRAFT);
        this.setPostFormat(PostFormat.MARKDOWN);
        this.setSeoKeywords("seoKeywords");
        this.setSeoOgImage("seoOgImage");
        this.setSeoOgLocale(OgLocale.en_EN);
        //this.setSeoOgTitle("");
        this.setSeoOgType(OgType.ARTICLE);
        this.setSeoOgVideo("seoOgVideo");
    }

    public void initFromPost(Post post) {
        if (post.getSeoData() != null) {
            this.setSeoOgImage(post.getSeoData().getOgImage());
            this.setSeoOgVideo(post.getSeoData().getOgVideo());
            this.setSeoOgLocale(post.getSeoData().getOgLocale());
            this.setSeoOgType(post.getSeoData().getOgType());
        } else {
            this.setSeoOgImage("");
            this.setSeoOgLocale(OgLocale.en_EN);
            //this.setSeoOgTitle("");
            this.setSeoOgType(OgType.ARTICLE);
            this.setSeoOgVideo("");
        }
    }

    public void initFromPost(Post post, String postTags) {
        this.initFromPost(post);
        this.setPostTags(postTags);
    }

    public void fillOgFieldsInPost(Post post) {
        SeoPostData data = null;
        if (post.getSeoData() == null) {
            data = new SeoPostData();
        } else {
            data = post.getSeoData();
        }
        data.setOgImage(this.seoOgImage);
        data.setOgLocale(this.seoOgLocale);
        data.setOgTitle(this.title);
        data.setOgType(this.seoOgType);
        data.setOgVideo(this.seoOgVideo);
        post.setSeoData(data);
    }

}
