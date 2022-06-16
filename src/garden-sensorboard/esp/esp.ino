#define PERIOD 250

#include <ArduinoJson.h>
#include <WiFiClient.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include "Led.h"
#include "BlinkTask.h"
#include "TempSensor.h"
#include "FotoRes.h"

const int led_pin = 4;
const int temp_pin = 5;
const int fotores_pin = 6;

Led led(led_pin);
BlinkTask blinkTask;
TempSensor pTemp(temp_pin);
FotoRes fotoRes(fotores_pin);

DynamicJsonDocument json(1024);
char jsonString[100];

int valueTemp = 0;
int valueFotores = 0;

enum {NORMALE, ALLARME} state;
String stateName[3] = {"NORMALE", "ALLARME"};

const char* ssid = "FRITZ!Box 7530 YD";
const char* password = "78371902711764011878";
const char* serverName = "http://192.168.178.36:8080/api/data/";

void setup() {
  Serial.begin(9600);
  while(!Serial){};
  Serial.println(String("\nConnecting to network ") + ssid);

  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }
  Serial.println("");
  Serial.println(String("Connected to network ") + ssid);
  // put your setup code here, to run once:
  state = NORMALE;
  blinkTask.init(PERIOD, PERIOD, &led);

}

void loop() {
  step();

}

void step() {
  valueTemp = pTemp.getTemperature();
  valueFotores = fotoRes.getValue();
  switch (state) {
    case NORMALE:
      normale();
      break;
    case ALLARME:
      alarm();
      break;
  }
}

void normale() {
  led.switchOn();
  sendData();
  if (valueTemp == 5) {
    state = ALLARME;
  }
}

void alarm() {
  led.switchOff();
  sendData();
}

void sendData() {
  static unsigned long int tprev = 0;

  double period;
  if (state = ALLARME) period = 1000;

  int t = millis();
  if (t - tprev >= period) {
    populateJson();
    sendPostRequest();

    t = tprev;
  }
}

void sendPostRequest() {
  WiFiClient wifiClient;
  HTTPClient http;
  http.begin(wifiClient, serverName);
  http.addHeader("Content-Type", "application/json");
  Serial.println(http.POST(jsonString));
  http.end();
}

void populateJson() {
  json["tempLivello"] = valueTemp;
  json["fotoresLivello"] = valueFotores;
  json["stato"] = stateName[state];

  serializeJson(json, jsonString);
  Serial.println(jsonString);
}
