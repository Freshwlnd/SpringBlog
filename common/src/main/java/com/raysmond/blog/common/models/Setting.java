package com.raysmond.blog.common.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A generic setting model
 *
 * @author Raysmond<i@raysmond.com>
 */
@Entity
@Table(name = "settings")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Setting extends BaseModel{

    @Column(name = "_key", unique = true, nullable = false)
    private String key;

    @Lob
    @Column(name = "_value")
    @JsonDeserialize(contentUsing = StringDeserializer.class, contentAs = String.class)
//    private Serializable value;
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

}
