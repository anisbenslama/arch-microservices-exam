package com.boutique.avis.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produits-service")
public interface ProduitFeignClient {
    
    @GetMapping("/api/produits/{id}")
    ProduitDTO getProduitById(@PathVariable("id") Long id);
    
    record ProduitDTO(Long id, String nom, String description, Double prix) {}
}
