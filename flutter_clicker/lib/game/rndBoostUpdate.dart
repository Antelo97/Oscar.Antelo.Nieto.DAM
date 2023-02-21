import 'dart:math';
import 'package:flutter/material.dart';

/* RndBoostUpdate -> Esta clase generara las elecciones de mejora aleatoria
que saldran cada tiempo. */

/* Classe RndBoostUpdate que contiene como atributos los puntos por click,
los puntos por segundo y un nombre que tendra la mejora aleatoria. */
class rndBoostUpdate {
  double clickMultiplier = 0;
  double scorePerSecond = 0;
  String boostName = "";
  rndBoostUpdate(
      {required this.clickMultiplier,
      required this.scorePerSecond,
      required this.boostName});

  double getClickMultiplier() {
    return this.clickMultiplier;
  }

  double getScorePerSecond() {
    return this.scorePerSecond;
  }

  String getBoostName() {
    return this.boostName;
  }
}

// Funcion que generara un List de tres elecciones de mejora aleatoria
List<rndBoostUpdate> genBoosters() {
  List<rndBoostUpdate> listBosster = [];
  for (int i = 0; i < 3; i++) {
    double clickMultiplierNew = genMultiplyClicker();
    double scorePerSecondNew = genMultiplySecond();
    String boostNameNew = genRandomName();
    listBosster.add(rndBoostUpdate(
        clickMultiplier: clickMultiplierNew,
        scorePerSecond: scorePerSecondNew,
        boostName: boostNameNew));
  }
  return listBosster;
}

// Funcion que genera el nombre de la mejora aleatoria
String genRandomName() {
  List<String> listRandomName = [
    "Pesticida para el huerto",
    "Salir en las noticias del pueblo",
    "Contratar nuevos campesinos del pueblo Este",
    "No te recomiendo comprar esta mejora ...",
    "Esta mejora puede ser buena ...",
    "Trabajadores del Sur",
    "Salir en el periodico",
    "Invertir en terrenos",
    "Robar los productos del huerto de al lado",
    "Rodar el tractor alrededor del huerto",
    "No dejar que las palomas caguen el huerto",
    "Contratar al tonto del pueblo como vigilante",
    "Reparar un pequeño desastre",
    "Mecanismo de replantacion automatica",
    "Sorpresa",
    "Tu compralo y ya",
    "Contratar grupo de abuelas",
    "Admitir explotar menores que trabajan en el huerto",
    "Esta mejora puede ser buena o mala (como todas)",
    "No se me ocurre que poner en esta mejora. Tu comprala"
        "Irte a dar un paseo por el pueblo",
    "Ir a la verbena del pueblo",
    "Dejar al vecino que se encarge del huerto"
  ];
  int ranNum = Random().nextInt(listRandomName.length - 0);
  return listRandomName[ranNum];
}

// Funcion que genera un valor aleatorio de puntos por click
double genMultiplyClicker() {
  double multiply = 0.0;
  int randomVariable = Random().nextInt(6);
  if (randomVariable == 0) {
    multiply = 0.5;
  } else if (randomVariable == 1) {
    multiply = 1.0;
  } else if (randomVariable == 2) {
    multiply = 1.5;
  } else if (randomVariable == 3) {
    multiply = 2.0;
  } else if (randomVariable == 4) {
    multiply = 2.5;
  } else if (randomVariable == 5) {
    multiply = 3.0;
  }
  return multiply;
}

// Funcion que genera un valor aleatorio de puntos por segundo
double genMultiplySecond() {
  double multiply = 0.0;
  int randomVariable = Random().nextInt(4);
  if (randomVariable == 0) {
    multiply = 0.0;
  } else if (randomVariable == 1) {
    multiply = 1.0;
  } else if (randomVariable == 2) {
    multiply = 2.0;
  } else if (randomVariable == 3) {
    multiply = 3.0;
  }
  return multiply;
}

/* Funcion que aleatoriamente suma o resta el valor de la mejora a la
estadistica actual */
int applyBooster(int variableToModify, int boostToImplement) {
  if (variableToModify < boostToImplement) {
    variableToModify += boostToImplement;
  } else {
    int rnd = Random().nextInt(5);
    if (rnd < 3) {
      variableToModify += boostToImplement;
    } else {
      variableToModify -= boostToImplement;
    }
  }
  return variableToModify;
}
