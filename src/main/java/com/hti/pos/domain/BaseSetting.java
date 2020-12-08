package com.hti.pos.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by ravuthz
 * Date : 12/4/2020, 10:59 PM
 * Email : ravuthz@gmail.com,
 * yovannaravuth@gmail.com
 */

@Setter
@Getter
@MappedSuperclass
public class BaseSetting extends BaseEntity {

    @NotEmpty
    @Column
    protected String name;

    @NotEmpty
    @Column
    protected String slug;

    @Column
    protected String thumbnail;

    @Column
    protected String description;

    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public String makeSlug(String input) {
        if (input == null) {
            throw new IllegalArgumentException("makeSlug.input is required");
        }
        String noWhiteSpace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public void init(String name, String thumbnail, String description) {
        this.name = name;
        this.slug = makeSlug(name);
        this.thumbnail = thumbnail;
        this.description = description;
    }
}
