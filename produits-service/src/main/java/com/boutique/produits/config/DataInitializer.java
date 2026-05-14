package com.boutique.produits.config;

import com.boutique.produits.entity.Categorie;
import com.boutique.produits.entity.Produit;
import com.boutique.produits.service.ProduitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {
    
    private final ProduitService produitService;
    
    @Override
    public void run(String... args) {
        // Créer des catégories
        if (produitService.getAllCategories().isEmpty()) {
            log.info("Initialisation des données de démonstration...");
            
            Categorie electronique = produitService.createCategorie(
                new Categorie("Électronique", "Produits électroniques et gadgets"));
            Categorie vetements = produitService.createCategorie(
                new Categorie("Vêtements", "Mode et accessoires"));
            Categorie livres = produitService.createCategorie(
                new Categorie("Livres", "Livres et magazines"));
            
            // Créer des produits
            produitService.createProduit(
                new Produit("iPhone 15", "Dernier smartphone Apple", 1099.99, electronique),
                electronique.getId());
            produitService.createProduit(
                new Produit("MacBook Pro", "Ordinateur portable puissant", 1999.10, electronique),
                electronique.getId());
            produitService.createProduit(
                new Produit("T-Shirt Blanc", "T-shirt 100% coton", 19.10, vetements),
                vetements.getId());
            produitService.createProduit(
                new Produit("Spring Boot in Action", "Livre sur Spring Boot", 49.00, livres),
                livres.getId());
            
            log.info("Données initialisées avec succès!");
        }
    }
}
