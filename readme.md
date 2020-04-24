## Project
### DEMO
[https://www.youtube.com/watch?v=mdcjMwWi5ZU](https://www.youtube.com/watch?v=mdcjMwWi5ZU)
#### INTRODUCTION
As the emergence of mobile devices and advances in geo-positioning capabilities, location-based services are ubiquitous in our lives. Most current platforms, such as Uber, Lyft, DoorDash, indeed provide excellent platforms for rideshare and food delivery but fail to extend the platform to all walks of life. There is a huge amount of gig economy freelancers that could have been exerted their own expertise to earn extra money during their free time but no such platforms provide connections between gig workers and demand requesters. Therefore, we aim to build a prototype application that extends the platform not limited to food delivery or rideshare but covering all walks of life. 

#### FUNCTIONALITY 
This project is built on JSP, MySQL, HTML, CSS, and XML. To support our functionalities, the following external libraries are added to the project:
1. JDBC Driver for MySQL 5.1.7
2. JavaMail API 1.4
3. JSTL 1.2

It is expected to meet the following features:
1. The user can be either a demand requester or a gig economy worker, which means they can post a demand request or accept a demand request from others.
2. Gig economy workers can browse all demand requests from the feed wall and then decide which one they have the willingness to do the request. Should the gig economy worker apply for a job, the gig economy worker needs to give the demand requester a quote for the request. Once they all have an agreement on the quote, the order will be created and pending to be done. 


### Architecture
Three-tier architecture is followed throughout the entire project as our design pattern to improve code quality and make code neat. The system is broken down into the following components.
#### Layers
- Presentation layer -> WebContent/WEB-INF/View
All project presentation logics are written in JSP pages and they only present the final output to the user and are not involved in any data computation or manipulation.
- Business Logic Layer -> src/aite/servlet, src/aite/model
This layer first handles all client requests, find the corresponding methods to delegate, communicate with the data access layer, fetch the final output from the data access layer, send the result to the presentation layer. All classes under package aite.model simply define the model properties, without using any getters and setters.
- Data access layer -> src/aite/service
All classes under package aite.servlet build connections to the database and are responsible for retrieving and organizing data.

------------


### Splash Page
The splashs page is presented when the user first launches the app or enters the site. With the mindset of simplicity, all unnecessary elements are removed and only logo, login, and register buttons are left on the landing page. The user can either log in to the system or create a new account. If the user is already logged in, the system shall direct to the ”Worker Seeking Page.”

### Login Page
The login page is presented when the user clicks on the login button on the splash page and allows users to access the system by typing the username and password. If the incorrect username or password is provided, an error message will be presented.

### Sign-Up Page
This sign-up page is presented when the user clicks on the register button on the splash page. The user can type the username and password to create an account for
system access. If the username is already taken, the system will prompt the
user to pick another username.

### Worker Seeking Page
After the user is logged in, this page is then presented with a list and two tabs on the top that the user can switch between the people-looking page and request-hunting page. The list contains gig workers that provide their own services with service fees and the user can pick whatever service he/she needs at the moment and send a request to the gig service provider.

### Request Hunting Page
After the user clicks on the tab item "Request Hunting" on the top, the page is presented with a list of request tasks waiting to be done by gig workers. The worker can browse the wall and apply for a task in which he/she is interested.

### Settings Page
This page can be accessed by clicking on the tab item "Settings" and it allows users to change their password, publish a new task, edit their own service, browse current tasks, order history, received reviews, and system announcement.

### Service Detail Page
After the user clicks on a list item on Worker Seeking Page, the system shall present the service details provided by the gig economy worker. Gig economy workers can post or adjust their own service details from My Service Page. The user can send their location and description to the service provider, and then the service provider can decide whether they want to accept the request or not. After the user sends the request, they can see the
request and cancel the request if he/she no longer needs it. The request list is shown to users who are service providers, and service providers can decide whether they want to accept the request or not.

### Demand Request Detail Page
When the user clicks on a list item on Request Hunting Page, the demand request detail page is shown to the user. The gig worker can decide whether if he/she wants to apply for the job or not. If he/she has the willingness to accept the requested task, he/ she needs to give a price quote according to the task description and waits until the demand requester decides whether to take the price agreement. Once the worker sends an offer, the worker will see the price quote sent and can decide whether to withdraw the offer or not. The application list is shown when the user is the task owner and he/she can decide who to take the request.

### Change Password Page
The user can change their password on this page if he/she types the correct old password, new password, and confirm password.

### New Task Page
The user can publish a new task by providing a title, location, and description. Task Title and Description are mandatory, and Task Location is optional. If success, the task will be shown on Request Hunting page.

### My Service Page
This page is presented when the gig worker intend to add or edit the service. They can customize their own services and disable the service if they no longer provide it. Due to the time limit, only one service is allowed at a time per gig worker.

### My Tasks Page
This page shows the user's current available tasks to public. The user can click on the title and check its application list.

### Order History Page
Once the requester accepts an offer or the service provider takes the request, an order will be created and be put on this page. This page shows the user's whole order history.

### Order Detail Page
This page shows the user's current available tasks to public. The user can click on the title and check its application list. Once both parties leave a review to each other, the order will be marked as "Completed" and then they can see each other's review.

### Review Page
The user can browse all the received reviews from the
past.

### Announcement Page
The user can browse announcements.