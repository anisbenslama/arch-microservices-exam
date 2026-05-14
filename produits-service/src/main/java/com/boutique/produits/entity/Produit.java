package com.boutique.produits.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "produits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 200)
    private String nom;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false)
    private Double prix;
    
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;
    
    public Produit(String nom, String description, double prix, Categorie categorie) {
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.categorie = categorie;
    }
}
