/* LevelUpdate -> Estan todas las funciones que se encargan de los cambios
del juego en funcion del nivel actual, asi como el cambio de nivel del 
jugador al llegar a x puntos */

// Funcion que verifica si el usuario pasa de nivel o no
int lvlUpdate(int score, int level) {
  int newLevel = lvlNewLevelNeededPoints(level);
  bool levelUpdate = false;
  for (int i = 0; i <= score; i++) {
    if (i == newLevel) {
      levelUpdate = true;
    }
  }
  if (levelUpdate) {
    level += 1;
  }
  return level;
}

// Funcion que obtiene los puntos necesarios para avanzar al siguiente nivel
int lvlNewLevelNeededPoints(int level) {
  int newLevel = 0;
  if (level == 1) {
    newLevel = 200;
  } else if (level >= 2 && level <= 10) {
    newLevel = 200 + (level - 1) * 400;
  } else if (level >= 11 && level <= 15) {
    newLevel = 3800 + (level - 10) * 800;
  } else if (level >= 16 && level <= 35) {
    newLevel = 7800 + (level - 15) * 1600;
  } else if (level >= 36 && level <= 45) {
    newLevel = 39800 + (level - 35) * 3200;
  } else if (level >= 46 && level <= 60) {
    newLevel = 71800 + (level - 45) * 9600;
  } else if (level >= 61 && level <= 85) {
    newLevel = 215800 + (level - 60) * 28800;
  } else if (level >= 86 && level <= 98) {
    newLevel = 935800 + (level - 85) * 57600;
  } else if (level >= 99 && level <= 109) {
    newLevel = 1684600 + (level - 98) * 89200;
  } else if (level >= 110 && level <= 129) {
    newLevel = 2665800 + (level - 109) * 115200;
  } else if (level >= 130 && level <= 149) {
    newLevel = 4969800 + (level - 129) * 165900;
  } else if (level >= 150 && level <= 199) {
    newLevel = 8290800 + (level - 149) * 192500;
  } else if (level >= 200) {
    newLevel = 17915800 + (level - 199) * 385000;
  }
  return newLevel;
}

// Funcion que cambia el logo del icono del clicker en funcion del nivel
String lvlIconChange(int level) {
  String iconRute = "";
  if (level < 16) {
    iconRute = "assets/rPequeno.png";
  } else if (level >= 16 && level < 30) {
    iconRute = "assets/rMediano.png";
  } else if (level >= 30 && level < 70) {
    iconRute = "assets/rAdulto.png";
  } else if (level >= 70 && level < 100) {
    iconRute = "assets/rAbuelo.png";
  } else if (level >= 100 && level < 150) {
    iconRute = "assets/rLegendario.png";
  } else if (level >= 150 && level < 200) {
    iconRute = "assets/rMistico.png";
  } else if (level >= 200) {
    iconRute = "assets/rCelestial.png";
  }
  return iconRute;
}

// Funcion que cambia la fase del repollo en funcion del nivel
String lvlTextChange(int level) {
  String levelName = "";
  if (level < 16) {
    levelName = "Pequeño";
  } else if (level >= 16 && level < 30) {
    levelName = "Mediano";
  } else if (level >= 30 && level < 70) {
    levelName = "Adulto";
  } else if (level >= 70 && level < 100) {
    levelName = "Abuelo";
  } else if (level >= 100 && level < 150) {
    levelName = "Legendario";
  } else if (level >= 150 && level < 200) {
    levelName = "Mistico";
  } else if (level >= 200) {
    levelName = "Celestial";
  }
  return levelName;
}
