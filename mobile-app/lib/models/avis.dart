class Avis {
  final int id;
  final int produitId;
  final String auteur;
  final String commentaire;
  final int note;
  final DateTime? dateCreation;

  Avis({
    required this.id,
    required this.produitId,
    required this.auteur,
    required this.commentaire,
    required this.note,
    this.dateCreation,
  });

  factory Avis.fromJson(Map<String, dynamic> json) {
    return Avis(
      id: json['id'],
      produitId: json['produitId'],
      auteur: json['auteur'],
      commentaire: json['commentaire'] ?? '',
      note: json['note'],
      dateCreation: json['dateCreation'] != null
          ? DateTime.parse(json['dateCreation'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'produitId': produitId,
      'auteur': auteur,
      'commentaire': commentaire,
      'note': note,
    };
  }
}
