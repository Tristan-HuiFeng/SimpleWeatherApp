package com.example.simpleweatherapp

import android.content.Context
import android.util.Log
import com.example.simpleweatherapp.entity.Weather
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class NetworkRequest {



    companion object {
        fun getResponseFromHttpUrl(url: URL): String? {
            Log.d("TESTURL3", url.toString())
            val urlConnection = url.openConnection() as HttpURLConnection

            val input = urlConnection.inputStream

            val scanner = Scanner(input)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if (hasInput) {
                scanner.next()
            } else {
                null
            }

            urlConnection.disconnect()


        }

        fun getJSONWeather(input: String): Weather {

            val json = JSONObject(input)

            val name: String;
            val country: String;
            val weather: String;
            val desc: String;
            val temp: Double;
            val feelslike: Double;
            val humidity: Double;
            val visibility: Double;
            val rain: Double;
            val wind: Double;


            name = json.getString("name");
            country = json.getJSONObject("sys").getString("country");

            val weatherObj = json.getJSONArray("weather").getJSONObject(0)

            weather = weatherObj.getString("main");
            desc  = weatherObj.getString("description");

            val temperatureObj = json.getJSONObject("main")

            temp = temperatureObj.getDouble("temp").minus(273.15);
            feelslike = temperatureObj.getDouble("feels_like").minus(273.15);
            humidity = temperatureObj.getDouble("humidity");


            visibility = json.getDouble("visibility");
            rain = json.getJSONObject("rain").getDouble("1h");
            wind = json.getJSONObject("wind").getDouble("speed");



            return Weather(name, country, weather, desc, temp, feelslike, humidity, visibility, rain, wind)
        }
    }

}