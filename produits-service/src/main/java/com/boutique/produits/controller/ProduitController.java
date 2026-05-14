package com.boutique.produits.controller;

import com.boutique.produits.entity.Produit;
import com.boutique.produits.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits")
public class ProduitController {
    
    private final ProduitService produitService;
    
    @GetMapping
    @Operation(summary = "Récupérer tous les produits", description = "Retourne la liste de tous les produits")
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }
    
    @GetMapping("/categorie/{categorieId}")
    @Operation(summary = "Produits par catégorie", description = "Retourne les produits d'une catégorie spécifique")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(
            @Parameter(description = "ID de la catégorie") @PathVariable Long categorieId) {
        List<Produit> produits = produitService.getProduitsByCategorie(categorieId);
        return ResponseEntity.ok(produits);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Produit par ID", description = "Retourne un produit spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Produit trouvé"),
        @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    })
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit produit = produitService.getProduitById(id);
        if (produit == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(produit);
    }
    
    @PostMapping
    @Operation(summary = "Créer un produit", description = "Ajoute un nouveau produit")
    public ResponseEntity<Produit> createProduit(
            @RequestBody Produit produit,
            @RequestParam Long categorieId) {
        try {
            Produit saved = produitService.createProduit(produit, categorieId);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
