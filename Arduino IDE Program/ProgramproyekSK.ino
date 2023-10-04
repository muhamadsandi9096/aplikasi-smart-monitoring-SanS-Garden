#include <WiFi.h>
#include <FirebaseESP32.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <DHT.h>
#include <NTPClient.h>
#include <TimeLib.h>
#include <WiFiUdp.h>
#include <Firebase_ESP_Client.h>

//~~~~~~~~~~~~~~~~~~Wifi dan Fire Base~~~~~~~~~~~~~~~~~~//
#define WIFI_SSID "Muskin Asikin"
#define WIFI_PASSWORD "C4tur26TBL"

#define API_KEY "qqqq8f0r5YQveu0e1uxllh0wl6CQv71JMTlgP3IfX4yV"
#define DATABASE_URL "https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/"
#define FIREBASE_PROJECT_ID "sansgarden-275d2"
FirebaseData firebaseData;
FirebaseJson cahaya;
FirebaseJson pH;
FirebaseJson suhu;
FirebaseJson kelembabanTanah;
FirebaseJson kelembabanUdara;
FirebaseJson Cuaca;
FirebaseJson tangkiAir;
FirebaseJson content;

FirebaseAuth auth;
FirebaseConfig config;

String Lokasifbdo = "kebun/versi2/";
String documentPath = "datamonitoring";

//~~~~~~~~~~~~~~~~~~ Timestamp dan Deep Sleep ~~~~~~~~~~~~~~~~~~//
 #define uS_TO_S_FACTOR 1000000  /// Conversion factor for micro seconds to seconds //
  #define TIME_TO_SLEEP  10 // disini mau berapa jam //

  // Define NTP Client to get time
     WiFiUDP ntpUDP;
     NTPClient timeClient(ntpUDP);
  // Variables to save date and time
     String formattedTime;
     String timeStamp;
  
//~~~~~~~~~~~~~~~~~~lcd~~~~~~~~~~~~~~~~~~//
  LiquidCrystal_I2C lcd(0x27, 20, 4);

//~~~~~~~~~~~~~~~~~~Relay~~~~~~~~~~~~~~~~~~//
  const int rly1=5; 
  const int rly2=17; 
  const int rly3=16; 
  const int rly4=4; 
//~~~~~~~~~~~~~~~~~~soilmoisture~~~~~~~~~~~~~~~~~~//
  const int soilPin = 32;
  const int AirValue = 2500;  //kondisi kering
  const int WaterValue = 1000;  //kondisi basah
  int soilmoisturepercent = 0;
  int soilpercent = 0;
//~~~~~~~~~~~~~~~~~~LDR sensor~~~~~~~~~~~~~~~~~~//
  const int ldrPin = 34; 
  const int gelapValue = 2050; 
  const int terangValue = 50;
  int ldrsensorpercent=0;
  int ldrpercent=0;
//~~~~~~~~~~~~~~~~~~DHT 11~~~~~~~~~~~~~~~~~~// 
  #define DHTTYPE DHT11 
  #define DHTPIN 25 //ESP32 ATAU ARDUINO
  DHT dht(DHTPIN, DHTTYPE);
//~~~~~~~~~~~~~~~~Sensor Hujan~~~~~~~~~~~~~~//
  #define hujanpin 26

//~~~~~~~~~~~~~~~~~~Manual via APP~~~~~~~~~~~~~~~~~~~//
  int buttonPompa;
  int buttonCahaya;
  int buttonPendinginan;
  int outputSoil;
  int outputRaindrop;
  int outputLdr;


  
