package com.boutique.produits.repository;

import com.boutique.produits.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByCategorieId(Long categorieId);
    List<Produit> findByCategorieNom(String categorieNom);
    List<Produit> findByPrixBetween(Double min, Double max);
}
