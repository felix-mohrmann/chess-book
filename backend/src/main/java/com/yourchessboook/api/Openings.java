package com.yourchessboook.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class Openings {
    private List<Opening> openings = new LinkedList<>();

    public void addOpening(Opening opening) {
        this.openings.add(opening);
    }
}
