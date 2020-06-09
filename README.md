### Dr.HoneeB - Android Application

Dr.HoneeB – It is an android application of team Dr.HoneeB (E16) which uses Java.


### The main Classes in this project are:

* `MainActivity` – It is the main activity of the project which controls the bottom navigation drawer. It also initializes AWS connection*.
* `cameraFragment` – This class is a fragment in bottom navigation drawer and is used to open camera and gallery for user to upload a photo.
* `helpFragment` – This class is a fragment in bottom navigation drawer and is used to help users identify which is the right photo to upload.
* `settingsFragment` – This class is a fragment in bottom navigation drawer and is used to show user what this application is about and gives user an option to change his/her username.
* `historyFragment` - This class is a fragment in bottom navigation drawer and is used to show all the photos previously uploaded by user and its results. This feature is not included in iteration one.

* `Globals` – This class is used for creating global variable, so that they can be used in other classes when required.
* `TakeAnotherPicOrGetHealth` – It is class which is called when a user selects a photo from gallery or clicks a photo from camera from cameraFragment and gives user an option to change the existing photo or to get bee health. It also has 3 important Async methods. 
UploadAsyncTask(), this method is used to upload pictures to AWS S3 bucket. httpGetBeeOrNotBeeAsyncTask(), this method is used to call Heroku server to check whether the photo uploaded is bee or not.

*httpGetAsyncTask() is used to get results from Heroku*

* `ResultActivity` – This class is used to display the health condition of the bee and the picture user uploaded.
* `NotABee` – This class to used to display a layout when a bee is not found in the image.
* `Constants` – This class is used for creating database object, so that they can be used in other classes whenever database required.
* `MyDbHelper` – This class is used for creating CRUD in SQLite database, data can be operated by using generating object when the functionalities of database are required.
* `AdapterRecords` – This class is an adapter for “Records” page.
* `RecordDetailActivity` – This class is used to display a layout when user click an certain record.

### The below are the main resource files used:

*Drawable resource files:*
* `Splash_screen.xml` – This xml is used to display splash screen (launch screen) with application logo and name on it.
* `Corners.xml` – This xml is used create custom buttons for the application.

All the remaining drawable resource files are icons/photos used in the application.

*Layout resource files:*
* `activity_main.xml` – This xml is used as the main layout file which initializes bottom navigation drawer.
* `fragment_camera.xml` – This layout file is associated with cameraFragment and has a button “Take A Photo” when clicked open gallery or camera for user to upload a picture.
* `fragment_explore.xml` - This layout file is associated with exploreFragment and will be implemented in iteration 2.
* `anotherphoto_layout.xml` - This layout file is associated with TakeAnotherPicOrGetHealth and has two buttons “Take Another Photo” and “Get Bee Health” when clicked the first button it opens gallery or camera for user to upload a picture and the other for getting results for the uploaded photo.
* `fragment_history.xml` - This layout file is associated with historyFragment and is not included for iteration 1.
* `fragment_help.xml` - This layout file is associated with helpFragment and is used to show users which is the right photo to upload.
* `fragment_settings.xml` - This layout file is associated with settingsFragment and has two buttons about and user information.
* `result_layout.xml` - This layout file is associated with resultActivity and is used to display health condition of the bee and also has a button “Get Health For Another Bee” which allows users to upload another photo and get bee health.
