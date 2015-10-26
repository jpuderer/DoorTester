DoorTester
=====================

Overview
--------------

This project contains sample code for a project of mine, where I reversed engineered
an electronic door lock, and modified it so I could control over Bluetooth-LE using 
a Bluno Beetle (Arduino compatible) board.

The Android source is largely copied verbatim from the BlunoBasicDemo, and was used to quickly
test communication between an Android device and the electronic door lock. You can find the 
source code for the BlunoBasicDemo here:
https://github.com/DFRobot/BlunoBasicDemo

I don't endorse quality of the code in anyway, in fact I consider the quality to be pretty terrible.
This app was just to get used to test the hardware.

You can find more details (as well as a final version of the application) here:
http://www.jpuderer.net/2015/09/my-fancy-bluetooth-le-operated-door-lock.html

This project contains both the Android application source, and the Arduino source.


Getting Started
---------------

This sample uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

The source for the Bluno Beetle is in the Arduino folder, and can be compiled
and installed from the Arduino IDE (using board type: Arduino Uno).

License
-------

I'm normally pretty permissive with my licenses, but the orginal BlunoBasicDemo is 
GPLv3 (with some portions APLv2), so then so is this.

