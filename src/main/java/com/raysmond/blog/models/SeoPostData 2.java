package com.raysmond.blog.models;

import com.raysmond.blog.models.support.OgLocale;
import com.raysmond.blog.models.support.OgType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "seo_posts_data")
@Getter
@Setter
public class SeoPostData extends BaseModel {

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "seoData", cascade = CascadeType.ALL)
    private Post post;

    @Column
    private String ogTitle = "";

    @Column
    @Enumerated(EnumType.STRING)
    private OgType ogType = OgType.ARTICLE;

    @Column
    private String ogImage = "";

    @Column
    private String ogVideo = "";

    @Column
    @Enumerated(EnumType.STRING)
    private OgLocale ogLocale = OgLocale.en_EN;

    /*@Column
    private String ogUrl;

    @Column
    private String ogDescription;*/

}
