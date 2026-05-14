package com.boutique.produits;

import com.boutique.produits.entity.Categorie;
import com.boutique.produits.entity.Produit;
import com.boutique.produits.repository.CategorieRepository;
import com.boutique.produits.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {
    
    @Mock
    private ProduitRepository produitRepository;
    
    @Mock
    private CategorieRepository categorieRepository;
    
    @InjectMocks
    private ProduitService produitService;
    
    private Categorie categorie;
    private Produit produit;
    
    @BeforeEach
    void setUp() {
        categorie = new Categorie("Test", "Description test");
        categorie.setId(1L);
        
        produit = new Produit("Test Produit", "Description", 99.99, categorie);
        produit.setId(1L);
    }
    
    @Test
    void testGetProduitById() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        
        Produit found = produitService.getProduitById(1L);
        
        assertThat(found).isNotNull();
        assertThat(found.getNom()).isEqualTo("Test Produit");
        verify(produitRepository, times(1)).findById(1L);
    }
    
    @Test
    void testGetProduitsByCategorie() {
        when(produitRepository.findByCategorieId(1L)).thenReturn(Arrays.asList(produit));
        
        List<Produit> produits = produitService.getProduitsByCategorie(1L);
        
        assertThat(produits).hasSize(1);
        assertThat(produits.get(0).getNom()).isEqualTo("Test Produit");
    }
    
    @Test
    void testCreateProduit() {
        when(categorieRepository.findById(1L)).thenReturn(Optional.of(categorie));
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        
        Produit created = produitService.createProduit(produit, 1L);
        
        assertThat(created).isNotNull();
        assertThat(created.getCategorie().getNom()).isEqualTo("Test");
    }
}
