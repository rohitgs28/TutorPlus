# TutorPlus


For Gooogle login:


1.For running the app which uses the Google services login,make sure the google
play services API is installed. If it is already installed make sure that it is 
the latest version.


2.Generate the Json file


Use the Java keytool to get the SHA-1 fingerprint
 
 
for Windows,

keytool -list -v -keystore "%USERPROFILE%\\.android\\debug.keystore" -alias
androiddebugkey -storepass android -keypass android



for Mac,

keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey
-storepass android -keypass android


use this link to get the json file <https://developers.google.com/identity/sign-in/android/start-integrating>


enter the package name as package com.dal.group7.tutorplus.ui.activities;


paste the SHA1 fingerprint and click ENABLE google sign in. Generate the configuration file to download our google-services.json




For Running the app:

Please follow the following steps

1)Clean Build


2)Rebuild


3)Build APK



