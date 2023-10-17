package com.yourchessboook.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@ToString
public class Variants {
    private List<Variant> variants = new LinkedList<>();

    public void add(Variant variant) {
        this.variants.add(variant);
    }
}
