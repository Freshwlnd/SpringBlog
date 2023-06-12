package com.raysmond.blog.microservice2.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A generic setting model
 *
 * @author Raysmond<i @ raysmond.com>
 */
@Entity
@Table(name = "settings")
@Getter
@Setter
public class Setting extends BaseModel {

    @Column(name = "_key", unique = true, nullable = false)
    private String key = "key";

    @Lob
    @Column(name = "_value")
    private Serializable value = "value";

}
