#include <WiFi.h>
#include <FirebaseESP32.h>
#include <Wire.h>
#include <LiquidCrystal_I2C.h>
#include <DHT.h>
#include <NTPClient.h>
#include <TimeLib.h>
#include <WiFiUdp.h>


//~~~~~~~~~~~~~~~~~~Wifi dan Fire Base~~~~~~~~~~~~~~~~~~//
//#define WIFI_SSID "Muskin Asikin"
//#define WIFI_PASSWORD "C4tur26TBL"
#define WIFI_SSID "vivo 1915"
#define WIFI_PASSWORD "Sandi971"

#define API_KEY "8f0r5YQveu0e1uxllh0wl6CQv71JMTlgP3IfX4yV"
#define DATABASE_URL "https://sansgarden-275d2-default-rtdb.asia-southeast1.firebasedatabase.app/"
FirebaseData firebaseData;
FirebaseJson pH;
FirebaseJson suhu;
FirebaseJson kelembabanTanah;
FirebaseJson kelembabanUdara;
FirebaseJson content;
FirebaseJson statusPompa;
FirebaseJson statusPenyiraman;


FirebaseAuth auth;
FirebaseConfig config;

String Lokasifbdo = "kebun/versi1/";

//~~~~~~~~~~~~~~~~~~ Timestamp  ~~~~~~~~~~~~~~~~~~//
 #define uS_TO_S_FACTOR 1000000  /// Conversion factor for ms to seconds //

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
//~~~~~~~~~~~~~~~~~~soilmoisture~~~~~~~~~~~~~~~~~~//
  const int soilPin = 32;
  const int AirValue = 2500;  //kondisi kering
  const int WaterValue = 700;  //kondisi basah
  int soilmoisturepercent = 0;
  int soilpercent = 0;
  String kondisiPompa;
  String kondisiPenyiraman;

//~~~~~~~~~~~~~~~~~~DHT 11~~~~~~~~~~~~~~~~~~// 
  #define DHTTYPE DHT11 
  #define DHTPIN 25 //ESP32 ATAU ARDUINO
  DHT dht(DHTPIN, DHTTYPE);

//~~~~~~~~~~~~~~~~~~pH Tanah~~~~~~~~~~~~~~~~~~// 
  #define pHpin 35
  int pHValue = 0; //ADC value from sensor 
  float pHoutputValue = 0.0; //pH value after conversion
  
//~~~~~~~~~~~~~~~~~~Manual via APP~~~~~~~~~~~~~~~~~~~//
  int buttonPompa;
  int buttonCahaya;
  int buttonPendinginan;
  int outputSoil;

  
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
  lcd.print(" Terhubung Internet ");
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

//==================== Soil Moisture ====================//
  pinMode(soilPin, INPUT); 
 
}


