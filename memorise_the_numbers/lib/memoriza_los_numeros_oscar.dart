import 'dart:async';
import 'dart:ffi';
import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:async/async.dart';
import 'package:flutter/services.dart';

int puntuacionOscar = 0;
int monedasTotal = 0;

List<int> gameNumbers = [];
List<int> oneToTen = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
List<String> playerNumbers = ["", "", "", "", "", "", "", "", "", ""];
List<String> btnNumbers = ["?", "?", "?", "?", "?", "?", "?", "?", "?", "?"];
List<bool> hitSequence = [
  false,
  false,
  false,
  false,
  false,
  false,
  false,
  false,
  false,
  false
];

RestartableTimer? timerGame;
int memoriseNumbers = -1;
int index = 0;
double timeSlider = 1;
String textRepeated = "Habilitar números repetidos";
bool isRepeated = false;
String textTotalNumbers = "10";

int hits = 0;

bool opacityMainBox = false;
bool opacitySettingBox = true;

bool enabledBottomButtons = false;
bool enabledBtnDelete = false;
bool enabledBtnStart = true;
bool enabledBtnConfirm = false;
bool enabledBtnSettings = true;

bool enabledBtnYesRepeat = true;
bool enabled10Numbers = true;
bool enabled20Numbers = true;
bool enabled30Numbers = true;
bool enabled40Numbers = true;
bool enabled50Numbers = true;
bool enabledSlider = true;

bool opacityHitSequence = false;

class ScreenGame extends StatefulWidget {
  @override
  /*
  'createState()' crea un objeto 'State' que guarda el estado interno del Widget, en este caso
  se está creando una instancia de 'ScreenUserExtended'
  */
  State<ScreenGame> createState() => ScreenGameExtended();
}

