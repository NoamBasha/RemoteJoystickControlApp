# Remote Joystick Control App

## Introduction
This project is an Android application for remote control, which was developed in Java using Android Studio.  
The user opens the application and sees the connection layout and the control layout.  
The user insert the desired IP and PORT and connect to the flight simulator.   
The user can control the plane via the joystick and seekbars.  

## Preperation:
### Server Side:
1. Download the 'FlightGear' application.  
2. Open the 'FlightGear' application and go to settings -> additional settings and write the following line:  
--telnet=socket,in,10,127.0.0.1,6400,tcp  
(The number 6400 represents the PORT number. You may choose another PORT number).     
3. Press the 'fly!' button then press on Toolbar -> 'Cessna C172P' -> 'Autostart'.  
4. You may change the view by pressing 'v' on your keyboard.  

### Client Side:
1. Open Android Studio.
2. Clone the project.
3. Run the application.
4. Enter your IP address and the PORT youv'e entered on the server side.
5. Fly the plane :)
	
## Architecture:
The architecture of the project is based on the MVVM architecture:
* Model:  
FGPlayer – Connects to the server and sends the values it receives
* View Model:  
ViewModel – Connects between the Model and the View.
* View:  
MainActivity – Binded to the application's view. Sends instructions to the view model according to the user.   
Joystick – Standalone class for a joystick (that can be transferred to other prjoects).

## Functionality
To fly the plane the user must control 4 components:
* Throttle - the amount of fuel that is provided to the engine.
* Rudder - the fin of the plane.
* Elevator - controls the pitch of the plane.
* Aileron - controls the roll of the plane.

![](/app/src/main/java/com/example/remotejoystickcontrolapp/RemoteJoystickControlAppPhoto.jpeg)

## UML

![](/app/src/main/java/com/example/remotejoystickcontrolapp/RemoteJoystickControlAppUML.jpeg)

## Video

__VIDEO__

## Developers
* Noam Basha
* Hanna Sofer
