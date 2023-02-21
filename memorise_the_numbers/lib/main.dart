import 'package:flutter/material.dart';
import 'package:memorise_the_numbers/memoriza_los_numeros_oscar.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: 'Memorise the Numbers', 
        initialRoute:
            "/screenGame", 
        routes: {
          "/screenGame": (BuildContext context) => ScreenGame(),
        });
  }
}
