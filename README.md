# spaceX
We would like you to develop an app that will display SpaceX data. API documents are available here: https://docs.spacexdata.com 
Must have: 
• Display a list of launches with minimal information. 
• Ability to sort launches by either launch date or mission name. 
o When sorted by launch date, group them by year o When sorted by mission name, group them by the first alphabet 
• Ability to filter by launch success. 
• When a launch is selected display a screen with detailed launch information and the rocket details used for the launch. 
o Data from One Launch endpoint o Data from One Rocket endpoint 
• Provide a feature to navigate users to Wikipedia page about the rocket. 
Please create a public repository on Github and share the link. In Read Me, please explain the architecture, design patterns and assumptions you’ve made for the assignment. 
Bonus points: 
• Try to avoid third party libraries 
• Kotlin 


#Devloper Commants as below:

In this assignment i have used a clean architecture to separate and make each layer independent of each other by using Dagger to keep everything decoupled from each other.

The framework used is MVVM to load the list of Launches on the UI, do sorting and filtering of the data, have made it quite dynamic rather than to only do one field filtering can do filtering based on various other fields

In terms of assumptions have only tried to explain and implement all the key features in the requirement and not displaying whole lot of data as no UI was required but have used all the latest principles and architectural components.

In the End, the goal of the assignment and how it is implemented is to go through MVVVM, Retrofit, Dagger, LiveData updations of UI and how to separate everything and use the kotlin extensions to do few operations.

Also, developed in Kotlin and no third party lobraries used.
