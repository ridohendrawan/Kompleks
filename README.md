# Kompleks

An Android mobile application used to manage the visitors of your neighborhood.

## Introduction

In Indonesia, there is a common pain point when managing a housing complex or a neighborhood. That pain point is having to write our visitors manually. Kompleks is a mobile application used to take note of your neighborhood's visitors. This application aims to resolve that pain point. When we do this manually, the pain point branches: lost pens/pencils, having to spend extra funds to buy papers/logbooks, having to dedicate a spot to store them, not knowing what our visitor looks like, etcetera.

## Architecture

- Full of asynchronous functions, coroutines, and batch processing for maximum speed.
- Built with performance and user experience in mind.
- Simple, self-explanatory, intuitive UI.
- Low RAM usage.
- Image processing is our main strength, we do not want to store big binary files inside our Cloud Storage for maximum speed and usage.
- Usage of modern Android 2021 styles: View Binding, Coroutines, Compression, Work Managers, and many more.
- Worker notification to notify the data have been cleaned up.
- Mostly the application is coded in English, but the text displayed in the application is Indonesian. However, this application completely supports a11y and i18n.

## Features

- Every distribution of the application will have their own database.
- User can log in, but they cannot sign up.
- User can add a visitor.
- User can see all visitors who are currently inside the neighborhood.
- User can see all visitors who are currently not inside the neighborhood.
- User can update the status of a visitor: from 'inside' the neighborhood to 'outside' the neighborhood. User cannot manually update a single data of the user because there is no need to do that.
- User cannot manually delete data. All data deletions are processed with a work manager and Cloud Functions. Data is deleted every Monday for a single data who has been longer than 30 days.

## Requirements

- Android Studio IDE 4.2+
- Firebase Authentication
- Firebase Cloud Storage
- Firebase Firestore Database

## Installation

- First off, clone the repository.

```bash
git clone https://github.com/ridohendrawan/Kompleks.git
```

- Second, import the repository to your Android Studio, then setup Firebase there.
- Download `google-settings.json` from your Firebase account, then place it in `Kompleks/app` folder.
- Create a single user in your Firebase Authentication service. For example:

```bash
username: user@kompleks.avanger.com
password: 123456
```

- Setup rules for Firebase Firestore. This is important, as we will only accept requests from an authorized user.

```bash
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

- Same as above, but this time we setup rules for Firebase Cloud Storage.

```bash
rules_version = '2';
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

- Build the application and run!
- As a note, you might need to add several indexes to the Firestore when its your first time performing queries for performance. Just open your `Logcat` in Android Studio and it will guide you there.

## Contribution

For now, this repository does not accept pull requests. Please contact the main developer, [Nicholas Dwiarto](https://github.com/lauslim12) if you wish to contribute to the development.

## License

MIT License.
