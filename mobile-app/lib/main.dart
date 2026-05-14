import 'package:flutter/material.dart';
import 'screens/categories_screen.dart';

void main() {
  runApp(const BoutiqueApp());
}

class BoutiqueApp extends StatelessWidget {
  const BoutiqueApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Boutique',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(
          seedColor: Colors.deepPurple,
          brightness: Brightness.light,
        ),
        useMaterial3: true,
        appBarTheme: const AppBarTheme(
          centerTitle: true,
          elevation: 0,
        ),
      ),
      home: const CategoriesScreen(),
    );
  }
}
