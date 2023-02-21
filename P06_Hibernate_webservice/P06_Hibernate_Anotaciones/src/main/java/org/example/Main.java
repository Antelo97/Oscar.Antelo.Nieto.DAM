package org.example;

import com.google.gson.*;

import java.io.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.example.conn.HibernateUtil;
import org.example.meth.MethCharToEp;
import org.example.meth.MethCharacter;
import org.example.meth.MethEpisode;
import org.example.meth.MethLocation;
import org.example.pojos.CharToEp;
import org.example.pojos.Character;
import org.example.pojos.Episode;
import org.example.pojos.Location;

public class Main {

    public static void main(String[] args) throws IOException {
        createAllTables();
        HibernateUtil.closeSessionFactory();
    }

    public static void createAllTables() throws IOException {
        createTableLocations();
        createTableCharacters();
        createTableEpisodes();
        createTableCharToEp();
    }

    public static void createTableLocations() {
        int countLocations = 0;

        URL url;
        HttpURLConnection conn = null;
        String line;

        try {
            url = new URL("https://rickandmortyapi.com/api/location");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonData = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
                br.close();

                JsonObject jObject = new Gson().fromJson(jsonData.toString(), JsonObject.class);
                JsonObject info = jObject.getAsJsonObject("info");
                countLocations = info.get("count").getAsInt();
            }

            for (int idAPI = 1; idAPI <= countLocations; idAPI++) {
                url = new URL("https://rickandmortyapi.com/api/location/" + idAPI);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonData = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        jsonData.append(line);
                    }
                    br.close();

                    Location location = new Gson().fromJson(jsonData.toString(), Location.class);
                    MethLocation.createLocation(location);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createTableCharacters() {
        int countCharacters = 0;

        URL url;
        HttpURLConnection conn = null;
        String line;

        try {
            url = new URL("https://rickandmortyapi.com/api/character");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonData = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
                br.close();

                JsonObject jobject = new Gson().fromJson(jsonData.toString(), JsonObject.class);
                JsonObject info = jobject.getAsJsonObject("info");
                countCharacters = info.get("count").getAsInt();
            }

            for (int idAPI = 1; idAPI <= countCharacters; idAPI++) {
                url = new URL("https://rickandmortyapi.com/api/character/" + idAPI);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonData = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        jsonData.append(line);
                    }
                    br.close();

                    Gson gson = new Gson();
                    Character character = gson.fromJson(jsonData.toString(), Character.class);

                    JsonObject jObject = gson.fromJson(jsonData.toString(), JsonObject.class);
                    JsonObject location = jObject.getAsJsonObject("location");
                    String url_location = location.get("url").getAsString();

                    if (url_location.contains("/")) {
                        Location loc = MethLocation.getLocationByUrl(url_location);
                        int id_location = loc.getId_location();
                        character.setId_location(id_location);
                    } else {
                        character.setId_location(-1);
                    }

                    JsonObject origin = jObject.getAsJsonObject("origin");
                    String url_origin = origin.get("url").getAsString();

                    if (url_origin.contains("/")) {
                        Location loc = MethLocation.getLocationByUrl(url_origin);
                        int id_origin = loc.getId_location();
                        character.setId_origin(id_origin);
                    } else {
                        character.setId_origin(-1);
                    }

                    MethCharacter.createCharacter(character);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createTableEpisodes() {
        int countEpisodes = 0;

        URL url;
        HttpURLConnection conn = null;
        String line;

        try {
            url = new URL("https://rickandmortyapi.com/api/episode");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonData = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
                br.close();

                JsonObject jObject = new Gson().fromJson(jsonData.toString(), JsonObject.class);
                JsonObject info = jObject.getAsJsonObject("info");
                countEpisodes = info.get("count").getAsInt();
            }

            for (int idAPI = 1; idAPI <= countEpisodes; idAPI++) {
                url = new URL("https://rickandmortyapi.com/api/episode/" + String.valueOf(idAPI));
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonData = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        jsonData.append(line);
                    }
                    br.close();

                    Episode episode = new Gson().fromJson(jsonData.toString(), Episode.class);
                    MethEpisode.createEpisode(episode);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createTableCharToEp() throws IOException {
        int countCharacters = 0;

        URL url;
        HttpURLConnection conn = null;
        String line;

        try {
            url = new URL("https://rickandmortyapi.com/api/character");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder jsonData = new StringBuilder();

                while ((line = br.readLine()) != null) {
                    jsonData.append(line);
                }
                br.close();

                JsonObject jObject = new Gson().fromJson(jsonData.toString(), JsonObject.class);
                JsonObject info = jObject.getAsJsonObject("info");
                countCharacters = info.get("count").getAsInt();
            }

            for (int idAPIChar = 1; idAPIChar <= countCharacters; idAPIChar++) {
                url = new URL("https://rickandmortyapi.com/api/character/" + idAPIChar);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder jsonData = new StringBuilder();

                    while ((line = br.readLine()) != null) {
                        jsonData.append(line);
                    }
                    br.close();

                    Gson gson = new Gson();
                    JsonObject jObject = gson.fromJson(jsonData.toString(), JsonObject.class);
                    JsonArray jArray = jObject.getAsJsonArray("episode");

                    int id_character = MethCharacter.getCharacterByIdApi(idAPIChar).getId_character();

                    for (int idAPIEp = 0; idAPIEp < jArray.size(); idAPIEp++) {
                        String url_episode = jArray.get(idAPIEp).getAsString();
                        int id_episode = MethEpisode.getEpisodeByUrl(url_episode).getId_episode();

                        CharToEp charToEp = new CharToEp(id_character, id_episode);
                        MethCharToEp.createCharToEp(charToEp);
                    }
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }
}

