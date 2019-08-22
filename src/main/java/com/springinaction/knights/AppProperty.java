package com.springinaction.knights;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

@Data
@Component
@Conditional(ConditionalPracticeConditionImpl.class)
public class AppProperty {

    private String title;
    private String artist;
    private Integer abs;

    public AppProperty(@Value("${disc.title}") String title, @Value("${disc.artist}") String aritist, @Value("#{T(java.lang.Math).abs(-1)}") Integer abs) {
        this.title = title;
        this.artist = aritist;
        this.abs = abs;
    }
}
