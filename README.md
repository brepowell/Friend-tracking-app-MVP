
# Project Name: MeetMe
***Team:*** Finders || Keepers
***Team members:*** Melody B. (Prarin Behdarvandian), Jardi Martinez Jordan, and Breanna Powell

# Basic Functionality ("Alpha"):
- Map works to track one user’s current location
    - GPS of device must be on to work correctly
    - If the GPS is not on in the phone/emulator, the marker shows up as being set in Ghana (Lat - 0, Long - 0)
- We figured out a way to do the handshake between the users, but we need to implement it 
(see Work to be completed).
- Jardi set up a database and an API to connect with the database

# Work to be completed:
- Handshake between the 2 USERS:
    - “Send Invitation” → Send USER 2 a deep link invitation via SMS
        - The invitation text message will have a link that takes USER 2 to the consent screen.
        - USER 1 goes to the Waiting for Consent screen
        - USER 1’s location goes up to the database in the cloud
        - We are going to use Intents for the navigation between screens during the handshake process because those allow us to open our app from a text messaging app without having to use push notifications and without having to use permissions for text messaging.
    - “Consent” → When USER 2 clicks the consent button
        - The URI (the identifier that tells us what screen to be on) needs to be parsed
        - USER 2’s location is added to the database in the cloud
        - USER 1 gets an SMS deep link that sends them to the map screen
- On Map:
    - Both users see both map markers
    - Send location of user 1 to user 2 in a live format
    - Permission to enable the GPS of the device in case it’s case it’s not enabled
    - Getting a route between the two users
    - Set up a timer for the duration of the session
- Stop tracking
    - Once the stop tracking button is clicked and a user decides to end the session, then BOTH need to stop tracking. The database needs to save the session information.
    - The timer needs to stop
- Input Validation
    - Invariants
        - Tracking session must be between 1 to 160 hours (1 week)
        - Password Strength requirements
        - Legitimate phone numbers
            - Must look into area codes
- Log in - maybe?
- Contacts - maybe?
- Secure the API key using the Secrets Gradle - maybe?

# Links to Documents

Here is the document with the work to be completed:
[Work to be completed](https://docs.google.com/document/d/1ENkjm07bQwUbZAubqQwTekEmvSXaW3hpHHSpGqWsBAs/edit#)

Here is our updated plan:
[Updated Project Plan](https://docs.google.com/document/d/1lmta3Ku5QuoJBZ1p7y5C__nRY3mDVDhxJSrwUcbpZao/edit?usp=sharing)

Here is our prototype document for Checkpoint 2, with the MVP features, breakdown of tasks, etc:
[Prototype Document](https://docs.google.com/document/d/1092YUAJ7lwsJ6vis1JZsLcatlr38prw7zgCVGFQG3oY/edit?usp=sharing)