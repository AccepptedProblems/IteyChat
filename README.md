# How to connect to Firestore in Java Springboot (IntelliJ)

## Prepare

- Download `itey-security-key.zip` and unzip it.
- This folder contain json file, this json file contains key to connect to Google Firestore

## Connect to Firestore in IntelliJ

- Open IntelliJ, then open the project
- On the toolbar, select `Run > Edit Configurations`. 
If you don't have any configuration, follow next part then comeback, otherwise go to next step.
- Select to `Enviroment variables`
- Select the add sign and insert this following variables:

    - Name: `GOOGLE_APPLICATION_CREDENTIALS`
    - Value: The absolute path to the `key json file` (Note: remove the double quote in path if exist)

- Click `OK > Apply > OK`.
- Run app to make sure everything is good (**This step is quite important**)

## Add configuration in IntelliJ 

- On the toolbar, select `Run > Edit Configurations`
- Click add sign in the top left corner, select Maven
- Paste this code to the `Run` field: `spring-boot:run`
- Click `Apply > OK`
