package com.example.simpleweatherapp.entity

class Weather(var name: String? = null, var country: String? = null,var weather: String? = null,
              var desc: String? = null, var temp: Double? = 0.0, var feelslike: Double? = 0.0, var humidity: Double = 0.0,
              var visibility: Double? = 0.0, var rain: Double? = 0.0, var wind: Double? = 0.0,) {
    init{

        this.name = name;
        this.country = country;
        this.weather= weather;
        this.desc= desc;
        this.temp= temp;
        this.feelslike = feelslike;
        this.humidity = humidity;
        this.visibility = visibility;
        this.rain = rain;
        this.wind = wind;
    }
}


