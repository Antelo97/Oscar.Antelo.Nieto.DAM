import 'package:flutter_clicker/database/database.dart';
import 'package:flutter_clicker/game/bodyGame.dart';
import 'package:flutter_clicker/game/bodyShop.dart';

/* Player -> Objeto Player contendra todos los datos del jugador, tales como
usuario, contraseña y estadisticas de este (puntos, mejoras compradas, ...) */

// Clase Player definida
class Player {
  String username;
  String password;
  int score;
  int level;
  String levelName;
  int clickMultiplier;
  int scorePerSecond;
  int newLevel;
  String iconClicker;
  int boost1price;
  int boost2price;
  int boost3price;
  int boost4price;
  int boost5price;
  int boost6price;
  int boost7price;
  int boost8price;
  int boost9price;
  int boost1amount;
  int boost2amount;
  int boost3amount;
  int boost4amount;
  int boost5amount;
  int boost6amount;
  int boost7amount;
  int boost8amount;
  int boost9amount;

  Player(
      {required this.username,
      required this.password,
      required this.score,
      required this.level,
      required this.levelName,
      required this.clickMultiplier,
      required this.scorePerSecond,
      required this.newLevel,
      required this.iconClicker,
      required this.boost1price,
      required this.boost2price,
      required this.boost3price,
      required this.boost4price,
      required this.boost5price,
      required this.boost6price,
      required this.boost7price,
      required this.boost8price,
      required this.boost9price,
      required this.boost1amount,
      required this.boost2amount,
      required this.boost3amount,
      required this.boost4amount,
      required this.boost5amount,
      required this.boost6amount,
      required this.boost7amount,
      required this.boost8amount,
      required this.boost9amount});
}

/* Aqui se va a definir un objeto Player que se utilizara como soporte para
extraer los datos de un usuario en concreto. Al hacer el login del usuario,
y al comprobar que el usuario existe, y los datos son correctos, a traves
de varias consultas, se obtendran todas las estadisticas del jugador y se
setearan a los parametros de este objeto. Una vez seteados, el juego tomara
las estadisticas de este objeto */
Player player = new Player(
    username: "",
    password: "",
    score: 0,
    level: 0,
    levelName: "",
    clickMultiplier: 0,
    scorePerSecond: 0,
    newLevel: 0,
    iconClicker: "",
    boost1price: 0,
    boost2price: 0,
    boost3price: 0,
    boost4price: 0,
    boost5price: 0,
    boost6price: 0,
    boost7price: 0,
    boost8price: 0,
    boost9price: 0,
    boost1amount: 0,
    boost2amount: 0,
    boost3amount: 0,
    boost4amount: 0,
    boost5amount: 0,
    boost6amount: 0,
    boost7amount: 0,
    boost8amount: 0,
    boost9amount: 0);

// Esta funcion se utiliza para reiniciar las estadisticas del usuario
void resetStats() {
  score = 0;
  level = 1;
  levelName = "Pequeño";
  clickMultiplier = 1;
  scorePerSecond = 0;
  newLevel = 200;
  iconClicker = "assets/rPequeno.png";
  boost1price = 4200;
  boost2price = 11200;
  boost3price = 34500;
  boost4price = 52100;
  boost5price = 74000;
  boost6price = 103400;
  boost7price = 325800;
  boost8price = 680900;
  boost9price = 1005000;
  boost1amount = 0;
  boost2amount = 0;
  boost3amount = 0;
  boost4amount = 0;
  boost5amount = 0;
  boost6amount = 0;
  boost7amount = 0;
  boost8amount = 0;
  boost9amount = 0;
  saveData();
}
