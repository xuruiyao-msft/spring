package com.springinaction.knights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BraveKnight implements Knight {

    @Autowired
    private Quest quest;

    public BraveKnight(){}

    public BraveKnight(Quest quest) {
        this.quest = quest;
    }

    public void embarkOnQuest() {
        quest.embark();
    }
}