class ScreenGameExtended extends State<ScreenGame> {
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      // AppBar
      appBar: AppBar(
          title: Text(
            "Memoriza los números",
            style: TextStyle(
                color: Colors.white,
                fontFamily: "Dancing Script Regular",
                fontSize: 50,
                fontWeight: FontWeight.w900),
          ),
          toolbarHeight: 100,
          backgroundColor: Color.fromARGB(255, 0, 85, 154),
          //flexibleSpace: Image.asset("assets/imgAppBar.jpg", fit: BoxFit.cover),
          centerTitle: true),
      body: Stack(
        children: [
          Container(
            color: Colors.amber,
            /*
              decoration: new BoxDecoration(
                  image: new DecorationImage(
                      image: new AssetImage("assets/imgBackgroundScreen.png"),
                      fit: BoxFit.cover))
                      */
          ),
          Center(
            child: Column(
              children: [
                // Botón Start
                Container(
                    margin: EdgeInsets.fromLTRB(0, 30, 45, 10),
                    child: getRawButton("Comenzar", enabledBtnStart)),
                // Contenedor principal
                Row(
                  // Centrado horizontal de la fila
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    // btnDelete
                    Icon(Icons.delete_outline, size: 40, color: Colors.red),
                    SizedBox(width: 10),
                    getRawButton("Borrar", enabledBtnDelete),
                    SizedBox(width: 50),
                    // Contenedor principal
                    Container(
                      width: 130,
                      height: 130,
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
                      child: Center(
                        child: AnimatedOpacity(
                          opacity: opacityMainBox ? 1.0 : 0.0,
                          duration: Duration(seconds: 1),
                          child: Text(memoriseNumbers.toString(),
                              style: TextStyle(
                                  fontSize: 70,
                                  letterSpacing: 3,
                                  fontWeight: FontWeight.w900,
                                  fontFamily: "Dancing Script Regular",
                                  color: Colors.yellow)),
                        ),
                      ),
                    ),
                    SizedBox(width: 50),
                    getRawButton("Confirmar", enabledBtnConfirm),
                    SizedBox(width: 10),
                    Icon(Icons.check,
                        size: 40, color: Color.fromARGB(169, 0, 112, 4)),
                  ],
                ),
                SizedBox(height: 107),
                Column(
                  children: [
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: playerNumbers.map((e) {
                        return getTopContainers(e);
                      }).toList(),
                    ),
                    SizedBox(height: 50),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                      children: btnNumbers.map((e) {
                        return getBottomContainers(e);
                      }).toList(),
                    ),
                  ],
                ),
              ],
            ),
          ),
          Positioned(
            left: 0,
            top: 325,
            child: Row(
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                  children: oneToTen.map((e) {
                    return getBoxNumbers(e.toString());
                  }).toList(),
                ),
              ],
            ),
          ),
          Positioned(
            left: 4,
            top: 348,
            child: AnimatedOpacity(
              opacity: opacityHitSequence ? 1.0 : 0.0,
              duration: Duration(seconds: 1),
              child: Row(
                children: oneToTen.map((e) {
                  return getSolution(hitSequence[e - 1], e);
                }).toList(),
              ),
            ),
          ),
          // Ajustes
          Positioned(
              left: 1250,
              top: 15,
              child: getRawButton("Ajustes", enabledBtnSettings)),
          getSettingBox(),
          Positioned(
            left: 15,
            top: 15,
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                RawMaterialButton(
                  fillColor: Colors.black,
                  hoverColor: Color.fromARGB(255, 255, 17, 0),
                  highlightColor: Color.fromARGB(255, 255, 89, 77),
                  splashColor: Colors.yellow,
                  visualDensity: VisualDensity.standard,
                  padding: EdgeInsets.all(8),
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(8),
                    side: BorderSide(color: Colors.black, width: 3),
                  ),
                  child: Text("Volver",
                      style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 2,
                          fontWeight: FontWeight.w900,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.white)),
                  onPressed: () {
                    setState(() {
                      //---------ROBERTO-----------
                    });
                  },
                ),
                SizedBox(height: 40),
                Row(
                  children: [
                    Icon(Icons.man, size: 40, color: Colors.black),
                    SizedBox(width: 5),
                    Text("[nombre_usuario]",
                        style: TextStyle(
                          fontSize: 20,
                          letterSpacing: 2,
                          fontWeight: FontWeight.w900,
                          fontFamily: "Dancing Script Regular",
                          color: Colors.black,
                        )),
                  ],
                ),
                SizedBox(height: 10),
                Row(
                  children: [
                    Icon(Icons.money, size: 40, color: Colors.black),
                    SizedBox(width: 5),
                    Text("Puntuación: " + puntuacionOscar.toString(),
                        style: TextStyle(
                            fontSize: 20,
                            letterSpacing: 2,
                            fontWeight: FontWeight.w700,
                            fontFamily: "Dancing Script Regular",
                            color: Colors.black)),
                  ],
                ),
                SizedBox(height: 10),
                Row(
                  children: [
                    Icon(Icons.monetization_on, size: 40, color: Colors.black),
                    SizedBox(width: 5),
                    Text("Monedas: " + monedasTotal.toString(),
                        style: TextStyle(
                            fontSize: 20,
                            letterSpacing: 2,
                            fontWeight: FontWeight.w700,
                            fontFamily: "Dancing Script Regular",
                            color: Colors.black)),
                  ],
                )
              ],
            ),
          ),
        ],
      ),
    );
  }

  void startGame() {
    enabledBtnSettings = false;
    opacitySettingBox = false;
    modifySettingComponents();
    gameNumbers.clear();

    enabledBtnStart = false;
    Random random = new Random();
    int randomNumber = 0;

    List<String> btnNumbersTemp = [];

    if (isRepeated == false) {
      for (int x = 0; x < 10; x++) {
        randomNumber = random.nextInt(int.parse(textTotalNumbers)) + 1;
        if (gameNumbers.contains(randomNumber) == true) {
          x--;
        } else {
          gameNumbers.add(randomNumber);
          btnNumbersTemp.add(randomNumber.toString());
        }
      }
    } else {
      for (int x = 0; x < 10; x++) {
        randomNumber = random.nextInt(int.parse(textTotalNumbers)) + 1;
        gameNumbers.add(randomNumber);
        btnNumbersTemp.add(randomNumber.toString());
      }
    }

    opacityMainBox = true;
    memoriseNumbers = gameNumbers[index];
    index++;

    timerGame = RestartableTimer(Duration(seconds: timeSlider.toInt()), () {
      setState(() {
        if (index <= 9) {
          memoriseNumbers = gameNumbers[index];
          index++;
          timerGame!.reset();
        } else {
          timerGame!.cancel();
          opacityMainBox = false;
          enabledBottomButtons = true;
          enabledBtnDelete = true;
          enabledBtnConfirm = true;
          btnNumbersTemp.sort((a, b) => int.parse(a).compareTo(int.parse(b)));
          btnNumbers = btnNumbersTemp;
        }
      });
    });
  }

  int getIndex() {
    int index = 0;
    for (int i = 0; i < playerNumbers.length; i++) {
      if (playerNumbers[i] != "") {
        index++;
      }
    }
    return index;
  }

  int getHits() {
    hits = 0;
    for (int i = 0; i < gameNumbers.length; i++) {
      if (gameNumbers[i].toString() == playerNumbers[i]) {
        hits++;
        hitSequence[i] = true;
      } else {
        hitSequence[i] = false;
      }
    }
    return hits;
  }

  Widget getTopContainers(String playerNumber) {
    return Container(
      width: 130,
      height: 130,
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Color.fromARGB(100, 20, 0, 0),
          border: Border.all(color: Colors.yellow, width: 4),
          boxShadow: [
            new BoxShadow(
                color: Color.fromARGB(100, 0, 0, 0),
                offset: new Offset(0.0, 0.0),
                blurRadius: 50.0)
          ]),
      child: Center(
        child: Text(playerNumber,
            style: TextStyle(
                fontSize: 70,
                letterSpacing: 3,
                fontWeight: FontWeight.w900,
                fontFamily: "Dancing Script Regular",
                color: Colors.yellow)),
      ),
    );
  }

  Widget getBottomContainers(String btnNumber) {
    return Container(
      width: 130,
      height: 130,
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(20),
          color: Color.fromARGB(100, 0, 0, 0),
          border: Border.all(color: Colors.yellow, width: 4),
          boxShadow: [
            new BoxShadow(
                color: Color.fromARGB(150, 0, 0, 0),
                offset: new Offset(0.0, 0.0),
                blurRadius: 50.0)
          ]),
      child: Center(
        child: OutlinedButton(
          child: Text(btnNumber,
              style: TextStyle(
                  fontSize: 70,
                  letterSpacing: 3,
                  fontWeight: FontWeight.w900,
                  fontFamily: "Dancing Script Regular",
                  color: Colors.yellow)),
          onPressed: enabledBottomButtons
              ? () {
                  setState(() {
                    playerNumbers[getIndex()] = btnNumber;
                    checkEngdame();
                  });
                }
              : null,
        ),
      ),
    );
  }

  Widget getSolution(bool isRight, int index) {
    String img;
    double leftMargin = 0;

    if (isRight) {
      img = "assets/green.png";
    } else {
      img = "assets/red.png";
    }

    if (index == 1) {
      leftMargin = 20;
    } else if (index == 2 || index == 3 || index == 4) {
      leftMargin = 26;
    } else if (index == 5 || index == 6 || index == 7) {
      leftMargin = 27;
    } else {
      leftMargin = 26;
    }

    return Container(
      height: 125,
      width: 125,
      margin: EdgeInsets.fromLTRB(leftMargin, 0, 0, 0),
      decoration: BoxDecoration(
          image: new DecorationImage(image: new AssetImage(img)),
          boxShadow: [
            new BoxShadow(
                color: Colors.transparent,
                offset: new Offset(0.0, 0.0),
                blurRadius: 50.0)
          ]),
    );
  }

  Widget getBoxNumbers(String boxNumber) {
    double leftMargin;

    if (boxNumber == "1") {
      leftMargin = 7;
    } else if (boxNumber == "2" || boxNumber == "3" || boxNumber == "4") {
      leftMargin = 107;
    } else if (boxNumber == "5" || boxNumber == "6" || boxNumber == "7") {
      leftMargin = 107;
    } else {
      leftMargin = 107;
    }

    return Container(
      height: 45,
      width: 45,
      margin: EdgeInsets.fromLTRB(leftMargin, 0, 0, 0),
      decoration: BoxDecoration(
          borderRadius: BorderRadius.circular(40),
          color: Colors.black,
          border: Border.all(color: Colors.yellow, width: 4),
          boxShadow: [
            new BoxShadow(
                color: Colors.transparent,
                offset: new Offset(0.0, 0.0),
                blurRadius: 50.0)
          ]),
      child: Text(
        boxNumber + "º",
        style: TextStyle(
            color: Colors.yellow,
            fontFamily: "Dancing Script Regular",
            fontSize: 25,
            fontWeight: FontWeight.bold),
        textAlign: TextAlign.center,
      ),
    );
  }

  showEndgameMessage(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: Text("Juego finalizado"),
          content: Text("¡Has conseguido " +
              getHits().toString() +
              "/10 aciertos!\n\n+" +
              (getHits() * 20).toString() +
              " puntos"),
          actions: [
            OutlinedButton(
              onPressed: () {
                //msg = "SÍ";
                //mostrarSnackBar(context, msg, Colors.yellow);
                setState(() {
                  resetGame();
                  Navigator.pop(context);
                });
              },
              child: Text("Aceptar"),
            ),
          ],
        );
      },
    );
  }

  void checkEngdame() {
    if (!playerNumbers.contains("")) {
      enabledBtnConfirm = true;
    } else {
      enabledBtnConfirm = false;
    }
  }

  Widget getRawButton(String text, bool enabled) {
    Color hvrColor = Colors.black;
    Color hlhColor = Colors.black;
    int opacity = 255;

    if (text == "Confirmar" || text == "Sí") {
      hvrColor = Color.fromARGB(218, 0, 255, 8);
      hlhColor = Color.fromARGB(255, 87, 255, 93);
    } else if (text == "Volver" || text == "Borrar" || text == "No") {
      hvrColor = Color.fromARGB(255, 255, 17, 0);
      hlhColor = Color.fromARGB(255, 255, 89, 77);
    } else {
      hvrColor = Color.fromARGB(255, 0, 140, 255);
      hlhColor = Color.fromARGB(255, 101, 186, 255);
    }

    if (!enabled) {
      opacity = 120;
    }

    return Stack(
      children: [
        RawMaterialButton(
          fillColor: Color.fromARGB(opacity, 0, 0, 0),
          hoverColor: hvrColor,
          highlightColor: hlhColor,
          splashColor: Colors.yellow,
          visualDensity: VisualDensity.standard,
          padding: EdgeInsets.all(8),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(8),
            side: BorderSide(color: Color.fromARGB(opacity, 0, 0, 0), width: 3),
          ),
          constraints: BoxConstraints(minWidth: 50.0, minHeight: 10.0),
          child: Text(text,
              style: TextStyle(
                  fontSize: 20,
                  letterSpacing: 2,
                  fontWeight: FontWeight.w900,
                  fontFamily: "Dancing Script Regular",
                  color: Color.fromARGB(opacity, 255, 255, 255))),
          onPressed: enabled
              ? () {
                  setState(() {
                    if (text == "Borrar") {
                      playerNumbers[getIndex() - 1] = "";
                      checkEngdame();
                    } else if (text == "Comenzar") {
                      startGame();
                    } else if (text == "Confirmar") {
                      hits = getHits();
                      opacityHitSequence = true;
                      showEndgameMessage(context);
                    } else if (text == "Habilitar números repetidos") {
                      isRepeated = true;
                      textRepeated = "Deshabilitar números repetidos";
                    } else if (text == "Deshabilitar números repetidos") {
                      isRepeated = false;
                      textRepeated = "Habilitar números repetidos";
                    } else if (text == "10") {
                      textTotalNumbers = "10";
                      managementTotalNumbers(
                          textTotalNumbers,
                          enabled10Numbers,
                          enabled20Numbers,
                          enabled30Numbers,
                          enabled40Numbers,
                          enabled50Numbers);
                    } else if (text == "20") {
                      textTotalNumbers = "20";
                      managementTotalNumbers(
                          textTotalNumbers,
                          enabled10Numbers,
                          enabled20Numbers,
                          enabled30Numbers,
                          enabled40Numbers,
                          enabled50Numbers);
                    } else if (text == "30") {
                      textTotalNumbers = "30";
                      managementTotalNumbers(
                          textTotalNumbers,
                          enabled10Numbers,
                          enabled20Numbers,
                          enabled30Numbers,
                          enabled40Numbers,
                          enabled50Numbers);
                    } else if (text == "40") {
                      textTotalNumbers = "40";
                      managementTotalNumbers(
                          textTotalNumbers,
                          enabled10Numbers,
                          enabled20Numbers,
                          enabled30Numbers,
                          enabled40Numbers,
                          enabled50Numbers);
                    } else if (text == "50") {
                      textTotalNumbers = "50";
                      managementTotalNumbers(
                          textTotalNumbers,
                          enabled10Numbers,
                          enabled20Numbers,
                          enabled30Numbers,
                          enabled40Numbers,
                          enabled50Numbers);
                    } else if (text == "Ajustes") {
                      if (opacitySettingBox) {
                        opacitySettingBox = false;
                        modifySettingComponents();
                      } else {
                        opacitySettingBox = true;
                        modifySettingComponents();
                      }
                    }
                  });
                }
              : null,
        ),
      ],
    );
  }

  void managementTotalNumbers(String textTotalNumbers, bool enabled10,
      bool enabled20, bool enabled30, bool enabled40, bool enabled50) {
    if (textTotalNumbers == "10") {
      enabled10Numbers = false;
      enabled20Numbers = true;
      enabled30Numbers = true;
      enabled40Numbers = true;
      enabled50Numbers = true;
    } else if (textTotalNumbers == "20") {
      enabled10Numbers = true;
      enabled20Numbers = false;
      enabled30Numbers = true;
      enabled40Numbers = true;
      enabled50Numbers = true;
    } else if (textTotalNumbers == "30") {
      enabled10Numbers = true;
      enabled20Numbers = true;
      enabled30Numbers = false;
      enabled40Numbers = true;
      enabled50Numbers = true;
    } else if (textTotalNumbers == "40") {
      enabled10Numbers = true;
      enabled20Numbers = true;
      enabled30Numbers = true;
      enabled40Numbers = false;
      enabled50Numbers = true;
    } else if (textTotalNumbers == "50") {
      enabled10Numbers = true;
      enabled20Numbers = true;
      enabled30Numbers = true;
      enabled40Numbers = true;
      enabled50Numbers = false;
    }
  }

  void resetGame() {
    index = 0;
    puntuacionOscar += hits * 50;
    monedasTotal = (puntuacionOscar / 1000).floor();
    hits = 0;
    gameNumbers = [];
    playerNumbers = ["", "", "", "", "", "", "", "", "", ""];
    btnNumbers = ["?", "?", "?", "?", "?", "?", "?", "?", "?", "?"];
    opacityMainBox = false;
    opacityHitSequence = false;
    enabledBottomButtons = false;
    enabledBtnDelete = false;
    enabledBtnStart = true;
    enabledBtnConfirm = false;
    enabledBtnSettings = true;
  }

  Widget getSettingBox() {
    return Positioned(
      left: 1070,
      top: 75,
      child: AnimatedOpacity(
        opacity: opacitySettingBox ? 1.0 : 0.0,
        duration: Duration(seconds: 1),
        child: Container(
          height: 230,
          width: 450,
          margin: EdgeInsets.fromLTRB(0, 0, 0, 0),
          decoration: BoxDecoration(
              borderRadius: BorderRadius.circular(10),
              color: Color.fromARGB(150, 0, 0, 0),
              border: Border.all(color: Colors.yellow, width: 4),
              boxShadow: [
                new BoxShadow(
                    color: Colors.transparent,
                    offset: new Offset(0.0, 0.0),
                    blurRadius: 50.0)
              ]),
          child: Padding(
            padding: const EdgeInsets.fromLTRB(0, 15, 0, 0),
            child: Column(
              children: [
                Text("Intervalo de espera (s): " + timeSlider.toString(),
                    style: TextStyle(
                        fontSize: 20,
                        letterSpacing: 2,
                        fontWeight: FontWeight.w900,
                        fontFamily: "Dancing Script Regular",
                        color: Colors.white)),
                Column(
                  children: [
                    Slider(
                      value: timeSlider.toDouble(),
                      min: 0,
                      max: 5,
                      activeColor: Color.fromARGB(255, 0, 124, 225),
                      inactiveColor: Colors.white,
                      divisions: 5,
                      onChanged: enabledSlider
                          ? (value) {
                              setState(() {
                                timeSlider = value;
                                if (value == 0) {
                                  timeSlider = 1;
                                }
                              });
                            }
                          : null,
                    ),
                    Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                      getRawButton(textRepeated, enabledBtnYesRepeat),
                    ]),
                    SizedBox(height: 15),
                    Row(mainAxisAlignment: MainAxisAlignment.center, children: [
                      getRawButton("10", enabled10Numbers),
                      SizedBox(width: 20),
                      getRawButton("20", enabled20Numbers),
                      SizedBox(width: 20),
                      getRawButton("30", enabled30Numbers),
                      SizedBox(width: 20),
                      getRawButton("40", enabled40Numbers),
                      SizedBox(width: 20),
                      getRawButton("50", enabled50Numbers),
                    ]),
                  ],
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  void modifySettingComponents() {
    if (opacitySettingBox == true) {
      enabledBtnYesRepeat = true;
      enabled10Numbers = true;
      enabled20Numbers = true;
      enabled30Numbers = true;
      enabled40Numbers = true;
      enabled50Numbers = true;
      enabledSlider = true;
      managementTotalNumbers(
          textTotalNumbers,
          enabled10Numbers,
          enabled20Numbers,
          enabled30Numbers,
          enabled40Numbers,
          enabled50Numbers);
    } else {
      enabledBtnYesRepeat = false;
      enabled10Numbers = false;
      enabled20Numbers = false;
      enabled30Numbers = false;
      enabled40Numbers = false;
      enabled50Numbers = false;
      enabledSlider = false;
    }
  }
}
