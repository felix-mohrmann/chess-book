package de.yourchessboook.service;

import de.yourchessboook.api.Variant;
import de.yourchessboook.api.Variants;
import de.yourchessboook.model.VariantEntity;
import de.yourchessboook.repo.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VariationsService {
    private final VariantRepository variantRepository;

    @Autowired
    public VariationsService(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    public VariantEntity addVariation(Variant variant) {
        StringBuilder moves = new StringBuilder();
        for (String s : variant.getMoveArray()) {
            moves.append(s);
            moves.append(" ");
        }

        VariantEntity variantEntity = new VariantEntity();
        variantEntity.setMoves(moves.toString());
        variantEntity.setName(variant.getName());
        variantEntity.setOrientation(variant.getOrientation());
        variantRepository.save(variantEntity);
        return variantEntity;
    }

    public List<VariantEntity> getAllVariations() {
        return variantRepository.findAll();
    }

    @Transactional
    public void deleteVariation(String name) {
        variantRepository.deleteByName(name);
    }
}
