import 'dart:convert';
import 'package:http/http.dart' as http;
import '../models/categorie.dart';
import '../models/produit.dart';
import '../models/avis.dart';

class ApiService {
  // À MODIFIER : Mettez l'IP de votre machine sur le réseau local
  static const String baseUrl = 'http://192.168.1.X:8090'; // ⚠️ Changez X par votre IP
  // Pour l'émulateur Android : http://10.0.2.2:8090
  // Pour l'émulateur iOS : http://localhost:8090

  Future<List<Categorie>> getCategories() async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/api/categories'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => Categorie.fromJson(json)).toList();
      } else {
        throw Exception('Erreur: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Impossible de charger les catégories: $e');
    }
  }

  Future<List<Produit>> getProduitsByCategorie(int categorieId) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/api/produits/categorie/$categorieId'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => Produit.fromJson(json)).toList();
      } else {
        throw Exception('Erreur: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Impossible de charger les produits: $e');
    }
  }

  Future<List<Avis>> getAvisByProduit(int produitId) async {
    try {
      final response = await http.get(
        Uri.parse('$baseUrl/api/avis/$produitId'),
        headers: {'Content-Type': 'application/json'},
      );

      if (response.statusCode == 200) {
        List<dynamic> data = json.decode(response.body);
        return data.map((json) => Avis.fromJson(json)).toList();
      } else {
        throw Exception('Erreur: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Impossible de charger les avis: $e');
    }
  }

  Future<Avis> addAvis(Avis avis) async {
    try {
      final response = await http.post(
        Uri.parse('$baseUrl/api/avis'),
        headers: {
          'Content-Type': 'application/json',
        },
        body: json.encode(avis.toJson()),
      );

      if (response.statusCode == 201) {
        return Avis.fromJson(json.decode(response.body));
      } else if (response.statusCode == 404) {
        throw Exception('Produit non trouvé');
      } else if (response.statusCode == 400) {
        throw Exception('Note invalide (doit être entre 1 et 5)');
      } else {
        throw Exception('Erreur: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Impossible d\'ajouter l\'avis: $e');
    }
  }
}
