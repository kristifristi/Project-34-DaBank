const int motor1b = 8;
const int motor1a = 9;

const int ir1 = 7;

int startTimer = 5;
int timer = 0;
int time = 0;

void setup() {
  // put your setup code here, to run once:
  pinMode(motor1a, OUTPUT);
  pinMode(motor1b, OUTPUT);
  pinMode(ir1, INPUT);
  startTimer = millis();

  bool flag = 0;
}

void loop() {
  digitalWrite(motor1a, HIGH);
  digitalWrite(motor1b, LOW);

  startTimer = millis();

  while (digitalRead(ir1) == 0) {
    if(flag = 0){
      
    }
    timer = millis();
    time = startTimer - timer;
    delay(800);
    digitalWrite(motor1a, LOW);
  }

  // put your main code here, to run repeatedly:
}
