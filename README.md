# **A Bookworm's Organizer** <img src="http://clipart-library.com/images/6cp5X4yEi.png" width="100" height="50" />

## *~* *Digitize* *your* *book* *collection!* *~*

### What is it? 
This application aids to *digitize and organize* a user's book collection, helping keep track of literature and store
information about books.

### Who will use it?
The application is meant to be used primarily by *readers* with large collections looking to digitize and organize their
book collection, maybe to store information for an online blog or just keep track of everything. 

### Why make this?
This project is of interest to me because I was quite the avid reader when I was younger and amassed a book collection
of my own over the years. However, when I got a lot busier during high school, I fell out of reading and missed out on
my hobby for quite some time. Now in university I am busier than ever, but I have slowly started to reignite my love
for reading by buying new books, which has inspired me to use this idea for my project.


### User Stories 
- As a user, I want to be able to add a new book to my collection
- As a user, I want to be able to remove a book from my collection
- As a user, I want to be able to find out how large my book collection is
- As a user, I want to be able to view a list of all book titles in my collection
- As a user, I want to be able to update the reading status of a book
- As a user, I want to be able to select a book and view its details 
- As a user, I want to be able to write a review for a book
- As a user, I want to be able to save my book collection to file
- As a user, I want to be able to be able to load my book collection from file

### Phase 4: Task 2
- I chose to implement the option of making a class in the model package ROBUST, specifically in the Book class with the
method updateBook
    - now has ReadStateException thrown if the Book to be updated has a readState that is not one of "Read", 
    "Currently Reading", or "Unread"
    - now has AlreadyReadException thrown if the Book to be updated has readState "Read"
    
### Phase 4: Task 3
- If I had more time to work on my project, I definitely would do some refactoring:
    - Split up the BookCollection GUI class into its respective components - JPanels, JList, JButtons with their 
    Action Listeners, etc. for cleaner organization and distinction
        - helps condense BookCollectionGUI class into a smaller, more concise class with a single responsibility of putting 
        all the UI components together rather than both making and putting them together
    