package ui;

import model.exceptions.AlreadyReadException;
import model.Book;
import model.BookCollection;
import model.exceptions.ReadStateException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents the Book Organizer Application
// Much of this code is attributed to BankTeller and JsonSerializationDemo
// https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class BookCollectionConsoleUI {
    private Scanner userInput;
    private static final String JSON_STORE = "./data/BookCollection.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private BookCollection myBookCollection;


    //EFFECT: constructs book collection and runs application
    public BookCollectionConsoleUI() throws FileNotFoundException {
        userInput = new Scanner(System.in);
        myBookCollection = new BookCollection("Raifah's BookCollection");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runBookwormOrganizerApp();
    }


    //MODIFIES: this
    //EFFECT: processes user input
    private void runBookwormOrganizerApp()  {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMenu();
            command = userInput.next();
            command = command.toLowerCase();

            if (command.equals("10")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nHave fun reading!");

    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println();
        System.out.println("~ Welcome to Your Book Collection! ~");
        System.out.println("\nWhat would you like to do today?");
        System.out.println("\t1 -> Add a Book");
        System.out.println("\t2 -> Remove a Book");
        System.out.println("\t3 -> Find a Book");
        System.out.println("\t4 -> Update a Book's Reading Status");
        System.out.println("\t5 -> Write a Book Review");
        System.out.println("\t6 -> View Collection Size");
        System.out.println("\t7 -> View Collection Titles");
        System.out.println("\t8 -> Load a Book Collection");
        System.out.println("\t9 -> Save Book Collection");
        System.out.println("\t10 -> Quit Application");
    }


    // MODIFIES: this
    // EFFECTS: initializes scanner and book collection
    private void init() {
        userInput = new Scanner(System.in);
        myBookCollection = new BookCollection("Raifah's BookCollection");
    }



    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command)  {
        if (command.equals("1")) {
            doAddBook();
        } else if (command.equals("2")) {
            doRemoveBook();
        } else if (command.equals("3")) {
            doFindBook();
        } else if (command.equals("4")) {
            doUpdateBook();
        } else if (command.equals("5")) {
            doWriteBookReview();
        } else if (command.equals("6")) {
            doCollectionSize();
        } else if (command.equals("7")) {
            doViewCollectionTitles();
        } else if (command.equals("8")) {
            doLoadBookCollection();
        } else if (command.equals("9")) {
            doSaveBookCollection();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // MODIFIES: this
    // EFFECTS: adds a book to the collection
    // Attribute code to a Stack Overflow Thread:
    // https://stackoverflow.com/questions/4058912/scanner-doesnt-read-whole-sentence-difference-between-next-and
    // -nextline-o
    private void doAddBook() {
        System.out.print("\nEnter Book Title: ");
        String title = userInput.next();
        title += userInput.nextLine();

        System.out.print("\nEnter Author(s): ");
        String author = userInput.next();
        userInput.nextLine();

        System.out.print("\nEnter Date of Publish: ");
        String date = userInput.next();
        userInput.nextLine();

        System.out.print("\nEnter Category: ");
        String category = userInput.next();
        userInput.nextLine();

        System.out.print("\nEnter Read State (Read/Reading/Unread): ");
        String readState = userInput.next();
        userInput.nextLine();

        System.out.print("\nEnter Book Review if Read/None: ");
        String review = userInput.next();
        userInput.nextLine();

        myBookCollection.addBook(title, author, date, category, readState, review);
        System.out.println();
        System.out.println(title + " has been added to your collection!");
    }


    // MODIFIES: this
    // EFFECTS: removes a book from the collection
    private void doRemoveBook() {
        System.out.print("\nEnter Book Title to Remove: ");
        String title = userInput.next();
        title += userInput.nextLine();

        myBookCollection.removeBook(title);
        System.out.println();
        System.out.println(title + " has been removed from your collection.");
    }


    // MODIFIES: this
    // EFFECTS: finds a book in the collection and displays its details
    private void doFindBook() {
        System.out.print("\nSearch for a Book by its Title: ");
        userInput.nextLine();
        String searchTitle = userInput.nextLine();

        if (myBookCollection.isBookInCollection(searchTitle)) {
            myBookCollection.findBook(searchTitle);
            System.out.println();
            System.out.println("\nTitle: " + myBookCollection.findBook(searchTitle).getTitle());
            System.out.println("\nAuthor(s): " + myBookCollection.findBook(searchTitle).getAuthor());
            System.out.println("\nDate Published: " + myBookCollection.findBook(searchTitle).getDate());
            System.out.println("\nCategory: " + myBookCollection.findBook(searchTitle).getCategory());
            System.out.println("\nRead Status: " + myBookCollection.findBook(searchTitle).getReadState());
            System.out.println("\nBook Review: " + myBookCollection.findBook(searchTitle).getReview());
        } else {
            System.out.println("\nOops, book " + "\"" + searchTitle + "\" " + " not found!");

        }
        System.out.println();
    }


    // MODIFIES: this
    // EFFECTS: updates a book's reading status in the collection
    private void doUpdateBook() {
        System.out.print("\nEnter Book Title to Update: ");
        String searchTitle = userInput.next();
        searchTitle += userInput.nextLine();

        if (myBookCollection.isBookInCollection(searchTitle)) {
            try {
                myBookCollection.findBook(searchTitle).updateReadStatus();
            } catch (AlreadyReadException e) {
                System.out.println("\nOops, you have already read " + "\"" + searchTitle + "\"" + "!");
            } catch (ReadStateException e) {
                System.out.println("\nOops, cannot update " + "\"" + searchTitle + "\""
                        + "because of INVALID Read State!");
            }
        }
    }



    // MODIFIES: this
    // EFFECTS: adds review to a book in the collection
    private void doWriteBookReview() {
        System.out.print("\nEnter Book Title to be Reviewed: ");
        String searchTitle = userInput.next();
        searchTitle += userInput.nextLine();

        System.out.print("\nEnter Book Review: ");
        String userReview = userInput.next();
        userReview += userInput.nextLine();

        if (myBookCollection.isBookInCollection(searchTitle)) {
            myBookCollection.findBook(searchTitle).addReview(userReview);
            System.out.println("\nYour book review has been added to " + "\"" + searchTitle + "\"" + "!");
        } else {
            System.out.println("\nOops, book " + "\"" + searchTitle + "\" " + " not found!");
        }
    }


    // MODIFIES: this
    // EFFECTS: returns size of book collection
    private void doCollectionSize() {
        int size = myBookCollection.collectionSize();
        System.out.println("\nYour book collection currently has " + size + " books!");
    }


    // EFFECTS: prints all the books in book collection to the console
    private void doViewCollectionTitles() {
        List<Book> books = myBookCollection.getBooks();

        for (Book b : books) {
            System.out.println(b.getTitle());
        }
    }


    // MODIFIES: this
    // EFFECTS: saves the book collection to file
    private void doSaveBookCollection() {
        try {
            jsonWriter.open();
            jsonWriter.write(myBookCollection);
            jsonWriter.close();
            System.out.println("Saved " + myBookCollection.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads book collection from file
    private void doLoadBookCollection() {
        try {
            myBookCollection = jsonReader.read();
            System.out.println("Loaded " + myBookCollection.getName() + " from " +  JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
