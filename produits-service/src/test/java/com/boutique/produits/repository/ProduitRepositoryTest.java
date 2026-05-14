package com.boutique.produits.repository;

import com.boutique.produits.entity.Categorie;
import com.boutique.produits.entity.Produit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class ProduitRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Test
    void testFindByCategorieId() {
        Categorie categorie = new Categorie("Test Cat", "Description");
        entityManager.persist(categorie);
        
        Produit produit1 = new Produit("Produit 1", "Desc 1", 10.0, categorie);
        Produit produit2 = new Produit("Produit 2", "Desc 2", 20.0, categorie);
        entityManager.persist(produit1);
        entityManager.persist(produit2);
        entityManager.flush();
        
        List<Produit> produits = produitRepository.findByCategorieId(categorie.getId());
        
        assertThat(produits).hasSize(2);
        assertThat(produits).extracting(Produit::getNom).containsExactlyInAnyOrder("Produit 1", "Produit 2");
    }
}
