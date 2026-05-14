package com.boutique.avis.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avis", indexes = {
    @Index(name = "idx_produit_id", columnList = "produitId")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Avis {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long produitId;
    
    @Column(nullable = false, length = 100)
    private String auteur;
    
    @Column(length = 1000)
    private String commentaire;
    
    @Column(nullable = false)
    private Integer note; // 1 à 5
    
    private LocalDateTime dateCreation;
    
    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
