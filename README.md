# Marshmallow UPI Demo (Android 6.0+)

A small Java/XML Android Studio project with sample transaction history and explicitly simulated payment flows. It does **not** send money, validate real UPI IDs, create payable QR codes, or connect to a bank.

## Requirements
- Android Studio with a compatible JDK (Android Studio's bundled JDK 17 is suitable for AGP 8.6.1).
- Android SDK Platform 35 installed for compilation.
- Android 6.0 (API 23) device or emulator to run the app.

## Build and run
1. Unzip the project.
2. Open the `MarshmallowUPIDemo` folder in Android Studio.
3. Allow Gradle sync; install Android SDK Platform 35 if prompted.
4. Connect an Android 6.0+ device with USB debugging enabled, or create an API 23+ emulator.
5. Press **Run**. To build an APK, choose **Build > Build Bundle(s) / APK(s) > Build APK(s)**.

The generated APK is normally under `app/build/outputs/apk/debug/`.

## Important Android 6 note
`minSdk 23` allows installation on Android 6.0. The project uses a simple platform Material theme and no AndroidX runtime dependencies. The build toolchain itself requires a modern development environment; it does not require the phone to run the latest Android.

## Real UPI: not enabled
Real-money payments require an authorized provider/acquiring bank or PSP integration and approval. Do not hard-code provider secrets into the APK. Use a backend for authenticated API calls, signed/webhook status handling, idempotency, reconciliation, and audit logs. Verify payment completion server-side with the provider; never trust a client-only callback. Never collect or store UPI PINs. Follow current NPCI, bank, provider, security, privacy, and applicable Indian regulatory requirements.

A common integration path is to contact an authorized bank/PSP/payment aggregator and obtain their official Android SDK/API documentation and sandbox credentials. Replace the demo simulation only after their onboarding and certification process. UPI intent availability and merchant/collect features depend on the provider and current rules.

## Demo limitations
- Balance is a hard-coded sample.
- Transactions are in-memory and reset when the process/app restarts.
- Simulated success does not move money.
- Sample QR screen intentionally does not emit a payment QR.
- This is starter code, not a production payment application.
