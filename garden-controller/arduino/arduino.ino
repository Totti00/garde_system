#define PIN_SERVO 7
#define PIN_LED_ONE 6
#define PIN_LED_TWO 5
#define PIN_LED_THREE 4
#define PIN_LED_FOUR 3
#define PERIOD 250
#define WRITE_PERIOD 1000

#include <Servo.h>
#include <SoftwareSerial.h>
#include "Led.h"
#include "BlinkTask.h"

enum {READ, ALARM, CLOSE, MANUAL} state;

Led* led_one;
Led* led_two;
Led* led_three;
Led* led_four;

Servo servo;
BlinkTask blinkTaskThree;
BlinkTask blinkTaskFour;
SoftwareSerial bt(2, 3); //rx, tx

String buffer = "";
char terminator = ' ';
bool blinkFlag = false;
bool irrigationSystem = false;
bool manualFlag = false;
bool turn = false;

int m = 0;    // velocità impianto preso manualmente
int f = 0;    // valore fotoresistenza
int t = 0;    // valore temperatura
int a = 0;    // velocità impianto preso automaticamente
String s = "";

void setup() {
  Serial.begin(9600);
  bt.begin(9600);

  servo.attach(PIN_SERVO);
  setServo(0);
  delay(1000);
  led_one = new Led(PIN_LED_ONE);
  led_two = new Led(PIN_LED_TWO);
  led_three = new Led(PIN_LED_THREE);
  led_four = new Led(PIN_LED_FOUR);
  blinkTaskThree.init(PERIOD, PERIOD, led_three);
  blinkTaskFour.init(PERIOD, PERIOD, led_four);
  
}

void loop() {
  step();
}

void step() {
  blink();
  switch (state) {
    case READ:
      read();
      parse();
      write();
      break;
    case ALARM:
      alarm();
      break;
    case CLOSE:
      close();
      break;
    case MANUAL:
      manual();
      break;
  }
}

// STATI 

void read() {
  // leggo tutti i caratteri che mi arrivano in sequenza e li appendo fino al
  // terminatore escluso
  bool end = false;
  if (Serial.available() > 0) {
    
    Serial.flush();
    buffer.concat(Serial.readStringUntil(terminator));
    
  } else if (bt.available() > 0) {

    bt.flush();
    buffer.concat(bt.readStringUntil(terminator));
    
  }
}

void parse() {
  if (buffer.length() > 0) {
    if (buffer[0] == 'f') {
      f = buffer.substring(1).toInt();
      Serial.println(f);
    } else if (buffer[0] == 't') {
      t = buffer.substring(1).toInt();
      Serial.println(t);
    } else if (buffer[0] == 's') {
      s = buffer.substring(1);
      Serial.println(s);
    } else if (buffer[0] == 'm') {
      m = buffer.substring(1).toInt();
    } else if (buffer[0] == 'M' && f > 0) {
      manualFlag = true;
    } else if (buffer[0] == 'A' && manualFlag) {
      manualFlag = false;
    }
    buffer = "";
  }

  if (f < 2) {
    irrigationSystem = true;
  }

  if (t == 5) {
    state = ALARM;
  } else if (t == 0) {
    state = CLOSE;
  } else if (manualFlag) {
    state = MANUAL;
  }
}

void write() {
  static unsigned long int tprev = 0;
  int r = millis();
  if (r - tprev >= WRITE_PERIOD) {
    String tmp = String(s + " " + String(t) + " " + String(f));
    tprev = r;
  }

  if (manualFlag) {
    Serial.write("MANUALE");
  }
}

void alarm() {
  setServo(0);
  blinkFlag = true;

  state = READ;
}

void close() {
  if(irrigationSystem) {
      setServo(t);
  }
  
  blinkFlag = false;
  manualFlag = false;
  
  if (f < 5) {
    led_one->switchOn();
    led_two->switchOn();
    led_three->switchOn();        //bisogna mapparli in 5 valori (0, 4)
    led_four->switchOn();        //bisogna mapparli in 5 valori (0, 4)
  }


  state = READ;
}

void manual() {
  blinkFlag = false;
  if (f < 5) {
    led_one->switchOn();
    led_two->switchOn();
    led_three->switchOn();        //bisogna mapparli in 5 valori (0, 4)
    led_four->switchOn();        //bisogna mapparli in 5 valori (0, 4)
  }

  if(irrigationSystem) {
      setServo(t);
  }

  state = READ;
}

// FUNZIONI

void blink() {
  static unsigned long int tprev = 0;
  if (blinkFlag) {
    int r = millis();
    if (r - tprev >= PERIOD) {
      blinkTaskThree.updateAndCheckTime();
      blinkTaskFour.updateAndCheckTime();
      tprev = r;
    }
  }
}

void setServo(int value) {
  int tmp = map(value, 0, 5, 5, 175);
  if (servo.read() != tmp) {
    servo.write(tmp);
  }
}

  
