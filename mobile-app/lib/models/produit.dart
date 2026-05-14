class Produit {
  final int id;
  final String nom;
  final String description;
  final double prix;
  final String? imageUrl;
  final Map<String, dynamic>? categorie;

  Produit({
    required this.id,
    required this.nom,
    required this.description,
    required this.prix,
    this.imageUrl,
    this.categorie,
  });

  factory Produit.fromJson(Map<String, dynamic> json) {
    return Produit(
      id: json['id'],
      nom: json['nom'],
      description: json['description'] ?? '',
      prix: (json['prix'] as num).toDouble(),
      imageUrl: json['imageUrl'],
      categorie: json['categorie'],
    );
  }

  String get prixFormatted => '${prix.toStringAsFixed(2)} DT';
}
