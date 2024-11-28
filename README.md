# LearnConnect

## Installation
1- Clone this repository:    git clone https://github.com/yourusername/your-repo-name.git
2- Open the project in Android Studio.
3- Sync the project with Gradle files.
4- Run the application on an emulator or physical device.

If you encounter any issues during installation, try the following steps:

1- Delete the .idea folder and Gradle files.
2- In Android Studio, go to File > Invalidate Caches / Restart.
3- After that, restart the IDE and rebuild the project.
This process often resolves common setup and dependency issues.

## Requirements
### 1. User Operations
The application begins with a Login Screen, where users can log in with their credentials. Alternatively, they can navigate to the Sign Up Screen to create a new account.

User Registration and Authentication:
  -  User registration and login are implemented using Firebase Authentication, ensuring secure and reliable handling of user credentials.
Profile Screen:
  -  After successfully logging in, users can access their Profile Screen via a Bottom Navigation Bar, providing an intuitive and seamless user experience.

### 2. Course Management
After logging in, the main screen displays a list of available courses. When a course is selected, the user is navigated to the Course Details Screen, where they can enroll in the course.
Enrolled Courses:
  -  Once enrolled, users can access their registered courses from the My Courses Screen at any time. If a user wishes to leave a course, they can use the Drop Course button, which becomes active after enrollment.
  -  Upon dropping a course, access to its lesson videos is revoked.
Profile Integration:
  -  The Profile Screen also provides an overview of the total number of courses the user has enrolled in, offering a quick summary of their progress.

### 3. Video Player
When a user enrolls in a course via the Course Details Screen, the "Go to Lessons" button becomes active. By clicking this button, the user navigates to the Lessons Screen, which displays lessons related to the selected course.
Lesson Layout:
  -  The lessons are listed at the bottom of the screen (e.g., Lesson 1, Lesson 2), while the top section features a Video Player. Selecting a lesson from the list starts its corresponding video in the player.
Progress Tracking:
  -  Video progress is stored locally using DataStore, ensuring that the user can resume from where they left off, even if the application is closed and reopened. This feature enhances usability and provides a seamless learning experience.

### 4. Dark Mode Support
Users can toggle between Light Mode and Dark Mode using a Switch available on the Login and Signup screens. This theme selection is applied not only to the current screen but to the entire application, ensuring a consistent appearance throughout.
Profile Screen Integration:
  -  After logging in, the application theme can also be changed via a Switch located on the Profile Screen, providing users with flexibility to update their preferences at any time.

## Bonus Features
### 1. Filtering and Searching by Categories
Users can easily search for specific courses using the Search EditText on the Courses Screen by typing a desired keyword to filter the course list. Additionally, a Filter Button next to the search bar allows users to filter courses based on selected categories.
Extended Functionality:
  -  These filtering and search features are also available on the My Courses Screen and the Favorites Screen, enabling users to quickly locate their enrolled or favorited courses with ease.

### 2. Favorites
On the Course Details Screen, users can add a course to their favorites by clicking the Add to Favorites button located in the top-right corner.
Accessing Favorites:
  -  Users can view their favorited courses by navigating to the Favorites Screen through the Go to Favorites button available on the Profile Screen. This screen displays a list of all courses the user has marked as favorites for quick access.

### 3. Video Speed Control
While watching a selected lesson on the Lesson Videos Screen, users can adjust the playback speed of the video using the Speed Control feature.
Speed Options:
  -  The video player allows users to change the playback speed to 1x, 1.25x, 1.5x, or 2x, offering a customized viewing experience that suits their learning pace.

## Additional Bonus Features
### 1. Sign Out
Users can log out of the application using the Sign Out button located at the bottom right of the Profile Screen. After signing out, the app navigates back to the Login and Signup Screens, and the back stack is cleared to prevent returning to the previous screens.

### 2. Back Stack Management
Efficient back stack management has been implemented to ensure smooth navigation and prevent unwanted behaviors:
Avoiding Stack Overload:
  -  Navigation operations clear unnecessary back stack entries to prevent stack overflow while navigating through the app.
Issue Prevention:
  -  Previously, if a user accessed lesson videos, returned to the main screen, and dropped a course, they could still navigate back to the lesson videos due to residual back stack entries. This issue has been resolved by clearing the back stack upon dropping a course.
Sign Out Back Stack Handling:
  -  When a user signs out, the back stack is cleared to prevent them from returning to the application using the back button, ensuring proper session termination.

### 3. Staying Logged In
When the user opens the application, it checks whether they previously signed out using the Sign Out button.
Automatic Login:
  -  If the user has not signed out, the app bypasses the Login Screen and opens directly in a logged-in state, providing a seamless experience and saving time.
Session Management:
  -  This feature ensures that users can continue from where they left off without needing to re-enter their credentials, as long as they haven't explicitly logged out or their credentials token expired .

### 4. User Data Management
User data is stored using a unique user ID for each individual.
Data Isolation:
  -  This approach ensures that data is securely tied to each user's account, preventing any mix-up or overlap of data between users. Each user’s information remains separate, ensuring privacy and integrity across the app.

## Architecture and Technologies
### MVVM
  -  The app follows the MVVM (Model-View-ViewModel) architecture pattern.
### Firebase
 Storing user data and course information locally in a database might not be the most efficient solution. Therefore, for user registration, login, and data management, Firebase Authentication and Firebase Realtime Database were used.
  -  Firebase Authentication:
        Handles secure user authentication, allowing users to register, log in, and manage their accounts. This ensures that user credentials are safely stored and processed on Firebase’s secure platform.
  -  Firebase Realtime Database:
        Used to store and retrieve user data and course information in real time. This allows seamless synchronization across devices, ensuring data consistency and accessibility for users on any device they log in from.
### Dependency Injection - Dagger
  -  To achieve low coupling between classes and reduce boilerplate code, Dagger was used for dependency injection.
### Navigation Library and Fragment
  -  Since the application is multi-screen, Fragments were used after the user login, utilizing the Navigation Component for efficient screen transitions. With Navigation, the app achieves an efficient, smooth, and organized navigation structure, enhancing both user experience and code maintainability.
### Coroutines
  -  To prevent the main thread from blocking during data write and read operations to the database, suspend functions and coroutines were used.
### RecyclerView
  -  To achieve an effective and smooth list display, RecyclerView was used.
### LiveData
  -  In the application, LiveData was utilized for updating UI components.
  -Observer Pattern:
    In certain parts of the app, the Observer pattern was used with LiveData to observe data changes. Whenever the data changes, the observer is notified and the UI is updated accordingly, ensuring that the app always displays the latest information.
### Data Binding:
  -  Data Binding was used in combination with LiveData to directly bind the data to the UI components. This eliminates the need for manual UI updates and simplifies the code. With data binding, changes in LiveData automatically trigger UI updates, reducing the boilerplate code and improving the maintainability of the app.









































