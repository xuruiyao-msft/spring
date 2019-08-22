package com.springinaction.knights;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Component
public class Minstrel {

    @Value("#{T(System).out}")
    private PrintStream stream;

    public void singBeforeQuest() {
        stream.println("tan xian zhi qian diao yong");
    }

    public void singAfterQuest() {
        stream.println("tan xian zhi hou diao yong");
    }
}
