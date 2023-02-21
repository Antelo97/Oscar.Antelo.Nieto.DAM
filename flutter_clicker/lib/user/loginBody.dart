import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mysql1/mysql1.dart';
import 'package:flutter_clicker/database/database.dart';
import 'package:flutter_clicker/player.dart';
import 'package:flutter_clicker/game/bodyGame.dart';

// LoginBody -> Body que contiene la funcionalidad de login de un usuario

class LoginBody extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => LoginBodyExtended();
}

class LoginBodyExtended extends State<LoginBody> {
  final formKey = GlobalKey<FormState>();
  String username = "";
  String password = "";
  String errorMsg = "";
  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new Stack(children: [
      // Background
      new Container(
          decoration: new BoxDecoration(
              image: new DecorationImage(
                  image: new AssetImage("assets/imgBackgroundScreen.png"),
                  fit: BoxFit.cover))),
      new Center(
          child: Form(
              key: formKey,
              // Recuadro
              child: Container(
                  //alignment: Alignment.centerLeft,
                  width: 335,
                  height: 435,
                  decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(30),
                      image: new DecorationImage(
                          image: new AssetImage("assets/imgBackgroundBox.png"),
                          fit: BoxFit.cover),
                      boxShadow: [
                        new BoxShadow(
                            color: Color.fromARGB(255, 50, 151, 3),
                            offset: new Offset(12.0, 12.0),
                            blurRadius: 20.0)
                      ]),
                  child: Column(mainAxisAlignment: MainAxisAlignment.center,
                      //crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        // Titulo
                        Container(
                          color: Colors.blue,
                          child: Text("Acceder",
                              textAlign: TextAlign.left,
                              style: TextStyle(
                                  fontSize: 50,
                                  color: Colors.yellow,
                                  fontWeight: FontWeight.w500,
                                  fontFamily: "Dancing Script Regular",
                                  fontStyle: FontStyle.normal)),
                        ),
                        // Usuario
                        Container(
                            color: Colors.red,
                            width: 250,
                            height: 75,
                            margin: EdgeInsets.fromLTRB(0, 35, 0, 0),
                            child: TextFormField(
                                decoration: InputDecoration(
                                    icon: Icon(Icons.person,
                                        color: Colors.yellow),
                                    hintText: "Inserte su usuario",
                                    hintStyle: TextStyle(color: Colors.amber),
                                    labelText: "Usuario",
                                    labelStyle: TextStyle(
                                        color: Colors.amber, fontSize: 30),
                                    focusedBorder: UnderlineInputBorder(
                                        borderSide:
                                            BorderSide(color: Colors.amber))),
                                style: TextStyle(
                                    color: Colors.white,
                                    fontFamily: "Dancing Script Regular",
                                    fontSize: 20),
                                onSaved: (value) {
                                  username = value!;
                                },
                                validator: (value) {
                                  if (value!.isEmpty) {
                                    return "Error: Campo vacio";
                                  }
                                })),
                        // Contrasena
                        Container(
                          color: Colors.brown,
                            width: 250,
                            height: 75,
                            margin: EdgeInsets.fromLTRB(0, 35, 0, 20),
                            child: TextFormField(
                                decoration: InputDecoration(
                                  icon: Icon(Icons.lock, color: Colors.yellow),
                                  hintText: "Inserte su contrasena",
                                  hintStyle: TextStyle(color: Colors.amber),
                                  labelText: "Contrasena",
                                  labelStyle: TextStyle(
                                      color: Colors.amber, fontSize: 30),
                                  focusedBorder: UnderlineInputBorder(
                                    borderSide: BorderSide(color: Colors.amber),
                                  ),
                                ),
                                obscureText: true,
                                style: TextStyle(
                                    color: Colors.white,
                                    fontFamily: "Dancing Script Regular",
                                    fontSize: 20),
                                onSaved: (value) {
                                  password = value!;
                                },
                                validator: (value) {
                                  if (value!.isEmpty) {
                                    return "Error: Campo vacio";
                                  }
                                })),
                        SizedBox(height: 10),
                        if (errorMsg.isNotEmpty)
                          Text(errorMsg,
                              style: TextStyle(
                                  color: Colors.yellow,
                                  fontWeight: FontWeight.bold,
                                  fontFamily: "Dancing Script Regular",
                                  fontStyle: FontStyle.normal),
                              textAlign: TextAlign.center),
                        SizedBox(height: 10),
                        // Entrar
                        ElevatedButton(
                            child: Text(
                              "Entrar",
                              style: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: "Dancing Script Regular",
                                  fontWeight: FontWeight.w700),
                            ),
                            style: ElevatedButton.styleFrom(
                              primary: Colors.yellow,
                              textStyle: TextStyle(
                                  color: Colors.black,
                                  fontSize: 20,
                                  fontFamily: "Dancing Script Regular",
                                  fontWeight: FontWeight.w700),
                            ),
                            onPressed: () async {
                              if (formKey.currentState!.validate()) {
                                formKey.currentState!.save();
                                MySqlConnection conn =
                                    await connectToDatabase();
                                bool userExists = await checkUserLogin(
                                    conn, username, password);
                                if (userExists) {
                                  setState(() {
                                    errorMsg = "";
                                  });
                                  startGame(context);
                                } else {
                                  setState(() {
                                    errorMsg =
                                        "Usuario o contrasena incorrectos";
                                  });
                                }
                                await conn.close();
                              }
                            })
                      ]))))
    ]);
  }

  /* Funcion que entra al juego una vez se haya verificado que el usuario
  existe y su contraseña es correcta. Antes de entrar, tomara los datos del
  usuario concreto en la BBDD y setteara los atributos en el objeto Player
  auxiliar */
  void startGame(BuildContext context) async {
    if (formKey.currentState!.validate()) {
      formKey.currentState!.save();
      MySqlConnection conn = await connectToDatabase();
      player.username = username;
      player.password = password;
      score = player.score = await getDataInt(conn, "score", username);
      player.level = await getDataInt(conn, "level", username);
      player.levelName = await getDataString(conn, "levelname", username);
      player.clickMultiplier =
          await getDataInt(conn, "clickmultiplier", username);
      player.scorePerSecond =
          await getDataInt(conn, "scorepersecond", username);
      player.newLevel = await getDataInt(conn, "newlevel", username);
      player.iconClicker = await getDataString(conn, "iconclicker", username);
      player.boost1price = await getDataInt(conn, "boost1price", username);
      player.boost2price = await getDataInt(conn, "boost2price", username);
      player.boost3price = await getDataInt(conn, "boost3price", username);
      player.boost4price = await getDataInt(conn, "boost4price", username);
      player.boost5price = await getDataInt(conn, "boost5price", username);
      player.boost6price = await getDataInt(conn, "boost6price", username);
      player.boost7price = await getDataInt(conn, "boost7price", username);
      player.boost8price = await getDataInt(conn, "boost8price", username);
      player.boost9price = await getDataInt(conn, "boost9price", username);
      player.boost1amount = await getDataInt(conn, "boost1amount", username);
      player.boost2amount = await getDataInt(conn, "boost2amount", username);
      player.boost3amount = await getDataInt(conn, "boost3amount", username);
      player.boost4amount = await getDataInt(conn, "boost4amount", username);
      player.boost5amount = await getDataInt(conn, "boost5amount", username);
      player.boost6amount = await getDataInt(conn, "boost6amount", username);
      player.boost7amount = await getDataInt(conn, "boost7amount", username);
      player.boost8amount = await getDataInt(conn, "boost8amount", username);
      player.boost9amount = await getDataInt(conn, "boost9amount", username);
      conn.close();
      Navigator.of(context).pushNamed("/screenGame");
    }
  }
}
