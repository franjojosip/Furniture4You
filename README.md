# Furniture4You Compose mobile application

This project is fully implemented in Compose. It combines modern UX/UI, Navigation and Security.<br /><br />
The goal is to create a functional furniture shopping app which allows the user to search the products, use cart, buy with valid card. User can also search furniture, filter, check details and colors, add to favorites, use notifications, etc..<br />
All the required information for the user can be found on Profile tab. All the important information can be found here (orders, addresses, cards, reviews and other usefull settings).

## Clean architecture
- MainActivity (start point of the app with navigation graphs)
- Common (models, components, dialogs, fields, mock data and utils) 
- Auth (for authentication)
- Each screen (represented with MVVM - Screen + ViewModel and usually State class)
    
## Stack
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Jetpack Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Jetpack Security](https://developer.android.com/jetpack/androidx/releases/security)
- [Biometrics](https://developer.android.com/jetpack/androidx/releases/biometric)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Material](https://m3.material.io/)
- MVVM architecture
- State and StateFlow
- Edge To Edge Configuration

## Implementation 
- [X] App core
- [X] Home screen
- [X] Profile
- [X] My orders
- [X] Shipping Addresses
- [X] Payment Method
- [X] Settings
- [X] My Cart
- [X] Promo code
- [X] Product Detail
- [X] Checkout
- [X] Success order
- [X] Test

## App Design
<img width="1758" height="938" alt="image_original" src="https://github.com/user-attachments/assets/d9e28122-86b1-4bb2-8dbb-5c8bec51c01e" />


## Getting started

1. Download this repository extract and open the template folder on Android Studio
2. Rename the app package in Manifest `com.fjjukic.furniture4you`
3. Check if the manifest package was renamed along with the package
5. On `app/build.gradle`, change the applicationId to the new app package
6. On `app/build.gradle`, update the dependencies Android Studio suggests
7. On `strings.xml`, set your application name
8. On `Theme.kt` & `Color.kt` set your application style
9. Replace the App Icons
10. Run `./gradlew dependencyUpdates` and check for dependencies
11. Ready to Use

And you're app is ready to use.

## Notes
- For testing purposes, use DEMO user (email: test@mail.com, password: test123)
- Biometrics can also be toggled inside the  Settings screen
- Registration is required, and the user can add biometrics after successful registration.
- Added unit and instrumentation tests
