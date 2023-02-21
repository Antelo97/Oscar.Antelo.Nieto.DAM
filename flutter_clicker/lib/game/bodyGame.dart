import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:percent_indicator/linear_percent_indicator.dart';
import 'dart:async';
import 'dart:math';
import 'package:async/async.dart';
import 'package:audioplayers/audioplayers.dart';
import 'package:flutter_clicker/database/database.dart';
import 'package:flutter_clicker/game/levelUpdate.dart';
import 'package:flutter_clicker/game/rndBoostUpdate.dart';
import 'package:flutter_clicker/player.dart';

/* BodyGame -> Contiene el body que representa la parte del juego. El juego
esta compuesto de varios campos de texto donde se ven las estadisticas del
jugador, y un boton que seria el clicker del juego donde se obtienen los
puntos. A su vez aleatoriamente aparecen tres elecciones de mejora aleatorias 
y un clicker dorado aleatorio que da un boost al usuario. */

// VARIABLES
// Fondo de pantalla del juego (cambia en el repollo dorado)
String backgroundImage = "assets/imgBackgroundScreen.png";
// Estadisticas del usuario
int score = player.score; // Puntos
int level = player.level; // Nivel
String levelName = player.levelName; // Nombre del nivel o estado del repollo
int newLevel = player.newLevel; // Puntos necesarios para el siguiente nivel
int clickMultiplier = player.clickMultiplier; // Puntos por click
int scorePerSecond = player.scorePerSecond; // Puntos por segundo
String iconClicker = player.iconClicker; // Icono del repollo
// Timers
Timer? timerScorePerSecond; // Timer necesario para los puntos/segundo
RestartableTimer? timerShowUpgrades; // Timer para las elecciones de mejora
RestartableTimer? timerGoldenCabbage; // Timer de aparicion del dorado
Timer? autoSave; // Timer que genera un autoguardado automatico
// Elecciones de mejora aleatoria
bool enabledTile1 = false; // Habilita/Deshabilita Mejora 1
bool enabledTile2 = false; // Habilita/Deshabilita Mejora 2
bool enabledTile3 = false; // Habilita/Deshabilita Mejora 3
bool opacityTiles = false; // Muestra/Oculta Mejoras
List<rndBoostUpdate> bstUpdater = genBoosters(); // Genera Mejoras Aleatorias
int upgradeLvl1Price = 300; // Precio Mejora 1
int upgradeLvl2Price = 600; // Precio Mejora 2
int upgradeLvl3Price = 1200; // Precio Mejora 3
// Repollo dorado
double xRandom = 0; // Posicion X del Repollo Dorado
double yRandom = 0; // Posicion Y del Repollo Dorado
bool opacityRandom = false; // Muestra/Oculta Repollo Dorado
bool enabledRandom = false; // Habilita/Deshabilita Repollo Dorado

class BodyGame extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => BodyGameExtended();
}

