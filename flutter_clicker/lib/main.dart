import 'package:flutter/material.dart';
import 'package:flutter_clicker/game/screenGame.dart';
import 'package:flutter_clicker/user/screenUser.dart';

void main() {
  // 'runApp' recibe como parámetro una referencia de la clase 'StartGame', llamando a su constructor
  runApp(const StartGame());

  /*
  También se puede crear una instancia de la clase y pasarla como parámetro
  StartGame sg = new StartGame();
  runApp(sg);
  */
}

class StartGame extends StatelessWidget {
  /* 
  Definición del constructor de la clase
  El 'super' implica una llamada al constructor de la clase padre, en este caso StatelessWidget, 
  que se le está pasando la key como parámetro/argumento 
  */
  const StartGame({super.key});

  /*
  Otra forma de invocar el constructor de la clase padre y enviarle un objeto de tipo key
  a través de la clase hija
  const StartGame({required Key key}) : super(key: key);
  */
  
  @override
  Widget build(BuildContext context) {
    // La clase 'MaterialApp' representa el Widget raíz de la aplicación
    return MaterialApp(
        title: 'Cabbage Clicker', // Solo visible en navegador (p.e. Chrome)
        initialRoute: "/screenGame", // El ruteo es un servicio proporcionado por la clase 'MaterialApp'
        routes: {
          // Cuando el usuario navega a "/screenUser", se devuelve una instancia de la clase 'ScreenUser' como Widget
          "/screenUser": (BuildContext context) => ScreenUser(),
          // Cuando el usuario navega a "/screenGame", se devuelve una instancia de la clase 'ScreenGame' como Widget
          "/screenGame": (BuildContext context) => ScreenGame()
        });
  }
}
