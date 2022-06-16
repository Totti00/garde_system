#include "TempSensor.h"
#include "Arduino.h"

#define VCC ((float)5)

TempSensor::TempSensor(int p) : pin(p){
} 
  
int TempSensor::getTemperature(){
  int value = analogRead(pin);
  float voltage = (value / 1024.0) * 5.0;  
  float temperature = (voltage - .5) * 100;
  int value_mappato = map(temperature, 0, 100, 0, 5);
  return value_mappato;
}
