#include "FotoRes.h"
#include "Arduino.h"

FotoRes::FotoRes(int p) : pin(p){
} 
  
int FotoRes::getValue(){
  int value = analogRead(pin);
  int value_mappato = map(value, 0, 1023, 0, 8);
  return value_mappato;
}
