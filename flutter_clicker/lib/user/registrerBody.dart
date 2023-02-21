import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:mysql1/mysql1.dart';
import 'package:flutter_clicker/database/database.dart';
import 'package:flutter_clicker/user/screenUser.dart';

// RegistrerBody -> Body que contiene la funcionalidad de registro de un usuario

class RegistrerBody extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => RegistrerBodyExtended();
}

class RegistrerBodyExtended extends State<RegistrerBody> {
  final formKey = GlobalKey<FormState>();
  String username = "";
  String password = "";
  bool usernameExists = false;
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
                  child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        // Titulo
                        Text("Registro",
                            style: TextStyle(
                                fontSize: 50,
                                color: Colors.yellow,
                                fontWeight: FontWeight.w500,
                                fontFamily: "Dancing Script Regular",
                                fontStyle: FontStyle.normal)),
                        // Usuario
                        Container(
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
                            width: 250,
                            height: 75,
                            margin: EdgeInsets.fromLTRB(0, 35, 0, 20),
                            child: TextFormField(
                                decoration: InputDecoration(
                                    icon:
                                        Icon(Icons.lock, color: Colors.yellow),
                                    hintText: "Inserte su contrasena",
                                    hintStyle: TextStyle(color: Colors.amber),
                                    labelText: "Contrasena",
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
                        // Confirmar
                        ElevatedButton(
                            child: Text("Confirmar"),
                            style: ElevatedButton.styleFrom(
                                primary: Colors.yellow,
                                textStyle: TextStyle(
                                    fontSize: 20,
                                    fontFamily: "Dancing Script Regular",
                                    fontWeight: FontWeight.w700)),
                            onPressed: () async {
                              if (formKey.currentState!.validate()) {
                                formKey.currentState!.save();
                                MySqlConnection conn =
                                    await connectToDatabase();
                                usernameExists =
                                    await checkUserRegister(conn, username);
                                if (usernameExists) {
                                  await conn.close();
                                  setState(() {
                                    errorMsg = "Username ya registrado";
                                  });
                                } else {
                                  final data = {
                                    'username': username,
                                    'password': password,
                                  };
                                  await createUser(conn, data);
                                  setState(() {
                                    errorMsg = "";
                                  });
                                  await conn.close();
                                  loginBack(context);
                                }
                              }
                            })
                      ]))))
    ]);
  }

  // Funcion que vuelve al apartado de login
  void loginBack(BuildContext context) {
    setState(() {
      currentPage = 0;
      Navigator.of(context).pushNamed("/screenUser");
    });
  }
}
