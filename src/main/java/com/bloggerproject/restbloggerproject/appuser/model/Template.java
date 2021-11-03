package com.bloggerproject.restbloggerproject.appuser.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Template {
    @SequenceGenerator(
            name = "template_sequence",
            sequenceName = "template_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "template_sequence"
    )
    private Long templateId;
    private String category;
    private String colorTheme;

    public Template() {
    }

    public Template(String category, String colorTheme) {
        this.category = category;
        this.colorTheme = colorTheme;
    }

}
