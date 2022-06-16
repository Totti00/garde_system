#ifndef __TEMP_SENSOR_TMP__
#define __TEMP_SENSOR_TMP__


class TempSensor {
public:
  TempSensor(int p);
  int getTemperature();
private:
  int pin;
};


#endif
