import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_clicker/database/database.dart';
import 'package:flutter_clicker/game/bodyGame.dart';
import 'package:flutter_clicker/game/bodyShop.dart';
import 'package:flutter_clicker/player.dart';

/* ScreenGame -> Pantalla compuesta por un AppBar que muestra el nombre
del usuario, un BottomNavigationBar que se necesita para desplazarse por las
distintas acciones del juego, y como body, dependiendo de cual haya clickeado
el usuario, sera la de juego o la de tienda.
*/

class ScreenGame extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => ScreenGameExtended();
}

class ScreenGameExtended extends State<ScreenGame> {
  int currentPage = 1; // Valor del NavigationBar, que representa una opcion
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    List<StatefulWidget> lstBodys = [BodyGame(), BodyGame(), BodyShop()];
    return Scaffold(
        // AppBar
        appBar: AppBar(
            title: Text(
              player.username,
              style: TextStyle(
                  color: Colors.yellow,
                  fontFamily: "Dancing Script Regular",
                  fontSize: 40),
            ),
            toolbarHeight: 50,
            flexibleSpace:
                Image.asset("assets/imgAppBar.jpg", fit: BoxFit.cover),
            centerTitle: true),
        // Body: CurrentPage
        body: lstBodys[currentPage],
        // NavigationBar
        bottomNavigationBar: BottomNavigationBar(
            onTap: (index) async {
              if (mounted) {
                setState(() {
                  currentPage = index;
                  if (index == 0) {
                    resetStats();
                  } else {}
                });
              }
              saveData();
            },
            currentIndex: currentPage,
            items: [
              BottomNavigationBarItem(
                  icon: Icon(Icons.restart_alt, size: 35, color: Colors.yellow),
                  label: ""),
              BottomNavigationBarItem(
                  icon: Icon(Icons.home, size: 35, color: Colors.yellow),
                  label: ""),
              BottomNavigationBarItem(
                  icon:
                      Icon(Icons.shopping_cart, size: 35, color: Colors.yellow),
                  label: ""),
            ],
            showSelectedLabels: false,
            showUnselectedLabels: false,
            backgroundColor: Color.fromARGB(255, 110, 57, 6)));
  }
}
