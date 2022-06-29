int arrayPinLEDS[] = {10, 11, 12, 13};

void setup()
{
  initAllLeds();
  Serial.begin(9600);
  Serial.println("Start");
}

void initAllLeds()
{
  for (int i = 0; i < sizeof(arrayPinLEDS) / sizeof(int); i++)
  {
    pinMode(arrayPinLEDS[i], OUTPUT);
  }
}

void highAllLeds()
{
  for (int i = 0; i < sizeof(arrayPinLEDS) / sizeof(int); i++)
  {
    digitalWrite(arrayPinLEDS[i], HIGH);
  }
}

void lowAllLeds()
{
  for (int i = 0; i < sizeof(arrayPinLEDS) / sizeof(int); i++)
  {
    digitalWrite(arrayPinLEDS[i], LOW);
  }
}

void loop()
{
  /*if (Serial.available() > 0)
  {
    Serial.println("Android: " + Serial.readString());
  }*/

  highAllLeds();
  delay(1000);
  lowAllLeds();
  delay(1000);
}
