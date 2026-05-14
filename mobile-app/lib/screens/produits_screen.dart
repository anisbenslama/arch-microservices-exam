import 'package:flutter/material.dart';
import '../services/api_service.dart';
import '../models/produit.dart';
import 'avis_screen.dart';
import '../widgets/produit_card.dart';

class ProduitsScreen extends StatefulWidget {
  final int categorieId;
  final String categorieNom;

  const ProduitsScreen({
    super.key,
    required this.categorieId,
    required this.categorieNom,
  });

  @override
  State<ProduitsScreen> createState() => _ProduitsScreenState();
}

class _ProduitsScreenState extends State<ProduitsScreen> {
  final ApiService _apiService = ApiService();
  late Future<List<Produit>> _produitsFuture;

  @override
  void initState() {
    super.initState();
    _produitsFuture = _apiService.getProduitsByCategorie(widget.categorieId);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.categorieNom),
        backgroundColor: Colors.deepPurple,
        foregroundColor: Colors.white,
      ),
      body: FutureBuilder<List<Produit>>(
        future: _produitsFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  CircularProgressIndicator(),
                  SizedBox(height: 16),
                  Text('Chargement des produits...'),
                ],
              ),
            );
          }

          if (snapshot.hasError) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  const Icon(Icons.error_outline, size: 48, color: Colors.red),
                  const SizedBox(height: 16),
                  Text('Erreur: ${snapshot.error}'),
                  const SizedBox(height: 16),
                  ElevatedButton(
                    onPressed: () {
                      setState(() {
                        _produitsFuture = _apiService.getProduitsByCategorie(
                          widget.categorieId,
                        );
                      });
                    },
                    child: const Text('Réessayer'),
                  ),
                ],
              ),
            );
          }

          final produits = snapshot.data!;
          if (produits.isEmpty) {
            return Center(
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Icon(Icons.inventory_2_outlined,
                      size: 64, color: Colors.grey[400]),
                  const SizedBox(height: 16),
                  Text(
                    'Aucun produit dans cette catégorie',
                    style: TextStyle(color: Colors.grey[600]),
                  ),
                ],
              ),
            );
          }

          return ListView.builder(
            padding: const EdgeInsets.all(16),
            itemCount: produits.length,
            itemBuilder: (context, index) {
              final produit = produits[index];
              return ProduitCard(
                produit: produit,
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => AvisScreen(produit: produit),
                    ),
                  );
                },
              );
            },
          );
        },
      ),
    );
  }
}
