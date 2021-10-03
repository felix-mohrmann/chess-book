package de.yourchessboook.controller;

import de.yourchessboook.api.Variant;
import de.yourchessboook.api.Variants;
import de.yourchessboook.model.VariantEntity;

import java.util.List;

public class VariantsControllerMapper {
    public Variants map(List<VariantEntity> variantsEntityList) {
        Variants variants = new Variants();
        for (VariantEntity variantEntity : variantsEntityList) {
            variants.add(map(variantEntity));
        }
        return variants;
    }

    public Variant map(VariantEntity variantEntity){
        return Variant.builder()
                .moveArray(variantEntity.getMoves().split(" "))
                .orientation(variantEntity.getOrientation())
                .name(variantEntity.getName()).build();
    }
}
