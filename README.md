# is3106-tutorme

# Introduction
TutorMe is a platform to link tutors and tutees up. 
This is a Carousell-inspired web application which serves enables tutees to make offers on the
Tutors puts up a job listing that consists of a subject with multiple levels. 
Tutees can negotiate for different rates, timeslots and area to meet up.

Tutees can make offers on the tutor's job listings and rate them after tutors have accepted them.
These ratings will influence the star score for individual job listings and the tutor as well.

Tutors and tutees can also chat with each other. Feedback can also be sent to admins as well.
Admins can view key metrics to monitor usage rates of the platform, ban and unban users.

# Technology Used
The backend is support by Java EE with selected external libraries such as authentication services using Java Web Tokens.
The frontend is powered by React and bootstrapped Ant Design components.

# Setting up the backend 
1. To run the NetBeans project, create a new Java DB Database with the following credentials
    # JavaDB Database Name: tutormeDB
    # JavaDB username: administrator
    # JavaDB password: password
    # JNDI name: tutorme
    # JDBC Connection Pool Name: tutormeConnectionPool
2. Please add the following libraries("Jackson Annotations", "JJWT") to the .ejb and .war components of the project. 
    The .jar files are stored in a folder named "dependencies".
3. For the emailSession, we realized that there was some bug restricting the authentication to Google's SMTP server.
    To resolve this, please navigate to the GlassFish server on your machine with the follow path       
    "../GlassFish_Server\glassfish\modules\endorsed" and replace the "grizzly-npn-bootstrap.jar" provided in the dependencies folder.
4. The rectification was to remove the "sun" folder within the "grizzly-npn-bootstrap.jar" file.

# Setting up the frontend
1. Navigate to the frontend folder and install relevant dependencies with `yarn install`.
2. Once download has been completed execute `yarn start` and the browser window should direct you to localhost:3000

The web application is deployed and ready for us now. Thank you.
