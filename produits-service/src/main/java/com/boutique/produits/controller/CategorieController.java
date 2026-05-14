package com.boutique.produits.controller;

import com.boutique.produits.entity.Categorie;
import com.boutique.produits.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Catégories", description = "Gestion des catégories de produits")
public class CategorieController {
    
    private final ProduitService produitService;
    
    @GetMapping
    @Operation(summary = "Toutes les catégories", description = "Retourne la liste de toutes les catégories")
    public ResponseEntity<List<Categorie>> getAllCategories() {
        return ResponseEntity.ok(produitService.getAllCategories());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Catégorie par ID")
    public ResponseEntity<Categorie> getCategorieById(@PathVariable Long id) {
        Categorie categorie = produitService.getCategorieById(id);
        if (categorie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categorie);
    }
    
    @PostMapping
    @Operation(summary = "Créer une catégorie")
    public ResponseEntity<Categorie> createCategorie(@RequestBody Categorie categorie) {
        try {
            Categorie saved = produitService.createCategorie(categorie);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
