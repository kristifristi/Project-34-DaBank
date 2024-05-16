// variables
const int rundelay 0;
const int testdelay 250;  //used before the body code of the testbox function

const int cycledelay 0;
//the components
#define countBoxes 4
const int CB = countBoxes;  //CountBoxes

int ir[countBoxes] = { 6, 8, 19, 12 };
int motor[countBoxes] = { 7, 8, 10, 13 };

int demands[countBoxes];  // filled in setup, auto increments
bool flag[countBoxes];    // filled in setup, filled with 0/false

int storage[countBoxes][2] = {
  //{Amount of cards, denomination},
  { 0, 1 },   // box 0
  { 0, 5 },   // box 1
  { 0, 10 },  // box 2
  { 0, 20 }   // box 3


};

void setup() {
  // setup the connection to the boxes.
  for (int i; i < CB; i++) {
    pinMode(ir[i], INPUT);
  }
  for (int i; i < CB; i++) {
    pinMode(motor[i], OUTPUT);
  }

  // fill the flags with zero, and the demands with an auto increment.
  for (int i; i < CB;) {
    demands[i] = ++i;
    flag[i] = 0;
  }

  Serial.begin(9600);
  Serial.println("TXT: \"Setup complete\"");
}



void loop() {
}



void runBoxes() {  // with a for loop cycle through all availible boxes.

  if (rundelay > 0)
    delay(rundelay);

  for (int i; i < CB; i++) {
    //start procedure

    if (digitalRead(ir[i]) == 0) {  // When a card is blocking the IR sensor


      if (demands[i] > 0) {            // And we are demanding a card from B0
        digitalWrite(motor[i], HIGH);  // tell the motor to push

        if (flag[i] == 0) {  // when a card starts being expelled we run this while the flag is low, then raise the flag.
          // decrement the storage and demands.
          storage[i][0]--;
          demands[i]--;
          // raise the flag, blocking this until the procedure has finished a cycle.
          flag[i] = 1;
        }

      } else {
        digitalWrite(motor[i], LOW);  // if we do not want to expel a card, stop the motor.
      }



    } else {                         // when there is no card infront of the IR
      digitalWrite(motor[i], HIGH);  // push a front of it

      flag[i] = 0;  // when no card is infront of the IR sensor we lower the flag, marking the end of a procedure.
    }
    //end procedure
  }
}

//

void testb(int box) {

  if (testdelay < 0;)
    delay(testdelay);

  //start procedure
  if (digitalRead(ir[box]) == 0) {  // When a card is blocking the IR sensor


    if (demands[box] > 0) {          // And we are demanding a card from B0
      digitalWrite(motor[0], HIGH);  // tell the motor to push

      if (flag[box] == 0) {  // when a card starts being expelled we run this while the flag is low, then raise the flag.
        // decrement the storage and demands
        storage[box][0]--;
        demands[box]--;

        flag[box] = 1;

        Serial.println("storage updated, flag raised");
      }

    } else {
      digitalWrite(motor[box], LOW);  // if we are not demanding a card do not expel one.

      Serial.println("card infront of sensor, cease");
    }



  } else {                           // when there is no card infront of the IR
    digitalWrite(motor[box], HIGH);  // push a front of it
    if (flag[box] != 0)
      Serial.print("lowering flag");

    flag[box] = 0;  // when no card is infront of the IR sensor we lower the flag

    Serial.println("pushing card to sensor");
  }
  //end procedure
}