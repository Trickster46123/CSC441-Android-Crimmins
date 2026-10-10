# Momentum

Momentum is an Android app I made for CSC 441 using Kotlin and Jetpack Compose. The idea behind the app is to give users a simple way to keep track of their goals. They can add goals, look at the ones they already have, and remove them whenever they want.

I wanted to keep the app simple and easy to use while also making sure everything worked between the different screens.

## Features

### Home Screen

The Home screen is where everything starts. It shows the Momentum title, a short description, and how many goals are currently in the list. The app also starts with five example goals.

Users can type in a goal and add it to their list. There are also buttons to see all their goals or go to the About screen.

### List Screen

The List screen shows all the goals that have been added. I used a scrolling list so users can still see their goals even when more are added.

Each goal has its own Remove button. I also added a Remove all button so users can clear the whole list instead of removing everything one at a time.

When the list is empty, a message appears telling the user there are no goals. There is also a button that takes them back to the Home screen.

### About Screen

The About screen gives some information about Momentum and what the app is supposed to do. It also shows who created the app.

I made sure users can return to the Home screen using either the Back button in the app or the Android system Back button.

## Input Validation

I added some rules for entering goals so users can't just type anything into the list.

- Goals cannot be blank or just spaces.
- Goals need to be at least 3 characters long.
- Goals cannot be more than 40 characters long.
- Goals have to start with a letter.
- Users cannot add the same goal twice, even with different capitalization.
- Extra spaces at the beginning and end are removed.

If someone enters a goal that breaks one of these rules, the app shows an error message explaining the problem. The Add button is also disabled when nothing has been entered.

## How to Run

1. Clone or download the project from GitHub.
2. Open the project in Android Studio.
3. Wait for Gradle to finish syncing.
4. Start an Android emulator or connect an Android device.
5. Press Run to launch the app.

## Known Limitations

Right now, the goals are only stored in memory. This means they aren't permanently saved, so they can be lost when the app restarts or its state is recreated.

One thing I would like to work on later is adding a way to save goals so they stay there even after closing the app. I could also add more features for organizing goals as the project continues.