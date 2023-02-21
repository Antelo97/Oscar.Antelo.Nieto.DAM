import 'package:mysql1/mysql1.dart';
import 'package:flutter_clicker/game/bodyGame.dart';
import 'package:flutter_clicker/game/bodyShop.dart';
import 'package:flutter_clicker/player.dart';

// Database -> Todas las funciones relacionadas con las operaciones en la BBDD

// FUNCIONES CONEXION

// Funcion encargada de obtener una conexion en la BBDD
Future<MySqlConnection> connectToDatabase() async {
  final conn = await MySqlConnection.connect(ConnectionSettings(
      host: 'localhost',
      port: 3306,
      user: 'userbbddclicker',
      password: 'asdf',
      db: 'bbddclicker'));
  return conn;
}

// FUNCIONES REGISTRO

// Funcion encargada de crear un usuario con las estadisticas por defecto
Future<void> createUser(MySqlConnection conn, Map<String, dynamic> data) async {
  await conn.query(
      'INSERT INTO usuarios (username, password, score, level, levelname, clickmultiplier, scorepersecond, newlevel, iconclicker, boost1price, boost2price, boost3price, boost4price, boost5price, boost6price, boost7price, boost8price, boost9price, boost1amount, boost2amount, boost3amount, boost4amount, boost5amount, boost6amount, boost7amount, boost8amount, boost9amount) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)',
      [
        data['username'],
        data['password'],
        0,
        1,
        "Pequeño",
        1,
        0,
        200,
        "assets/rPequeno.png",
        4200,
        11200,
        34500,
        52100,
        74000,
        103400,
        325800,
        680900,
        1005000,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
      ]);
}

/* Funcion encargada que verifica si ya existe un usuario registrado con ese
username */
Future<bool> checkUserRegister(MySqlConnection conn, String username) async {
  final results = await conn
      .query('SELECT username FROM usuarios WHERE username = ?', [username]);
  return results.isNotEmpty;
}

// FUNCIONES LOGIN

// Funcion que verifica si el usuario existe y su contraseña es correcta
Future<bool> checkUserLogin(
    MySqlConnection conn, String username, String password) async {
  final results = await conn.query(
      'SELECT COUNT(*) FROM usuarios WHERE username = ? AND password = ?',
      [username, password]);
  if (results.first[0] == 0) {
    return false;
  } else {
    return true;
  }
}

// FUNCIONES SELECT

/* Estas funciones se utilizaran para obtener los datos del usuario. Desde
el login se llamara tantas veces como datos haya que extraer. Son ambas,
dependiendo de si devuelve un valor Int o String
*/

// Funcion Int
Future<int> getDataInt(
    MySqlConnection conn, String select, String username) async {
  String querySql =
      "SELECT " + select + " FROM usuarios WHERE username = '" + username + "'";
  var doQuery = await conn.query(querySql);
  ResultRow resultQuery = doQuery.first;
  int result = resultQuery.first;
  return result;
}

// Funcion String
Future<String> getDataString(
    MySqlConnection conn, String select, String username) async {
  String querySql =
      "SELECT " + select + " FROM usuarios WHERE username = '" + username + "'";
  var doQuery = await conn.query(querySql);
  ResultRow resultQuery = doQuery.first;
  String result = resultQuery.first;
  return result;
}

// FUNCIONES UPDATE

// Funcion que se utiliza para actualizar y guardar los datos de un usuario
Future<void> saveData() async {
  final conn = await connectToDatabase();
  await conn.query(
      'UPDATE usuarios SET score = ?, level = ?, levelname = ?, clickmultiplier = ?, scorepersecond = ?, newlevel = ?, iconclicker = ?, boost1price = ?, boost2price = ?, boost3price = ?, boost4price = ?, boost5price = ?, boost6price = ?, boost7price = ?, boost8price = ?, boost9price = ?, boost1amount = ?, boost2amount = ?, boost3amount = ?, boost4amount = ?, boost5amount = ?, boost6amount = ?, boost7amount = ?, boost8amount = ?, boost9amount = ? WHERE username = ?',
      [
        score,
        level,
        levelName,
        clickMultiplier,
        scorePerSecond,
        newLevel,
        iconClicker,
        boost1price,
        boost2price,
        boost3price,
        boost4price,
        boost5price,
        boost6price,
        boost7price,
        boost8price,
        boost9price,
        boost1amount,
        boost2amount,
        boost3amount,
        boost4amount,
        boost5amount,
        boost6amount,
        boost7amount,
        boost8amount,
        boost9amount,
        player.username
      ]);
  await conn.close();
}
