package com.boutique.avis.controller;

import com.boutique.avis.entity.Avis;
import com.boutique.avis.service.AvisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
@Tag(name = "Avis", description = "Gestion des avis sur les produits")
public class AvisController {
    
    private final AvisService avisService;
    
    @GetMapping("/{produitId}")
    @Operation(summary = "Avis d'un produit", description = "Retourne tous les avis pour un produit donné")
    public ResponseEntity<List<Avis>> getAvisByProduit(@PathVariable Long produitId) {
        return ResponseEntity.ok(avisService.getAvisByProduitId(produitId));
    }
    
    @PostMapping
    @Operation(summary = "Ajouter un avis", description = "Ajoute un nouvel avis pour un produit")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Avis créé avec succès"),
        @ApiResponse(responseCode = "404", description = "Produit non trouvé"),
        @ApiResponse(responseCode = "400", description = "Note invalide (1-5)")
    })
    public ResponseEntity<?> createAvis(@RequestBody Avis avis) {
        try {
            Avis saved = avisService.saveAvis(avis);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/{produitId}/moyenne")
    @Operation(summary = "Moyenne des notes", description = "Retourne la moyenne des notes d'un produit")
    public ResponseEntity<Map<String, Object>> getMoyenneNotes(@PathVariable Long produitId) {
        double moyenne = avisService.getMoyenneNotes(produitId);
        Map<String, Object> response = new HashMap<>();
        response.put("produitId", produitId);
        response.put("moyenne", moyenne);
        response.put("nombreAvis", avisService.getAvisByProduitId(produitId).size());
        return ResponseEntity.ok(response);
    }
}
