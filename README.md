##Darshak##

Darshak is an application serving **two primary usage**-

1. detecting any suspicious activity of being tracked via cellular network for example Silent SMS
2. to assess security capabilities of your current cellular provider in realtime
 
It has following key features supporting GSM and 3G cellular networks -
* detects and alerts when you receive silent SMS
* invokes after every incoming/outgoing telephony call and SMS event
* displays whether authentication has been performed by the operator
over-the-air and used RAND number
* displays ciphering algorithm used by your operator for GSM ( A5/1,
A5/2, A5/3 ) and 3G networks (UEA0 or UEA1)


##Device and OS version supported##

Samsung Galaxy S3 (GT I9300) 

Android 4.1.2  (If you running other version, please install stock
firmware from [Samsung](http://www.sammobile.com/firmwares/))


##How to install##

1. The Samsung S3 needs to be rooted to run this application. Use [Framroot](http://forum.xda-developers.com/apps/framaroot/root-framaroot-one-click-apk-to-root-t2130276)
application from play store.
2. Download [the apk from here] (https://github.com/darshakframework/darshak/blob/master/apk/Darshak_unsigned.apk). Soon it will be available on Play store.
3. Install [SuperSU](https://play.google.com/store/apps/details?id=eu.chainfire.supersu&hl=de) and give access permission to the Darshak Application.

##Data Upload##

Currently Darshak stores essential data about GSM and 3G networks in the local
database file.  Soon upload functionality will be available to upload database file from the app to central repository. 

To help us improve and add features and build IMSI catcher detector profile, we request you to export database files manualy from the device and mail to darshak@sec.t-labs.tu-berlin.de.
Off course, we make this data avaialbe online for further research in mobile networks.
Disclaimer: This file does not contain your private information such as IMSI, phone number, incoming/outgoing call numbers etc.

##Thanks##

1. Andreas Schildbach for his essential code part.
2. https://github.com/Chainfire/libsuperuser/
3. http://iconmonstr.com
4. http://android-holo-colors.com/
5. Tobias Engel for his awesome https://github.com/2b-as/xgoldmon

##Bugs##

Please report bugs at darshak@sec.t-labs.tu-berlin.de, thank you.
