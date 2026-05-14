# 🛍️ Projet Boutique - Architecture Microservices

## 📋 Table des matières

1. [Description du projet](#description-du-projet)
2. [Architecture](#architecture)
3. [Prérequis](#prérequis)
4. [Installation et démarrage](#installation-et-démarrage)
5. [Configuration détaillée](#configuration-détaillée)
6. [API Endpoints](#api-endpoints)
7. [Tests](#tests)
8. [Application Mobile](#application-mobile)
9. [Dépannage](#dépannage)
10. [Structure du projet](#structure-du-projet)

---

## 📖 Description du projet

Ce projet est une application e-commerce complète basée sur une **architecture microservices**. Elle permet de gérer des catégories de produits, des produits et des avis clients.

### Fonctionnalités

- ✅ Gestion des catégories (CRUD)
- ✅ Gestion des produits (CRUD)
- ✅ Gestion des avis clients (notes de 1 à 5)
- ✅ Cache Redis pour optimiser les performances
- ✅ Service Discovery avec Eureka
- ✅ API Gateway pour le routage centralisé
- ✅ Communication inter-services avec Feign Client
- ✅ Documentation Swagger/OpenAPI
- ✅ Application mobile Flutter
- ✅ Containerisation avec Docker
- ✅ Tests unitaires, intégration et E2E

---

## 🏗️ Architecture


### Composants

| Service | Port | Description |
|---------|------|-------------|
| **Eureka Server** | 8761 | Service Discovery - Enregistrement et découverte des services |
| **API Gateway** | 8090 | Point d'entrée unique, routage et load balancing |
| **Produits-service** | 8091 | Gestion des catégories et produits + cache Redis |
| **Avis-service** | 8092 | Gestion des avis clients avec Feign client |
| **PostgreSQL** | 5432 | Base de données relationnelle |
| **Redis** | 6379 | Cache pour les données fréquemment accédées |

---

## 🔧 Prérequis

### Obligatoire
- **Java JDK 25** ou supérieur
- **Maven 3.9+**
- **Docker** et **Docker Compose** (version 2.0+)
- **Git**

### Pour l'application mobile
- **Flutter 3.16+** (avec Dart 3.2+)
- **Android Studio** / **Xcode** (pour les émulateurs)
- Un émulateur Android/iOS ou un appareil physique

### Pour les tests E2E
- **Node.js 18+**
- **npm** ou **yarn**

---

## 🚀 Installation et démarrage

### 1. Cloner le projet

```bash
git clone <votre-repo-url>
cd projet-boutique

# Vérifier les branches
git branch -a
# version1 : Backend uniquement
# version2 : Backend + Mobile + Tests

# Construire Eureka Server
cd eureka-server
mvn clean package -DskipTests
cd ..

# Construire Produits-service
cd produits-service
mvn clean package -DskipTests
cd ..

# Construire Avis-service
cd avis-service
mvn clean package -DskipTests
cd ..

# Construire API Gateway
cd api-gateway
mvn clean package -DskipTests
cd ..

4. Vérifier le bon fonctionnement

Service	URL	Status attendu
Eureka Dashboard	http://localhost:8761	  Interface Eureka avec services enregistrés
API Gateway	http://localhost:8090	        Page d'accueil du gateway
Swagger Produits	http://localhost:8091/swagger-ui.html	  Documentation API produits
Swagger Avis	http://localhost:8092/swagger-ui.html	      Documentation API avis

📡 API Endpoints

Via API Gateway (http://localhost:8090)
Catégories
Méthode	Endpoint	Description
GET	/api/categories	Liste toutes les catégories
GET	/api/categories/{id}	Détails d'une catégorie
POST	/api/categories	Créer une catégorie

Produits

Méthode	Endpoint	Description
GET	/api/produits	Liste tous les produits
GET	/api/produits/categorie/{categorieId}	Produits par catégorie
GET	/api/produits/{id}	Détails d'un produit
POST	/api/produits?categorieId={id}	Créer un produit
DELETE	/api/produits/{id}	Supprimer un produit
Avis

Méthode	Endpoint	Description

GET	/api/avis/{produitId}	Liste des avis d'un produit
GET	/api/avis/{produitId}/moyenne	Moyenne des notes
POST	/api/avis	Ajouter un avis
