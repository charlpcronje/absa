# absa
ABSA Weather App

## Adroid Version 6.0 Marshmalow
I'm choosing this widely supported by 84.9% of devices and still compatable with the UI functionality I wwnt to use

## API `OpenWeatherMap` for Data
I chose this one because I had limited time and it was at the top, I presume there was no wrong answer there

### Data Extracted From the API
I extracted the following data classes from the API
```
WeatherData  {
  base: String,
  clouds: Clouds,
  cod: Int,
  coord: Coord,
  Int,
  id: Int,
  main: Main,
  name: String,
  sys: Sys,
  timezone: Int,
  visibility: Int,
  weather: List<Weather>,
  wind: Wind
}
Coord {
  lat: Double,
  lon: Double
}

Clouds {
  all: Int
}

Main {
  feels_like: String,
  humidity: String,
  pressure: String,
  temp: String,
  temp_max: String,
  temp_min: Strin
}

Sys {
  country: String,
  id: Int,
  sunrise: Int,
  sunset: Int,
  type: Int
}

List<Weather> {
  description: String,
  icon: String,
  id: Int,
  main: String
}

Wind {
  deg: Int,
  speed: String
}
```

## Retrofit#
> I used Retrofit as a dependecy for fetching the data from `OpenWeatherMap`
`Retrofit` is lightweight, open source and easy to use

## GeoLocation 
I used Android's `LOCATION_SERVICE` for locating the device to get the current city where the device is located

## Unit Testing
I didn't have much time for this so far, I could only start early this morning. I am doing the unit testing now

## Proguard
I didn't have much time for this so far, I could only start early this morning. I am configuring progaurd asap