class BodyGameExtended extends State<BodyGame> {
  @override
  void initState() {
    super.initState();
    // Autoguardado
    autoSave = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        saveData();
      });
    });
    // Puntos Por Segundo
    timerScorePerSecond = Timer.periodic(Duration(seconds: 1), (timer) {
      setState(() {
        score += scorePerSecond;
        level = lvlUpdate(score, level);
        newLevel = lvlNewLevelNeededPoints(level);
        iconClicker = lvlIconChange(level);
        levelName = lvlTextChange(level);
        upgradeLvl1Price = 300 + ((level - 1) * 200);
        upgradeLvl2Price = 600 + ((level - 1) * 400);
        upgradeLvl3Price = 1200 + ((level - 1) * 800);
      });
    });
    // Mostrar/Ocultar las elecciones de mejora
    timerShowUpgrades = RestartableTimer(Duration(seconds: 30), () {
      setState(() {
        enabledTile1 = true;
        enabledTile2 = true;
        enabledTile3 = true;
        opacityTiles = true;
        bstUpdater = genBoosters();
        Timer? timerHideUpgrades = Timer(const Duration(seconds: 10), () {
          enabledTile1 = false;
          enabledTile2 = false;
          enabledTile3 = false;
          opacityTiles = false;
          timerShowUpgrades?.reset();
        });
      });
    });
    // Mostrar/Ocultar Repollo Dorado en una posicion aleatoria
    timerGoldenCabbage = RestartableTimer(Duration(seconds: 120), () {
      setState(() {
        xRandom = Random().nextInt(2 - 0).toDouble();
        if (xRandom == 0) {
          xRandom = Random().nextInt(512).toDouble();
        } else {
          xRandom = (128 + Random().nextInt(512)).toDouble();
        }
        yRandom = Random().nextInt(2 - 0).toDouble();
        if (yRandom == 0) {
          yRandom = Random().nextInt(512).toDouble();
        } else {
          yRandom = (128 + Random().nextInt(512)).toDouble();
        }
        opacityRandom = true;
        enabledRandom = true;
        Timer quitGoldenCabbage = Timer(const Duration(seconds: 10), () {
          opacityRandom = false;
          enabledRandom = false;
          timerGoldenCabbage?.reset();
        });
      });
    });
  }

  Widget build(BuildContext context) {
    // TODO: implement build
    // Eleccion de mejora 1
    ListTile firstUpgrade = ListTile(
        title: Text("$upgradeLvl1Price : " + bstUpdater[0].getBoostName(),
            style: TextStyle(
                fontSize: 15,
                letterSpacing: 1,
                fontWeight: FontWeight.w700,
                fontFamily: "Dancing Script Regular",
                color: Colors.yellow)),
        visualDensity: VisualDensity(vertical: -3),
        hoverColor: Color.fromARGB(100, 199, 199, 199),
        onTap: enabledTile1
            ? () {
                if (score >= upgradeLvl1Price) {
                  setState(() {
                    score -= upgradeLvl1Price;
                    scorePerSecond = applyBooster(scorePerSecond,
                        bstUpdater[0].getScorePerSecond().toInt());
                    clickMultiplier = applyBooster(clickMultiplier,
                        bstUpdater[0].getClickMultiplier().toInt());
                    enabledTile1 = false;
                    enabledTile2 = false;
                    enabledTile3 = false;
                    opacityTiles = false;
                  });
                } else {}
              }
            : null);
    // Eleccion de mejora 2
    ListTile secondUpgrade = ListTile(
        title: Text("$upgradeLvl2Price : " + bstUpdater[1].boostName,
            style: TextStyle(
                fontSize: 15,
                letterSpacing: 1,
                fontWeight: FontWeight.w700,
                fontFamily: "Dancing Script Regular",
                color: Colors.yellow)),
        visualDensity: VisualDensity(vertical: -3),
        hoverColor: Color.fromARGB(100, 199, 199, 199),
        onTap: enabledTile2
            ? () {
                if (score >= upgradeLvl2Price) {
                  setState(() {
                    score -= upgradeLvl2Price;
                    scorePerSecond = applyBooster(scorePerSecond,
                        bstUpdater[1].getScorePerSecond().toInt());
                    clickMultiplier = applyBooster(clickMultiplier,
                        bstUpdater[1].getClickMultiplier().toInt());
                    enabledTile1 = false;
                    enabledTile2 = false;
                    enabledTile3 = false;
                    opacityTiles = false;
                  });
                } else {}
              }
            : null);
    // Eleccion de mejora 3
    ListTile thirdUpgrade = ListTile(
        title: Text("$upgradeLvl3Price : " + bstUpdater[2].boostName,
            style: TextStyle(
                fontSize: 15,
                letterSpacing: 1,
                fontWeight: FontWeight.w700,
                fontFamily: "Dancing Script Regular",
                color: Colors.yellow)),
        visualDensity: VisualDensity(vertical: -3),
        hoverColor: Color.fromARGB(100, 199, 199, 199),
        onTap: enabledTile3
            ? () {
                if (score >= upgradeLvl3Price) {
                  setState(() {
                    score -= upgradeLvl3Price;
                    scorePerSecond = applyBooster(scorePerSecond,
                        bstUpdater[2].getScorePerSecond().toInt());
                    clickMultiplier = applyBooster(clickMultiplier,
                        bstUpdater[2].getClickMultiplier().toInt());
                    enabledTile1 = false;
                    enabledTile2 = false;
                    enabledTile3 = false;
                    opacityTiles = false;
                  });
                } else {}
              }
            : null);
    return Stack(children: <Widget>[
      // Background del juego
      Container(
          decoration: new BoxDecoration(
              image: new DecorationImage(
                  image: new AssetImage(backgroundImage), fit: BoxFit.cover))),
      Column(children: [
        // Animacion de opacidad de las elecciones de mejora
        AnimatedOpacity(
            opacity: opacityTiles ? 1.0 : 0.0,
            duration: Duration(seconds: 1),
            child: Center(
              child: Container(
                  width: 520,
                  height: 150,
                  margin: EdgeInsets.fromLTRB(0, 20, 0, 0),
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: Color.fromARGB(100, 0, 0, 0),
                      border: Border.all(color: Colors.yellow, width: 4),
                      boxShadow: [
                        new BoxShadow(
                            color: Color.fromARGB(100, 0, 0, 0),
                            offset: new Offset(0.0, 0.0),
                            blurRadius: 50.0)
                      ]),
                  child: Material(
                    color: Colors.transparent,
                    child: Column(
                        children: [firstUpgrade, secondUpgrade, thirdUpgrade]),
                  )),
            )),
        // Recuadro
        Container(
            width: 320,
            height: 480,
            margin: EdgeInsets.fromLTRB(0, 20, 0, 0),
            decoration: BoxDecoration(
                borderRadius: BorderRadius.circular(20),
                color: Color.fromARGB(100, 0, 0, 0),
                border: Border.all(color: Colors.yellow, width: 4),
                boxShadow: [
                  new BoxShadow(
                      color: Color.fromARGB(100, 0, 0, 0),
                      offset: new Offset(0.0, 0.0),
                      blurRadius: 50.0)
                ]),
            child: Container(
                margin: EdgeInsets.fromLTRB(0, 30, 0, 0),
                child: Column(children: [
                  // Mostrar Repollos
                  Container(
                      child: Text("Repollos: $score",
                          style: TextStyle(
                              fontSize: 40,
                              letterSpacing: 3,
                              fontWeight: FontWeight.w900,
                              fontFamily: "Dancing Script Regular",
                              color: Colors.yellow)),
                      margin: EdgeInsets.fromLTRB(0, 0, 0, 0)),
                  // Mostrar Puntos por click y Puntos por segundo
                  Container(
                      child: Text(
                          "R/C: $clickMultiplier | R/S: $scorePerSecond",
                          style: TextStyle(
                              fontSize: 30,
                              letterSpacing: 2,
                              fontWeight: FontWeight.w700,
                              fontFamily: "Dancing Script Regular",
                              color: Colors.yellow)),
                      margin: EdgeInsets.fromLTRB(0, 10, 0, 0)),
                  // Clicker
                  Container(
                      margin: EdgeInsets.fromLTRB(0, 25, 0, 10),
                      child: IconButton(
                          iconSize: 135,
                          icon: Image.asset(iconClicker),
                          onPressed: () {
                            setState(() {
                              score += 1 * clickMultiplier;
                              level = lvlUpdate(score, level);
                              newLevel = lvlNewLevelNeededPoints(level);
                              iconClicker = lvlIconChange(level);
                              levelName = lvlTextChange(level);
                              upgradeLvl1Price = 300 + ((level - 1) * 200);
                              upgradeLvl2Price = 600 + ((level - 1) * 400);
                              upgradeLvl3Price = 1200 + ((level - 1) * 800);
                            });
                          })),
                  // ProgressBar del nivel actual al siguiente
                  Padding(
                    padding: EdgeInsets.fromLTRB(50, 0, 0, 0),
                    child: LinearPercentIndicator(
                      width: 225,
                      lineHeight: 15,
                      percent: score / newLevel,
                      progressColor: Colors.yellow,
                      backgroundColor: Color.fromARGB(255, 223, 185, 17),
                      barRadius: const Radius.circular(20),
                    ),
                  ),
                  // Mostrar Nivel
                  Container(
                      child: Text("Nivel $level",
                          style: TextStyle(
                              fontSize: 30,
                              letterSpacing: 2,
                              fontWeight: FontWeight.w700,
                              fontFamily: "Dancing Script Regular",
                              color: Colors.yellow)),
                      margin: EdgeInsets.fromLTRB(0, 25, 0, 0)),
                  // Mostrar Fase del repollo
                  Container(
                      child: Text("Repollo $levelName",
                          style: TextStyle(
                              fontSize: 30,
                              letterSpacing: 2,
                              fontWeight: FontWeight.w700,
                              fontFamily: "Dancing Script Regular",
                              color: Colors.yellow)),
                      margin: EdgeInsets.fromLTRB(0, 10, 0, 0))
                ])))
      ]),
      // Repollo dorado
      Positioned(
          child: AnimatedOpacity(
              opacity: opacityRandom ? 1.0 : 0.0,
              duration: const Duration(seconds: 2),
              child: IconButton(
                  iconSize: 50,
                  icon: Image.asset("assets/rDorado.png"),
                  onPressed: enabledRandom
                      ? () {
                          Timer? oneSecondDelay =
                              Timer(const Duration(seconds: 1), () {});
                          setState(() {
                            enabledRandom = false;
                            opacityRandom = false;
                            int pastClickMultiplier = clickMultiplier;
                            int pastScorePerSecond = scorePerSecond;
                            clickMultiplier = (clickMultiplier * 5) + 1;
                            scorePerSecond = (scorePerSecond * 5) + 1;
                            backgroundImage = "assets/imgBackgroundGolden.png";
                            Timer timerShowRandom =
                                Timer(const Duration(seconds: 10), () {
                              clickMultiplier = pastClickMultiplier;
                              scorePerSecond = pastScorePerSecond;
                              enabledRandom = false;
                              opacityRandom = false;
                              backgroundImage =
                                  "assets/imgBackgroundScreen.png";
                            });
                          });
                        }
                      : null)),
          left: xRandom,
          top: yRandom)
    ]);
  }
}
