Converta – Currency Converter App
Overview
Converta is a modern Android mobile application developed in Kotlin to simplify international currency conversions. It provides users with real-time and accurate exchange rates through integration with the ExchangeRate API.
The application features Firebase Authentication for secure user registration and login, ensuring that all user data remains protected. Additionally, Converta supports multi-language functionality, enabling users to switch seamlessly between English, isiZulu, and IsiZulu.
With a clean and responsive user interface, Converta offers a smooth and efficient experience, allowing users to convert between major currencies such as USD, ZAR, EUR, GBP, and JPY in just a few taps.

Objective
The primary objectives of the Converta application are to:
•	Provide real-time currency conversion using a live exchange rate API.
•	Implement Firebase Authentication for secure login and registration.
•	Offer multi-language support to enhance accessibility and inclusivity.
•	Deliver a responsive, intuitive, and user-friendly interface.
•	Incorporate error handling and input validation for stability and reliability.
•	Implement offline support with data caching for limited connectivity scenarios.

Features
User Authentication
•	Secure login and registration through Firebase Authentication.
•	Passwords are encrypted and verified before user access is granted.
•	Single Sign-On (SSO) support for Google account login (POE requirement).
Currency Conversion
•	Converts between multiple global currencies in real time.
•	Utilizes the ExchangeRate API via Retrofit2 for up-to-date data retrieval.
•	Displays conversion results instantly and clearly.
Multi-Language Support
•	Supports English, isiZulu, and Tsonga.
•	Implemented via LanguageHelper class for dynamic localization.
•	The interface updates instantly when a language is changed in settings.
Settings Screen
•	Allows users to change their preferred language and app theme.
•	Updates preferences using shared preferences for persistence.
Error Handling
•	Handles invalid or empty input fields.
•	Displays user-friendly toast messages for network errors or API timeouts.
•	Utilizes try-catch blocks to prevent crashes during data retrieval.
Offline Mode with Sync
•	Users can continue to perform basic conversions offline.
•	Cached exchange rates are stored in RoomDB for offline access.
•	Once the internet connection is restored, rates automatically synchronize with the live API.
Real-Time Notifications
•	Integrated Firebase Cloud Messaging (FCM) for real-time updates.
•	Sends alerts about currency rate changes or app updates.
Modern User Interface
•	Built using XML layouts and Material Design components.
•	Optimized for different screen sizes with adaptive layout constraints.
•	Includes a custom app icon and optimized image assets (mdpi, hdpi, xhdpi).

Tools and Technologies
Tool / Technology	Purpose
Android Studio	Integrated development environment
Kotlin	Primary programming language
Firebase Authentication	Secure login and registration
Firebase Cloud Messaging	Real-time notifications
Retrofit2	API communication and data handling
Gson Converter	JSON parsing
ExchangeRate API	Live currency exchange data
RoomDB	Local offline data storage
XML	UI layout design
GitHub	Version control and collaboration

System Architecture
The Converta app follows a modular and layered architecture to ensure scalability, maintainability, and separation of concerns.
Key Components:
•	Authentication Module: Manages registration, login, and SSO using Firebase.
•	Converter Module: Fetches live rates from the API and performs calculations.
•	Settings Module: Handles user preferences, including language and theme.
•	LanguageHelper: Dynamically applies selected language localization.
•	Database Layer: Uses RoomDB for offline data caching and sync management.
•	Notification Layer: Integrates Firebase Cloud Messaging for real-time updates.

Error Handling and Validation
•	Validates all input fields for empty or invalid data types.
•	Displays Snackbar or Toast messages for errors (e.g., “Please enter a valid amount”).
•	Detects and handles no-internet scenarios with offline fallback.
•	Implements try-catch blocks for safe exception handling.
Results
The Converta app successfully:
•	Performs accurate real-time currency conversions.
•	Authenticates users securely using Firebase.
•	Supports offline mode with synchronization.
•	Sends push notifications for updates.
•	Provides multi-language support (English, isizulu, and IsiZulu).
•	Delivers a modern, accessible, and responsive user experience.
All system functionalities were tested and verified to meet project requirements.
Publishing readiness
The application is fully configured for release on the Google Play Store. It includes a signed release build, a unique package name (com.example.converta), and Firebase integration for analytics, authentication, and cloud messaging.
