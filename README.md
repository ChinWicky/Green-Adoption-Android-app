# Green Adoption-Android-app
This repository contains the source code for the Green Adoption Android app.  

You will need JDK 1.8+ installed to work with it. Gradle, Android SDK, and project dependencies will be downloaded automatically.

## Introduction
- My project is a graduation design project from my undergraduate studies. 
- The purpose of this project is to provide a platform for farmers to sell their agricultural products directly to consumers. 
- This platform can help farmers better process their products, and also provide urban residents with healthier food options.  
- The project can be opened in Android Studio 3.0.1, and the properties in the Gradle file can be modified to values that are suitable for the user's computer.
- Currently, the code pages are designed to support Chinese.

## Design pattern
This Android client adopts the MVVM design pattern, dividing the software into three main layers: 
- the View layer (presentation layer) 
- the ViewModel layer (core business logic layer)
- the Model layer (model layer and network interface layer)
<p align="center">
    <img src="/image/design.png" alt="design" />
</p>

### Programming language
JAVA

### Network request
Retrofit, OkHttp, RxJava

## Building the app
1. To install the app, you can run the app-release.apk package on an Android device or connect the device to a computer via USB and use a phone assistant to install the apk package.
<p align="center">
    <img src="/image/release.jpg" alt="release.apk" />
</p>
2. After installing the app, an application icon will appear on the mobile interface, and you can click on it to run the program. However, you need to deploy the server first.
<p align="center">
    <img src="/image/icon.jpg" alt="app icon"  width="200" height="200"/>
</p>

## Images

Interface images from the app

<p align="center">
    <img src="/image/login.jpg" alt="login" width="300" height="600" />
</p>

<p align="center">
    <img src="/image/goodslist.jpg" alt="good list" width="300" height="600"/>
</p>
