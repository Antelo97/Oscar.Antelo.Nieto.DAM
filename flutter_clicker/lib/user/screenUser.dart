import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_clicker/user/loginBody.dart';
import 'package:flutter_clicker/user/registrerBody.dart';

/* ScreenUser -> Pantalla compuesta por un AppBar que muestra el nombre
del juego, un BottomNavigationBar que se necesita para desplazarse por las
distintas acciones del juego, y como body, dependiendo de cual haya clickeado
el usuario, sera la de login o la de registro.
*/

int currentPage = 0; // Valor del NavigationBar, que representa una opcion

class ScreenUser extends StatefulWidget {
  @override
  /*
  'createState()' crea un objeto 'State' que guarda el estado interno del Widget, en este caso
  se está creando una instancia de 'ScreenUserExtended'
  */
  State<ScreenUser> createState() => ScreenUserExtended();
}

class ScreenUserExtended extends State<ScreenUser> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
        // AppBar
        appBar: AppBar(
            title: Text(
              "Repollo Clicker",
              style: TextStyle(
                  color: Colors.yellow,
                  fontFamily: "Dancing Script Regular",
                  fontSize: 40),
            ),
            toolbarHeight: 45,
            flexibleSpace:
                Image.asset("assets/imgAppBar.jpg", fit: BoxFit.cover),
            centerTitle: true),
        // Body: CurrentPage
        body: currentPage == 0 ? LoginBody() : RegistrerBody(),
        // NavigationBar
        bottomNavigationBar: BottomNavigationBar(
            onTap: (index) {
              setState(() {
                currentPage = index;
              });
            },
            currentIndex: currentPage,
            items: [
              BottomNavigationBarItem(
                  icon: Icon(Icons.login, size: 35, color: Colors.yellow),
                  label: ""),
              BottomNavigationBarItem(
                  icon: Icon(Icons.app_registration,
                      size: 35, color: Colors.yellow),
                  label: ""),
            ],
            showSelectedLabels: false,
            showUnselectedLabels: false,
            backgroundColor: Color.fromARGB(255, 110, 57, 6)));
  }
}
