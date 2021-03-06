package de.yourchessboook.controller;

import de.yourchessboook.api.Variant;
import de.yourchessboook.api.Variants;
import de.yourchessboook.model.VariantEntity;
import de.yourchessboook.service.VariationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/variations")
public class VariationsController extends VariantsControllerMapper{
    private final VariationsService variationsService;

    @Autowired
    public VariationsController(VariationsService variationsService) {
        this.variationsService = variationsService;
    }

    @PostMapping("/add")
    public ResponseEntity<Variant> addVariation(@RequestBody Variant moves){
        VariantEntity variantEntity = variationsService.addVariation(moves);
        return ok(map(variantEntity));
    }

    @GetMapping("/all")
    public ResponseEntity<Variants> getAllVariations(){
        List<VariantEntity> variants = variationsService.getAllVariations();
        return ok(map(variants));
    }

    @DeleteMapping("/delete/{name}")
    public void deleteVariation(@PathVariable String name){
        variationsService.deleteVariation(name);
    }
}
