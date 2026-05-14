package com.boutique.avis.service;

import com.boutique.avis.client.ProduitFeignClient;
import com.boutique.avis.entity.Avis;
import com.boutique.avis.repository.AvisRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AvisService {
    
    private final AvisRepository avisRepository;
    private final ProduitFeignClient produitFeignClient;
    
    public List<Avis> getAvisByProduitId(Long produitId) {
        log.info("Fetching avis for produit id: {}", produitId);
        return avisRepository.findByProduitIdOrderByDateCreationDesc(produitId);
    }
    
    public Avis saveAvis(Avis avis) {
        // Vérifier que le produit existe via Feign
        try {
            var produit = produitFeignClient.getProduitById(avis.getProduitId());
            log.info("Produit found: {}", produit.nom());
        } catch (FeignException.NotFound e) {
            log.error("Produit not found: {}", avis.getProduitId());
            throw new RuntimeException("Produit non trouvé avec l'id: " + avis.getProduitId());
        } catch (FeignException e) {
            log.error("Error calling produits-service: {}", e.getMessage());
            throw new RuntimeException("Service indisponible, veuillez réessayer plus tard");
        }
        
        // Valider la note
        if (avis.getNote() < 1 || avis.getNote() > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 1 et 5");
        }
        
        return avisRepository.save(avis);
    }
    
    public double getMoyenneNotes(Long produitId) {
        List<Avis> avis = getAvisByProduitId(produitId);
        return avis.stream()
            .mapToInt(Avis::getNote)
            .average()
            .orElse(0.0);
    }
}
