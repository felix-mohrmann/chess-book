package com.yourchessboook.controller;

import java.util.List;

import com.yourchessboook.api.Variant;
import com.yourchessboook.api.Variants;
import com.yourchessboook.model.VariantEntity;

public class VariantsControllerMapper {
    public Variants map(List<VariantEntity> variantsEntityList) {
        Variants variants = new Variants();
        for (VariantEntity variantEntity : variantsEntityList) {
            variants.add(map(variantEntity));
        }
        return variants;
    }

    public Variant map(VariantEntity variantEntity) {
        return Variant.builder()
                .moveArray(variantEntity.getMoves().split(" "))
                .orientation(variantEntity.getOrientation())
                .name(variantEntity.getName()).build();
    }
}
