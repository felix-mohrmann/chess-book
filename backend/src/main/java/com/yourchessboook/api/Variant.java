package de.yourchessboook.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Variant {
    private String name;
    private String[] moveArray;
    private String orientation;
}
