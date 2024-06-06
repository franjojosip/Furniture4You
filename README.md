
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
- Jetpack Compose [Compose](https://developer.android.com/jetpack/compose)
- Jetpack Navigation [Navigation](https://developer.android.com/jetpack/compose/navigation)
- Jetpack Security [Security](https://developer.android.com/jetpack/androidx/releases/security)
- Biometrics [Biometrics](https://developer.android.com/jetpack/androidx/releases/biometric)
- DI [Hilt](https://developer.android.com/training/dependency-injection/hilt-jetpack)
- Coil [Coil](https://coil-kt.github.io/coil/compose/)
- MVVM architecture
- Material3 [Material](https://m3.material.io/)
- State and StateFlow
- Edge To Edge Configuration

## Implementation 
- [ ] App core:
    - [X] Prelogin screen
    - [X] Navigation
    - [X] Popup messages
    - [X] Error UI
    - [X] Dialogs
    - [X] Search
    - [ ] App lock (PIN)
    - [ ] App lock (biometrics)
    - [ ] Data encryption
- [X] Home screen:
    - [X] Home
    - [X] Favorites
    - [X] Notifications
    - [X] Profile
- [X] Profile
    - [X] My orders
    - [X] Shipping Addresses
    - [X] Payment Method
    - [X] My reviews
    - [X] Settings
- [X] My orders
    - [X] Tabs
- [X] Shipping Addresses
    - [X] Add new address
- [X] Payment Method
    - [X] Add new payment card
- [X] Settings
    - [X] Personal information
    - [X] Password
    - [X] Notifications
    - [X] Help Center
- [X] My Cart
- [X] Promo code
- [X] Product Detail
- [X] Checkout
- [X] Success order

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

And you're app is ready to test.

## App Design (WORK IN PROGRESS)
<p align="left">

<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/0f2973f9-f809-47e6-a32b-0ba344c5534e" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/d3816fa8-1dc9-4813-a719-a084d64f1420" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/aa4d128f-7424-400a-a10d-ac0a7934807f" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/9bd22682-a422-48ce-9bc9-dc005bd32129" width="15%"/>
<img src="https://github.com/alexandr7035/Banking-App-Mock-Compose/assets/22574399/946ae794-9568-4601-bbaa-b6179f1395d9" width="15%"/>
</p>

## Notes
- Registration is currently not required, just use LOGIN button without entering any information
