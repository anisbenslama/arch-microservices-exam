package com.boutique.produits.service;

import com.boutique.produits.entity.Categorie;
import com.boutique.produits.entity.Produit;
import com.boutique.produits.repository.CategorieRepository;
import com.boutique.produits.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProduitService {
    
    private final ProduitRepository produitRepository;
    private final CategorieRepository categorieRepository;
    
    // Categories
    @Cacheable(value = "categories")
    public List<Categorie> getAllCategories() {
        log.info("Fetching all categories from database");
        return categorieRepository.findAll();
    }
    
    @Cacheable(value = "category", key = "#id")
    public Categorie getCategorieById(Long id) {
        return categorieRepository.findById(id).orElse(null);
    }
    
    @Transactional
    @CacheEvict(value = "categories", allEntries = true)
    public Categorie createCategorie(Categorie categorie) {
        if (categorieRepository.existsByNom(categorie.getNom())) {
            throw new RuntimeException("Category already exists: " + categorie.getNom());
        }
        return categorieRepository.save(categorie);
    }
    
    // Produits
    @Cacheable(value = "produitsByCategory", key = "#categorieId")
    public List<Produit> getProduitsByCategorie(Long categorieId) {
        log.info("Fetching produits for category id: {}", categorieId);
        return produitRepository.findByCategorieId(categorieId);
    }
    
    @Cacheable(value = "produit", key = "#id")
    public Produit getProduitById(Long id) {
        log.info("Fetching produit by id: {}", id);
        return produitRepository.findById(id).orElse(null);
    }
    
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
    
    @Transactional
    @CacheEvict(value = {"produitsByCategory", "produit"}, allEntries = true)
    public Produit createProduit(Produit produit, Long categorieId) {
        Categorie categorie = getCategorieById(categorieId);
        if (categorie == null) {
            throw new RuntimeException("Category not found: " + categorieId);
        }
        produit.setCategorie(categorie);
        return produitRepository.save(produit);
    }
    
    @Transactional
    @CacheEvict(value = {"produit", "produitsByCategory"}, allEntries = true)
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
}