//+++++++++++++++++++++Kelmebaban Tanah+++++++++++++++++++++++//
void kelembaban_Tanah(){
  int soilMoistureValue = analogRead(soilPin);
  //Serial.println(soilMoistureValue);
  lcd.setCursor(7,0);
      lcd.print("KT:");
  soilpercent = map(soilMoistureValue, AirValue, WaterValue, 0, 100);
  soilmoisturepercent = soilpercent;
  
  if(soilmoisturepercent >= 100){

    outputSoil = 0; // 0 berarti off
    kondisiPenyiraman = "Tanah Basah";
    
    lcd.setCursor(10,0);
      lcd.print("100%");
    Serial.print("soil:");
    Serial.print("100%");

    int sm = 100;
    kelembabanTanah.set("kelembabanTanah", sm);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
  else if(soilmoisturepercent >= 80 && soilmoisturepercent < 100){
    
    outputSoil = 0; // 0 berarti off
    kondisiPenyiraman = "Tanah masih Basah";
    
    lcd.setCursor(10,0);
      lcd.print(soilmoisturepercent);
    lcd.setCursor(12,0);
      lcd.print("% ");

    Serial.print("soil:");
    Serial.println(soilmoisturepercent);

    kelembabanTanah.set("kelembabanTanah", soilmoisturepercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
   
  else if (soilmoisturepercent >74 && soilmoisturepercent <80){  

    kondisiPenyiraman = "Tanah masih Lembab";
    
      lcd.setCursor(10,0);
        lcd.print(soilmoisturepercent);
      lcd.setCursor(12,0);
        lcd.print("% ");
        Serial.print("soil:");
    Serial.println(soilmoisturepercent);
  }
  else if(soilmoisturepercent >0 && soilmoisturepercent < 75){
    
    outputSoil = 1; // 1 berarti on
    kondisiPenyiraman = "Sedang di SIRAM!";
    
    lcd.setCursor(10,0);
      lcd.print(soilmoisturepercent);
    lcd.setCursor(12,0);
      lcd.print("% ");
  
    Serial.print("soil:");
    Serial.println(soilmoisturepercent);

    kelembabanTanah.set("kelembabanTanah", soilmoisturepercent);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
  }
  else if(soilmoisturepercent <=0){
    
    outputSoil = 1; // 1 berarti on
    kondisiPenyiraman = "Tanah Kering";
    
    lcd.setCursor(10,0);
      lcd.print(" 0% ");
    Serial.print("soil:");
    Serial.println("0 %");

    int smt = 0;
    kelembabanTanah.set("kelembabanTanah", smt);
    Firebase.updateNode(firebaseData,Lokasifbdo,kelembabanTanah);
   }
 
    statusPenyiraman.set("statusPenyiraman", kondisiPenyiraman);
    Firebase.updateNode(firebaseData,Lokasifbdo,statusPenyiraman);
}

//+++++++++++++++++++++++DHT 11+++++++++++++++++++//
void suhudankelembabanUdara(){
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  if (isnan(h) || isnan(t) ) {
   // lcd.setCursor(6,2);
   //   lcd.print(F("DHT ERR!"));
    
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
    lcd.print(F("C "));

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


//+++++++++++++++++++++++Sensor pH Tanah+++++++++++++++++++//
void pHTanah() {
  lcd.setCursor(7,1);
      lcd.print("pH:");
  pHValue = analogRead(pHpin);      //ADC Value sensor pH
  //pHoutputValue = (-0.0346 * pHValue ) +31.23;  //Rumus persamaan Linear
  //pHoutputValue = (0.01833 * pHValue ) -10.33;  //Rumus persamaan Linear 17july2022
  //pHoutputValue = (0.01607 * pHValue ) -8.75;  //Rumus persamaan Linear 17july2022 v2
  pHoutputValue = (0.0214 * pHValue ) -12.714;  //Rumus persamaan Linear 17july2022 v2
  
  Serial.println(pHValue);
  Serial.println("------");
  Serial.println(pHoutputValue);

  lcd.setCursor(10,1);
      lcd.print(pHoutputValue);

  pH.set("pH", pHoutputValue);
  Firebase.updateNode(firebaseData,Lokasifbdo,pH);

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
  lcd.setCursor(7,3);
      lcd.print(timeStamp);
  lcd.setCursor(12,3);
      lcd.print("        ");
  lcd.setCursor(0,3);
      lcd.print("       ");
}


//+++++++++++++++++++++++++Tombol Manual APP+++++++++++++++++++++++++//
 void buttonApp(){
 if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi1/buttonWater")) {
      if (firebaseData.dataType() == "int") {
        buttonPompa = firebaseData.intData();
        //Serial.println(buttonPompa);
      }
    }
    else if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi1/buttonCahaya")) {
      if (firebaseData.dataType() == "int") {
        buttonCahaya = firebaseData.intData();
        //Serial.println(buttonCahaya);
      }
    }
    else if (Firebase.RTDB.getInt(&firebaseData, "/kebun/versi1/buttonPendinginan")) {
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
  buttonApp();                        // output soil = 0 berarti off
  waktu();                            // output soil = 1 berarti on
 
  if(buttonPompa == 2){                               
    if(timeStamp >= "07:00:00" && timeStamp <= "07:00:15" && outputSoil == 1){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON");
        
      kondisiPompa = "Aktif"; 
    }
    else if(timeStamp >= "07:00:00" && timeStamp <= "07:00:15" && outputSoil == 0){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON"); 

      kondisiPompa = "Aktif"; 
    }
     else if(timeStamp >= "07:00:15" && outputSoil == 0){        
      digitalWrite(rly1, HIGH);
      lcd.setCursor(17,1);
        lcd.print(" OFF"); 

      kondisiPompa = "Tidak Aktif"; 
    }
    else if(outputSoil == 1){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON"); 

      kondisiPompa = "Aktif"; 
    }
    else if(outputSoil == 0){
      digitalWrite(rly1, HIGH); 
      lcd.setCursor(17,1);
        lcd.print("OFF");

      kondisiPompa = "Tidak Aktif";
    }
  }
  else if (buttonPompa == 0){       //mati
    digitalWrite(rly1, HIGH);
    lcd.setCursor(17,1);
        lcd.print("OFF");

    kondisiPompa = "Tidak Aktif";
  }
  else if (buttonPompa == 1){       //nyala
    digitalWrite(rly1, LOW);
    lcd.setCursor(17,1);
      lcd.print(" ON");

    kondisiPompa = "Tidak Aktif";
  }
  else{
   if(timeStamp >= "07:00:00" && timeStamp <= "07:00:15" && outputSoil == 1){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON");
        
      kondisiPompa = "Aktif"; 
    }
    else if(timeStamp >= "07:00:00" && timeStamp <= "07:00:15" && outputSoil == 0){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON"); 

      kondisiPompa = "Aktif"; 
    }
     else if(timeStamp >= "07:00:15" && outputSoil == 0){        
      digitalWrite(rly1, HIGH);
      lcd.setCursor(17,1);
        lcd.print(" OFF"); 

      kondisiPompa = "Tidak Aktif"; 
    }
    else if(outputSoil == 1){        
      digitalWrite(rly1, LOW);
      lcd.setCursor(17,1);
        lcd.print(" ON"); 

      kondisiPompa = "Aktif"; 
    }
    else if(outputSoil == 0){
      digitalWrite(rly1, HIGH); 
      lcd.setCursor(17,1);
        lcd.print("OFF");

      kondisiPompa = "Tidak Aktif";
    }
  }
  lcd.setCursor(15,0);
      lcd.print("POMPA");
      
  //Mengirim Ke Firebase    
  statusPompa.set("statusPompa", kondisiPompa);
  Firebase.updateNode(firebaseData,Lokasifbdo,statusPompa);
}


//++++++++++++++++++++++++++++++++Void Loop+++++++++++++++++++++++++++++++//
void loop() {
  buttonApp();
  kelembaban_Tanah();
  suhudankelembabanUdara();
  pHTanah();
  Logic_button_water();
  waktu();
  delay(2000);
} 