//+++++++++++++++++++++++++++++++++  setup  +++++++++++++++++++++++++++++++++//  
void setup() {
  // put your setup code here, to run once:
 Serial.begin(115200);
  // initialize the lcd
  lcd.init();
  lcd.backlight();

//=====================Firebase dan WIFI=====================//
  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.print("Menghubungkan Wi-Fi");
  lcd.setCursor(0,3);
  lcd.print("Menghubungkan Wi-Fi");
  while (WiFi.status() != WL_CONNECTED)
  {
    Serial.print(".");
    delay(500);
  }
  Serial.println();
  Serial.print("terhubung dengan IP: ");
  Serial.println(WiFi.localIP());
  Serial.println();
  Firebase.begin(DATABASE_URL, API_KEY);
  Firebase.reconnectWiFi(true);

  //Set database read timeout to 1 minute (max 15 minutes)
  Firebase.setReadTimeout(firebaseData, 1000 * 60);
  //tiny, small, medium, large and unlimited.
  //Size and its write timeout e.g. tiny (1s), small (10s), medium (30s) and large (60s).
  Firebase.setwriteSizeLimit(firebaseData, "tiny");
  
  Serial.println("------------------------------------");
  Serial.println("Terhubung........... ");
  lcd.setCursor(0,3);
  lcd.print("Terhubung........... ");
  delay(1000);

//=====================Time Stamp=====================//
  // Initialize a NTPClient to get time
  timeClient.begin();
  // Set offset time in seconds to adjust for your timezone, for example:
  // GMT +1 = 3600
  // GMT +8 = 28800
  // GMT -1 = -3600
  // GMT 0 = 0
  timeClient.setTimeOffset(25200);

  
//=====================dht11=====================//
  dht.begin();
 
    
//=====================relay=====================//
  pinMode(rly1, OUTPUT);
  pinMode(rly2, OUTPUT);
  pinMode(rly3, OUTPUT);
  pinMode(rly4, OUTPUT);

//==================== Soil Moisture ====================//
  pinMode(soilPin, INPUT);
  
//==================== LDR ====================//
  pinMode(ldrPin, INPUT);
  
//==================== Sensor Hujan ====================//
  pinMode(hujanpin, INPUT);
 
}


