#include <SoftwareSerial.h>
#include <avr/power.h>
#include <avr/sleep.h>

SoftwareSerial doorSerial(2, 4); // RX, TX

void setup() {
    // initialiaze the real (UART) Serial
    Serial.begin(115200);
    // initialize the SoftwareSerial port
    doorSerial.begin(9600);
    // Put TX in a high impedance state when not sending    
    pinMode(4, INPUT);
}

void writeDoorCommand(uint8_t command) {
    pinMode(4, OUTPUT);
    digitalWrite(4, 0);
    delay(30);
    digitalWrite(4, 1);
    delayMicroseconds(1200);
    doorSerial.write((uint8_t)command);
    pinMode(4, INPUT);
}

// Put the device into sleep mode
void sleep() {
    set_sleep_mode(SLEEP_MODE_IDLE);
    
    // Set Power Reduction register to disable timer (used by SoftSerial)
    PRR = PRR | 0b00100000;
    
    power_adc_disable();
    power_spi_disable();
    power_timer0_disable();
    power_timer1_disable();
    power_timer2_disable();
    power_twi_disable();

    // Enter sleep mode
    sleep_enable();
    sleep_mode();
    
    // Return from sleep
    sleep_disable();
    
    // Re-enable timer
    PRR = PRR & 0b00000000;
    
    power_all_enable();
}

void loop() {
    bool canSleep = true;
    while (doorSerial.available()) {
        canSleep = false;
        // send what has been received
        Serial.print("0x");
        Serial.println(doorSerial.read(), HEX); 
    }
    while (Serial.available()) {
        canSleep = false;
        // send what has been received
        writeDoorCommand(Serial.read());
    }
    
    // Sleep if there is no activity on the serial port
    if (canSleep) sleep();
    
    // Add a delay to avoid characters being sent too quickly to the door lock
    delay(100);
}


