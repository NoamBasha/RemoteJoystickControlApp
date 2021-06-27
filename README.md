# Remote Joystick Control App

## Introduction
This project is an Android application for remote control, which was developed in Java using Android Studio.  
The user opens the application and sees the connection layout and the control layout.  
The user insert the desired ip and port and connect to the flight simulator.   
The user can control the plane via the joystick and seekbars.  

## Preperation:
### Server Side:
1. Download the 'FlightGear' application _here_  
2. Open flight gear and go to settings and under the additnal settings write the following line:  
--telnet=socket,in,10,127.0.0.1,6400,tcp  
(The number 6400 represnsts the port number. You may choose another port number).   
3. Press the 'fly!' button, press on the 'Cessna C172P' on the tool bar and press on 'Autostart'  
4. You may change the view by pressing 'v' on your keyboard.  

### Client Side:
1. Open android studio
2. Clone the project
3. Run the application.
4. Enter your IP address and the port youv'e entered on the server side.
5. Fly the plane :)
	
## Architecture:
The architecture of the prjiect is based onm the MVVM archetoure:
* Model:  
FGModel – Connects to the server and sends the values he receives
* View Model:  
ViewModel – Connects between the Model and the view.
* View:  
MainActivity – Binded to the application view, sends instructions to the view model accorind to the user.   
Joystick – Standalone class for a joystick (the be transferred to other prjoects).

## Functionality
To fly the plane the user must control 4 components:
* Throttle - the amount of fuel that is provided to the engine.
* Rudder - the fin of the plane.
* Elevator - controls the oitch of the plane.
* Aileron - controls the roll of the plane.

__Insert PHOTO__

## UML

__UML__

## Video

__VIDEO__

## Developers
* Noam Basha
* Hanna Sofer
