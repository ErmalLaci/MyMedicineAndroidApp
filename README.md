# MyMedicineAndroidApp
Android app that connects to the NHS API and lets the user view information about certain illnesses.

*What can MyMedicine do?*

This app allows users to view informations about illnesses by connecting to the nhs api. It prioritizes showing treatments to user and lets them select a treatment. After selecting a treatment it will be stored in the users history, they can later give the treatment a rating(from 1 to 5) to see which treatments worked for them. The user will also input certain information which could affect which treatments work for them, for these traits I chose Gender, Age, Activity level, and Stress level. I chose these traits after doing some research but they are fairly arbitrary and were chosen to provide an example. The user's traits and history are stored in local storage.

*What else?*

I also built a REST API which the MyMedicine app connects to (I was running it locally, hence the IP address). When a user submits a result for a treatment the users traits, the illness and treatment are stored in a NoSQL database, I used mongoDB. The reason for this is to store data which could be used with a recommendation algorithm to provide users with recommendation for which treatments will work best for them based on their traits.

*But why?*

Learned some stuff and I'm interested in how software engineers can help the medical industry. I also needed a project for my dissertation.
