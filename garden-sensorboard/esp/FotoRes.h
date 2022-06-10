#ifndef __FOTORES_SENSOR__
#define __FOTORES_SENSOR__


class FotoRes {
public:
  FotoRes(int p);
  int getValue();
private:
  int pin;
};


#endif
