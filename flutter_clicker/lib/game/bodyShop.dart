import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:math';
import 'package:audioplayers/audioplayers.dart';
import 'package:async/async.dart';
import 'package:flutter_clicker/game/bodyGame.dart';
import 'package:flutter_clicker/player.dart';

/* BodyShop -> Contiene el body que representa la parte de la tienda del juego.
A traves de los puntos obtenidos, se pueden comprar mejoras que aumentan los
puntos por click o los puntos por segundo.
*/

// VARIABLES
// Precio de las mejoras
int boost1price = player.boost1price; // Precio Mejora 1
int boost2price = player.boost2price; // Precio Mejora 2
int boost3price = player.boost3price; // Precio Mejora 3
int boost4price = player.boost4price; // Precio Mejora 4
int boost5price = player.boost5price; // Precio Mejora 5
int boost6price = player.boost6price; // Precio Mejora 6
int boost7price = player.boost7price; // Precio Mejora 7
int boost8price = player.boost8price; // Precio Mejora 8
int boost9price = player.boost9price; // Precio Mejora 9
// Cantidad adquirida de las mejoras
int boost1amount = player.boost1amount; // Cantidad adquirida Mejora 1
int boost2amount = player.boost2amount; // Cantidad adquirida Mejora 2
int boost3amount = player.boost3amount; // Cantidad adquirida Mejora 3
int boost4amount = player.boost4amount; // Cantidad adquirida Mejora 4
int boost5amount = player.boost5amount; // Cantidad adquirida Mejora 5
int boost6amount = player.boost6amount; // Cantidad adquirida Mejora 6
int boost7amount = player.boost7amount; // Cantidad adquirida Mejora 7
int boost8amount = player.boost8amount; // Cantidad adquirida Mejora 8
int boost9amount = player.boost9amount; // Cantidad adquirida Mejora 9

class BodyShop extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => BodyShopExtended();
}

class BodyShopExtended extends State<BodyShop> {
  @override
  void initState() {
    super.initState();
  }

  Widget build(BuildContext context) {
    // TODO: implement build
    return Stack(children: <Widget>[
      // Background del juego
      Container(
          decoration: new BoxDecoration(
              image: new DecorationImage(
                  image: new AssetImage("assets/imgBackgroundScreen.png"),
                  fit: BoxFit.cover))),
      Center(
        // Recuadro
        child: Container(
          width: 480,
          height: 600,
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(20),
              color: Color.fromARGB(100, 0, 0, 0),
              border: Border.all(color: Colors.yellow, width: 4),
              boxShadow: [
                new BoxShadow(
                    color: Color.fromARGB(100, 0, 0, 0),
                    offset: new Offset(20.0, 20.0),
                    blurRadius: 10.0)
              ]),
          child: Container(
            margin: EdgeInsets.fromLTRB(10, 20, 10, 0),
            child: Column(children: [
              // Mejora 1
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Polvo de hada: $boost1price  |  Cantidad: $boost1amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop1.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost1price) {
                      setState(() {
                        score -= boost1price;
                        scorePerSecond += 1;
                        clickMultiplier += 1;
                        boost1price += (boost1price * 0.30).round();
                        boost1amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 2
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Granada fertilizante: $boost2price  |  Cantidad: $boost2amount",
                      style: TextStyle(
                          fontSize: 18,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop2.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost2price) {
                      setState(() {
                        score -= boost2price;
                        scorePerSecond += 2;
                        clickMultiplier += 2;
                        boost2price += (boost2price * 0.35).round();
                        boost2amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 3
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Herramienta: $boost3price  |  Cantidad: $boost3amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop3.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost3price) {
                      setState(() {
                        score -= boost3price;
                        scorePerSecond += 0;
                        clickMultiplier += 6;
                        boost3price += (boost3price * 0.40).round();
                        boost3amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 4
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text("Droga: $boost4price  |  Cantidad: $boost4amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop4.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost4price) {
                      setState(() {
                        score -= boost4price;
                        scorePerSecond += 8;
                        clickMultiplier += 0;
                        boost4price += (boost4price * 0.45).round();
                        boost4amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 5
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Inversion: $boost5price  |  Cantidad: $boost5amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop5.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost5price) {
                      setState(() {
                        score -= boost5price;
                        scorePerSecond += 5;
                        clickMultiplier += 4;
                        boost5price += (boost5price * 0.6).round();
                        boost5amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 6
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Pocima: $boost6price  |  Cantidad: $boost6amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop6.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost6price) {
                      setState(() {
                        score -= boost6price;
                        scorePerSecond += 10;
                        clickMultiplier += 10;
                        boost6price += (boost6price * 0.8).round();
                        boost6amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 7
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Envenenador de rivales: $boost7price  |  Cantidad: $boost7amount",
                      style: TextStyle(
                          fontSize: 17,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop7.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost7price) {
                      setState(() {
                        score -= boost7price;
                        scorePerSecond += 15;
                        clickMultiplier += 5;
                        boost7price += (boost7price * 1).round();
                        boost7amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 8
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text("Carta: $boost8price  |  Cantidad: $boost8amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop8.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost8price) {
                      setState(() {
                        score -= boost8price;
                        scorePerSecond += 0;
                        clickMultiplier += 20;
                        boost8price += (boost8price * 1.5).round();
                        boost8amount++;
                      });
                    } else {}
                  },
                ),
              ),
              // Mejora 9
              Material(
                color: Colors.transparent,
                child: ListTile(
                  hoverColor: Color.fromARGB(100, 199, 199, 199),
                  title: Text(
                      "Portal: $boost9price  |  Cantidad: $boost9amount",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 1,
                          fontWeight: FontWeight.w700,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.yellow)),
                  leading: Image.asset("assets/shop9.png"),
                  visualDensity: VisualDensity(vertical: 1.5),
                  onTap: () {
                    Timer? oneSecondDelay =
                        Timer(const Duration(seconds: 1), () {});
                    if (score >= boost9price) {
                      setState(() {
                        score -= boost9price;
                        Timer? oneSecondDelay =
                            Timer(const Duration(seconds: 1), () {});
                        scorePerSecond += 30;
                        clickMultiplier += 30;
                        boost9price += (boost9price * 2).round();
                        boost9amount++;
                      });
                    } else {}
                  },
                ),
              ),
            ]),
          ),
        ),
      )
    ]);
  }
}
