
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
- [X] App core:
    - [X] Prelogin screen
    - [X] Navigation
    - [X] Popup messages
    - [X] Error UI
    - [X] Dialogs
    - [X] Search
    - [X] App lock (password)
    - [X] App lock (biometrics)
    - [X] Data encryption
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
- [X] Test
    - [X] Unit testing
    - [X] Instrumented testing

## App Design
### Prelogin and Auth screens
<p align="left">
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/704b8043-d161-4cb5-86dd-91c4ebea276f" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/baa77dd5-29aa-4bdc-8385-c85a296d3981" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/ae52ba51-c3c9-4a0c-98db-9230be91603d" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/8c2edb72-a239-4bd9-9c47-af6ec4a6a6ca" width="20%"/>
</p>

### Home screen with bottom bar
<p align="left">
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/4a33b83a-b90e-4b65-b686-2f4bd9e97c36" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/03d52826-c3ca-48ad-b8f5-987d7ef0383e" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/3fac7058-636f-4c88-bab7-b5e6cf2d9015" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/56cdbfc6-7f49-43fb-9480-4b2af5334051" width="20%"/>
</p>

### Search products
<p align="left">
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/48ca3372-a11e-4553-9f12-e9d852473154" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/64efb0c3-307a-46a3-b4d4-5a8ca738b092" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/87c36e96-a0e9-4916-8bcd-e7571118c600" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/12d5312b-ea28-4aa5-aaba-6685cc81bdf6" width="20%"/>
</p>

### Checkout process
<p align="left">
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/3c3dbc39-3447-445c-9bdb-5ce334910401" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/8aef1aa9-f849-4a38-9be4-78cae9a19834" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/bcc3cd57-c27f-4de0-ba2b-2de8828bb13c" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/6818bde6-0b9b-477a-b731-380477d22003" width="20%"/>
</p>

### Profile and Settings
<p align="left">
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/450c54d6-e988-45c4-b92a-908c7a8f3386" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/8833f7ae-9958-4554-a27b-959635d1c551" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/92cff6cc-da13-4789-8410-5f2425516f03" width="20%"/>
<img src="https://github.com/franjojosip/Furniture4You/assets/52075105/52c2176f-a328-4d21-ad20-874b786055b7" width="20%"/>
<img src="https://github.com/user-attachments/assets/3106659e-fcb5-4aa0-8c4f-6a0986ea2f0b" width="20%"/>
</p>

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
