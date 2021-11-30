# Cloud Storage
This is a cloud storage project I built based on a final challenge in Udacity's Java Backend nanodegree

## Features
1. **Simple File Storage:** Upload/download/remove files
2. **Note Management:** Add/update/remove text notes
3. **Password Management:** Save, edit, and delete website credentials.  

## Stack
1. The back-end with Spring Boot
2. The front-end with Thymeleaf
3. Application tests with Selenium

### The Back-End
The back-end is all about security and connecting the front-end to database data and actions. 

1. Managing user access with Spring Security
 - Unauthorized users are restricted from accessing pages other than the login and signup pages.
 - Spring Boot has built-in support for handling calls to the `/login` and `/logout` endpoints. The security configuration is overidden to switch the default login page with one of my own.
 - A custom `AuthenticationProvider` is implemented which authorizes user logins by matching their credentials against those stored in the database.  


2. Handling front-end calls with controllers
 - Controllers bind application data and functionality to the front-end. Spring MVC's application model is used to identify the templates served for different requests and populating the view model with data needed by the template. 
 - The controllers are also be responsible for determining what, if any, error messages the application displays to the user. When a controller processes front-end requests, it delegates the individual steps and logic of those requests to other services in the application, but it interprets the results to ensure a smooth user experience.
 - Controllers are kept in a single package to isolate the controller layer called controller


3. Making calls to the database with MyBatis mappers


### The Front-End

1. Login page
 - Everyone is allowed access to this page, and users can use this page to login to the application. 
 - Shows login errors, like invalid username/password, on this page. 


2. Sign Up page
 - Everyone is allowed access to this page, and potential users can use this page to sign up for a new account. 
 - Validates that the username supplied does not already exist in the application, and shows such signup errors on the page when they arise.
 - Users' passwords are secured securely.


3. Home page
The home page is the center of the application and hosts the three required pieces of functionality. The existing template presents them as three tabs that can be clicked through by the user:


 i. Files
  - The user is able to upload files and see any files they previously uploaded. 

  - The user is able to view/download or delete previously-uploaded files.
  - Any errors related to file actions are displayed. For example, a user should not be able to upload two files with the same name.


 ii. Notes
  - The user is able to create notes and see a list of the notes they have previously created.
  - The user is be able to edit or delete previously-created notes.

 iii. Credentials
 - The user is able to store credentials for specific websites and see a list of the credentials they've previously stored. Passwords displayed in this list are encrypted.
 - The user is able to view/edit or delete individual credentials. When the user views the credential, they are able to see the unencrypted password.

The home page has a logout button that allows the user to logout of the application and keep their data private.

### Testing

1. Tests for user signup, login, and unauthorized access restrictions.
 - A test that verifies that an unauthorized user can only access the login and signup pages.
 - A test that signs up a new user, logs in, verifies that the home page is accessible, logs out, and verifies that the home page is no longer accessible. 


2. Tests for note creation, viewing, editing, and deletion.
 - A test that creates a note, and verifies it is displayed.
 - A test that edits an existing note and verifies that the changes are displayed.
 - A test that deletes a note and verifies that the note is no longer displayed.


3. Tests for credential creation, viewing, editing, and deletion.
 - A test that creates a set of credentials, verifies that they are displayed, and verifies that the displayed password is encrypted.
 - A test that views an existing set of credentials, verifies that the viewable password is unencrypted, edits the credentials, and verifies that the changes are displayed.
 - A test that deletes an existing set of credentials and verifies that the credentials are no longer displayed.