//+++++++++++++++++++++Kelmebaban Tanah+++++++++++++++++++++++//
void kelembaban_Tanah(){
  int soilMoistureValue = analogRead(soilPin);
  Serial.println(soilMoistureValue);
  soilpercent = map(soilMoistureValue, AirValue, WaterValue, 0, 100);
  soilmoisturepercent = soilpercent;
  
  lcd.setCursor(7,0);
        lcd.print("M:");
  
  if(soilmoisturepercent >= 100){

    outputSoil = 0; // 0 berarti off
    
    lcd.setCursor(9,0);
      lcd.print("100 %");
    Serial.print("soil:");
    Serial.print("100 %");

    int sm = 100;
    kelembabanTanah.set("kelembabanTanah", sm);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
  else if(soilmoisturepercent >= 80 && soilmoisturepercent < 100){
    outputSoil = 0; // 0 berarti off
    lcd.setCursor(9,0);
      lcd.print(soilmoisturepercent);
    lcd.setCursor(11,0);
      lcd.print(" % ");

    Serial.print("soil:");
    Serial.println(soilmoisturepercent);

    kelembabanTanah.set("kelembabanTanah", soilmoisturepercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
  else if(soilmoisturepercent <=0){
    outputSoil = 1; // 1 berarti on
    
    lcd.setCursor(9,0);
      lcd.print(" 0 %  ");
    Serial.print("soil:");
    Serial.println("0 %  ");

    int smt = 0;
    kelembabanTanah.set("kelembabanTanah", smt);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
   }
  else if(soilmoisturepercent >0 && soilmoisturepercent < 60){
    outputSoil = 1; // 1 berarti on
    lcd.setCursor(9,0);
      lcd.print(soilmoisturepercent);
    lcd.setCursor(11,0);
      lcd.print(" % ");
  
    Serial.print("soil:");
    Serial.println(soilmoisturepercent);

    kelembabanTanah.set("kelembabanTanah", soilmoisturepercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
  else if (soilmoisturepercent >59 && soilmoisturepercent <81){  
      lcd.setCursor(9,0);
        lcd.print(soilmoisturepercent);
      lcd.setCursor(11,0);
        lcd.print(" % ");
        Serial.print("soil:");
    Serial.println(soilmoisturepercent);
    kelembabanTanah.set("kelembabanTanah", soilmoisturepercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
}


//+++++++++++++++++ldr sensor+++++++++++++++++++++//
void ldr_cahaya(){
  int ldrStatus = analogRead(ldrPin);
  //Serial.println(ldrStatus);
  ldrsensorpercent = map(ldrStatus, gelapValue, terangValue, 0, 100);
  ldrpercent = ldrsensorpercent;
  
   lcd.setCursor(7,1);
        lcd.print("L:");
  
  if (ldrpercent >= 100) {
   
    outputLdr = 0;            //0 = lampu mati, terang, paranet tutup
    lcd.setCursor(9, 1);
      lcd.print("100 %");
    lcd.setCursor(17, 1); 
      lcd.print("OFF");
      lcd.setCursor(16, 0); 
      lcd.print("LAMP");

    Serial.print("int :");
    Serial.println("100 %");
    Serial.println("LAMPU OFF");

    int LD = 100;
    cahaya.set("cahaya", LD);
    Firebase.updateNode(firebaseData,Lokasifbdo,cahaya);
} 
  else if ( ldrpercent >=60 && ldrpercent < 100){
  
    outputLdr = 0;              //0 = lampu mati, terang, paranet tutup
    lcd.setCursor(9,1);
      lcd.print(ldrpercent);
    lcd.setCursor(11,1);
      lcd.print(" % ");
    lcd.setCursor(17, 1); 
      lcd.print("OFF");
      lcd.setCursor(16, 0); 
      lcd.print("LAMP");

    Serial.print("int :");
    Serial.println(ldrpercent);
    Serial.println("LAMPU OFF");

    cahaya.set("cahaya", ldrpercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,cahaya);
  }
  else if (ldrpercent <= 0){
      outputLdr = 1;             //1 = lampu nyala, gelap, paranet buka
      lcd.setCursor(9,1);
        lcd.print("0 %  ");
      lcd.setCursor(17, 1); 
        lcd.print("ON ");
        lcd.setCursor(16, 0); 
        lcd.print("LAMP");

      Serial.print("int :");
      Serial.println(" 0 %  ");
      Serial.println("LAMPU ON ");

      int LD2 = 0;
      cahaya.set("cahaya", LD2);
      Firebase.updateNode(firebaseData,Lokasifbdo,cahaya);
  }
  else if (ldrpercent >0 && ldrpercent <40){
     
      outputLdr = 1;           //1 = lampu nyala, gelap, paranet buka
      lcd.setCursor(9,1);
        lcd.print(ldrpercent);
      lcd.setCursor(11,1);
        lcd.print(" % ");
      lcd.setCursor(17, 1); 
        lcd.print("ON ");
        lcd.setCursor(16, 0); 
        lcd.print("LAMP");

      Serial.print("int :");
      Serial.println(ldrpercent);
      Serial.println("LAMPU ON ");

      cahaya.set("cahaya", ldrpercent);
      Firebase.updateNode(firebaseData,Lokasifbdo,cahaya);
  }
  else if (ldrpercent >=40 && ldrpercent <60){
     
      lcd.setCursor(9,1);
        lcd.print(ldrpercent);
      lcd.setCursor(11,1);
        lcd.print(" % ");
      lcd.setCursor(17, 1); 
        lcd.print("ON ");
        lcd.setCursor(16, 0); 
        lcd.print("LAMP");

      Serial.print("int :");
      Serial.println(ldrpercent);
      Serial.println("LAMPU ON ");

      cahaya.set("cahaya", ldrpercent);
      Firebase.updateNode(firebaseData,Lokasifbdo,cahaya);
      }
}

//+++++++++++++++++++++++DHT 11+++++++++++++++++++//
void suhudankelembabanUdara(){
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  if (isnan(h) || isnan(t) ) {
    lcd.setCursor(6,2);
      lcd.print(F("DHT ERR!"));
    
    Serial.print(F("Failed to read from"));
    Serial.print(F("DHT ERR!"));
   } else  {
      lcd.setCursor(0,0);
    lcd.print(F("H:"));
  lcd.setCursor(2,0);
    lcd.print(h);
  lcd.setCursor(4,0);
    lcd.write(B00100101);
    lcd.setCursor(5,0);
    lcd.print("  ");
  lcd.setCursor(0,1);
    lcd.print(F("T:"));  
  lcd.setCursor(2,1);
    lcd.print(t);
  lcd.setCursor(4,1);
    lcd.write(B11011111);
  lcd.setCursor(5,1);
    lcd.print(F("C  "));

  Serial.print(F("Humi:"));
  Serial.println(h);
  Serial.print(F("Temp:"));
  Serial.println(t);
  suhu.set("suhu", t);
  Firebase.updateNode(firebaseData,Lokasifbdo,suhu);
  
  kelembabanUdara.set("kelembabanUdara", h);
  Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanUdara);
  }
    
}

//++++++++++++++++++++Sensor Hujan++++++++++++++++++++++++//
void Sensor_hujan(){
  int cuacaValue = digitalRead(hujanpin);
  if (cuacaValue == 1){
    outputRaindrop = 0;
    String cuaca = "Cerah";
    lcd.setCursor(0,3);
    lcd.print("Cuaca");
    lcd.setCursor(6,3);
    lcd.print(cuaca);
    
    Cuaca.set("Cuaca", cuaca);
    Firebase.updateNode(firebaseData,Lokasifbdo,Cuaca);
  }else {
    outputRaindrop = 1;
    String cuaca = "hujan";
    lcd.setCursor(0,3);
    lcd.print("Cuaca");
    lcd.setCursor(6,3);
    lcd.print(cuaca);
    
    Cuaca.set("Cuaca", cuaca);
    Firebase.updateNode(firebaseData,Lokasifbdo,Cuaca);
  }
}

//+++++++++++++++++++++++++Tombol Manual APP+++++++++++++++++++++++++//
 void buttonApp (){
 if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi2/buttonWater")) {
      if (firebaseData.dataType() == "int") {
        buttonPompa = firebaseData.intData();
        //Serial.println(buttonPompa);
      }
    }
    else if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi2/buttonCahaya")) {
      if (firebaseData.dataType() == "int") {
        buttonCahaya = firebaseData.intData();
        //Serial.println(buttonCahaya);
      }
    }
    else if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi2/buttonPendinginan")) {
      if (firebaseData.dataType() == "int") {
        buttonPendinginan = firebaseData.intData();
        //Serial.println(buttonPendinginan);
      }
    }
    else {
      Serial.println(firebaseData.errorReason());
    }
 }



//+++++++++++++++++++++++++++++++Logic Button+++++++++++++++++++//
void Logic_button_water(){
  kelembaban_Tanah();
  Sensor_hujan();
  buttonApp ();
  
  if(buttonPompa == 2){                               // 0 berarti off atau tidak ada atau cerah 
    if(outputRaindrop == 0 && outputSoil == 1 || timeStamp >= "10:00:00" && timeStamp <= "10:00:10"){        // 1 berarti on atau ada atau hujan 
      digitalWrite(rly3, LOW); 
    }
    else if(outputRaindrop == 0 && outputSoil == 0 || timeStamp >= "10:00:00" && timeStamp <= "10:00:10"){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 1 && timeStamp >= "10:00:00" && timeStamp <= "10:00:10"){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 0 && timeStamp >= "10:00:00" && timeStamp <= "10:00:10"){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 1){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 0){
    digitalWrite(rly3, HIGH); 
    }
  }
  else if (buttonPompa == 0){       //mati
    digitalWrite(rly3, HIGH);
  }
  else if (buttonPompa == 1){       //nyala
    digitalWrite(rly3, LOW);
  }
  else{
    if(outputRaindrop == 0 && outputSoil == 1){        // 1 berarti on atau ada atau hujan 
      digitalWrite(rly3, LOW);                        // 0 berarti off atau tidak ada atau cerah
    }
    else if(outputRaindrop == 0 && outputSoil == 0){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 1){
    digitalWrite(rly3, HIGH); 
    }
    else if(outputRaindrop == 1 && outputSoil == 0){
    digitalWrite(rly3, HIGH); 
    }
  }
}

void Logic_button_cahaya(){
 ldr_cahaya();
 buttonApp ();
 
  if(buttonCahaya == 2){                              
    if(outputLdr == 1){                                 //1 = lampu nyala, gelap, paranet buka
      digitalWrite(rly2, LOW); 
    }
    else if(outputLdr == 0){
    digitalWrite(rly2, HIGH); 
    }
  }
  else if (buttonCahaya == 0){       //mati
    digitalWrite(rly2, HIGH);
  }
  else if (buttonCahaya == 1){       //nyala
    digitalWrite(rly2, LOW);
  } 
  else{                             
    if(outputLdr == 1){                                 //1 = lampu nyala, gelap, paranet buka
      digitalWrite(rly2, LOW); 
    }
    else if(outputLdr == 0){
    digitalWrite(rly2, HIGH); 
    }
  }
}


//++++++++++++++++++++++ WAKTU +++++++++++++++++++//
void waktu() {
  while(!timeClient.update()) {
    timeClient.forceUpdate();
  }
  formattedTime = timeClient.getFormattedTime();
  int splitT = formattedTime.indexOf("T");
  timeStamp = formattedTime.substring(splitT+1, formattedTime.length());
  Serial.print("HOUR: ");
  Serial.println(timeStamp);
}

//++++++++++++++++++++++++++++++++Void Loop+++++++++++++++++++++++++++++++//
void loop() {
  buttonApp();
  kelembaban_Tanah();
  ldr_cahaya();
  suhudankelembabanUdara();
  Sensor_hujan();
  Logic_button_water();
  Logic_button_cahaya();
  waktu();
  
  
} 
