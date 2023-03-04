package com.example.simpleweatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import java.net.URL


class MainActivity : AppCompatActivity() {

    var url = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={APIkey}"
    private lateinit var locationManager: LocationManager;
    private var lon: Double = 0.0;
    private var lat: Double = 0.0;

    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val key: String = BuildConfig.KEY;
        url = url.replace("{APIkey}", key);


        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                lat = location!!.latitude
                lon = location.longitude
                url = url.replace("{lon}", lon.toString());
                url = url.replace("{lat}", lat.toString());

                weatherJob()
        }








    }

    fun weatherJob() {
        CoroutineScope(Job() + Dispatchers.IO).async() {
            delay(1000)
            try {
                val result = NetworkRequest.getResponseFromHttpUrl(URL(url))
                val weatherObj = result?.let { NetworkRequest.getJSONWeather(it) }

                runOnUiThread {
                    val tvLocationName = findViewById<TextView>(R.id.tv_locationName)
                    tvLocationName.setText(weatherObj!!.name);

                    val tvLocation = findViewById<TextView>(R.id.tv_location)
                    tvLocation.setText(weatherObj.country);
                    val tvTemp = findViewById<TextView>(R.id.tv_temperature)
                    val temp =
                        weatherObj.temp.toString() + " feels like " + weatherObj.feelslike.toString();
                    tvTemp.setText(temp);

                    val tvWeather = findViewById<TextView>(R.id.tv_weather)
                    tvWeather.setText(weatherObj.weather);

                    val tvDesc = findViewById<TextView>(R.id.tv_desc)
                    tvDesc.setText(weatherObj.desc);

                    val tvWind = findViewById<TextView>(R.id.tv_windSpeed)
                    tvWind.setText(weatherObj.wind.toString());

                    val tvVisibility = findViewById<TextView>(R.id.tv_visibility)
                    tvVisibility.setText(weatherObj.visibility.toString());

                    val tvRain = findViewById<TextView>(R.id.tv_rain)
                    tvRain.setText(weatherObj.rain.toString());

                    val tvHumidity = findViewById<TextView>(R.id.tv_humidity)
                    tvHumidity.setText(weatherObj.humidity.toString());

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




}