package ui;

//import javafx.embed.swing.DataFlavorUtils;
import model.Book;
import model.BookCollection;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.event.*;

// Represents the GUI representation of BookWormOrganizerApp
// CITATION: ListDemoProject from Oracle page was used as a starting point
// https://docs.oracle.com/javase/tutorial/displayCode.html?code=https://docs.oracle.com/javase/tutorial/uiswing
// /examples/components/ListDemoProject/src/components/ListDemo.java
public class BookCollectionGUI extends JPanel
        implements ListSelectionListener {

    private JList list;
    private DefaultListModel listModel;
    private JScrollPane listScrollPane;
    private JPanel buttonPane;
    private JPanel saveLoadPane;
    private JLabel welcomeLabel;
    private ImageIcon icon;

    private BookCollection myBookCollection;

    private static final String addBookString = "Add Book";
    private static final String removeBookString = "Remove Book";
    private static final String saveCollectionString = "Save Collection";
    private static final String loadCollectionString = "Load Collection";
    private static final String viewBookDetailsString = "View Book Details";

    private JButton addBook;
    private JButton removeBook;
    private JButton saveCollection;
    private JButton loadCollection;
    private JButton viewBookDetails;

    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/BookCollection.json";
    private JsonWriter jsonWriter;

    private JTextField bookName;
    private JTextField bookAuthor;
    private JTextField bookDate;
    private JTextField bookCategory;
    private JTextField bookReadState;
    private JTextField bookReview;
    private JTextField searchTitle;


    // EFFECTS: constructs the GUI framework
    public BookCollectionGUI() {
        super(new BorderLayout());
        initialiseFields();
        initialiseListAndScrollPane();
        initialisePanel();
    }

    // MODIFIES: this
    // EFFECTS: initialises BookCollection UI fields
    private void initialiseFields() {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        listScrollPane = new JScrollPane(list);

        initialiseButtons();

        myBookCollection = new BookCollection("Raifah's BookCollection");

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        bookName = new JTextField("Book Title", 10);
        bookAuthor = new JTextField("Author(s)", 10);
        bookDate = new JTextField("Publish Date", 10);
        bookCategory = new JTextField("Category", 10);
        bookReadState = new JTextField("Read State: Read/Reading/Unread", 10);
        bookReview = new JTextField("Book Review", 10);
        searchTitle = new JTextField("Search by Title", 10);

        buttonPane = new JPanel();
        saveLoadPane = new JPanel();
        icon = new ImageIcon("./data/book-clipart.png");
        welcomeLabel = new JLabel("~ Welcome to your book collection! ~", icon, JLabel.CENTER);
        welcomeLabel.setVerticalTextPosition(JLabel.TOP);
        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
    }

    private void initialiseButtons() {
        addBook = new JButton(addBookString);
        removeBook = new JButton(removeBookString);
        saveCollection = new JButton(saveCollectionString);
        loadCollection = new JButton(loadCollectionString);
        viewBookDetails = new JButton(viewBookDetailsString);
    }

    // MODIFIES: this
    // EFFECTS: sets up JList and ScrollPane
    private void initialiseListAndScrollPane() {
        listModel.addElement("");
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);

        ViewBookDetailsListener viewBookDetailsListener = new ViewBookDetailsListener(viewBookDetails);
        viewBookDetails.setActionCommand(viewBookDetailsString);
        viewBookDetails.addActionListener(viewBookDetailsListener);
        viewBookDetails.setEnabled(false);

        AddBookListener addBookListener = new AddBookListener(addBook);
        addBook.setActionCommand(addBookString);
        addBook.addActionListener(addBookListener);
        addBook.setEnabled(false);

        removeBook.setActionCommand(removeBookString);
        removeBook.addActionListener(new RemoveBookListener());

        saveCollection.setActionCommand(saveCollectionString);
        saveCollection.addActionListener(new SaveCollectionListener());

        loadCollection.setActionCommand(loadCollectionString);
        loadCollection.addActionListener(new LoadCollectionListener());

        setTextFields1(addBookListener);
        setTextFields2(viewBookDetailsListener);

        String name = listModel.getElementAt(list.getSelectedIndex()).toString();
    }

    // MODIFIES: bookName, bookAuthor, bookCategory, bookReadState, bookReview
    // EFFECTS: sets JTextFields with their respective ActionListeners
    private void setTextFields1(AddBookListener addBookListener) {
        bookName.addActionListener(addBookListener);
        bookName.getDocument().addDocumentListener(addBookListener);

        bookAuthor.addActionListener(addBookListener);
        bookAuthor.getDocument().addDocumentListener(addBookListener);

        bookDate.addActionListener(addBookListener);
        bookDate.getDocument().addDocumentListener(addBookListener);

        bookCategory.addActionListener(addBookListener);
        bookCategory.getDocument().addDocumentListener(addBookListener);

        bookReadState.addActionListener(addBookListener);
        bookReadState.getDocument().addDocumentListener(addBookListener);

        bookReview.addActionListener(addBookListener);
        bookReview.getDocument().addDocumentListener(addBookListener);
    }

    // MODIFIES:
    // EFFECTS: sets JTextFields with their respective Actionlisteners
    private void setTextFields2(ViewBookDetailsListener viewBookDetailsListener) {
        searchTitle.addActionListener(viewBookDetailsListener);
        searchTitle.getDocument().addDocumentListener(viewBookDetailsListener);
    }


    // MODIFIES: buttonPane, BookCollection UI
    // EFFECTS: sets up JPanel
    private void initialisePanel() {
        buttonPane.setBackground(new Color(234, 58, 58));
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(removeBook);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(bookName);
        buttonPane.add(bookAuthor);
        buttonPane.add(bookDate);
        buttonPane.add(bookCategory);
        buttonPane.add(bookReadState);
        buttonPane.add(bookReview);
        buttonPane.add(addBook);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        saveLoadPane.add(saveCollection);
        saveLoadPane.add(loadCollection);
        saveLoadPane.add(searchTitle);
        saveLoadPane.add(viewBookDetails);

        add(welcomeLabel, BorderLayout.NORTH);
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.SOUTH);
        add(saveLoadPane, BorderLayout.LINE_END);

    }


    // MODIFIES: listModel
    // EFFECTS: refreshes and adds books to scroll pane
    public void refreshBooks() {
        listModel.clear();
        List<Book> listOfBooks = myBookCollection.getBooks();
        for (Book book: listOfBooks) {
            String bookName = book.getTitle();
            listModel.addElement(bookName);
        }
        int index = list.getSelectedIndex();
        list.setSelectedIndex(index);
        list.ensureIndexIsVisible(index);
    }

    // CITATION: taken from Youtube tutorial: https://www.youtube.com/watch?v=FmEWqS2FP3U
    // EFFECTS: plays page flip sound effect
    public void playPageFlipSound() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        try {
            File sound = new File("./data/page-flip-7.wav");
            Clip c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(sound));
            c.start();
        } catch (Exception e) {
            System.out.println("Error occurred");
        }

    }




    // represents a class that works with and observes the View Book Details JButton
    class ViewBookDetailsListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public ViewBookDetailsListener(JButton button) {
            this.button = button;
        }

        // EFFECTS: shows details of selected books in popup window
        @Override
        public void actionPerformed(ActionEvent e) {
            displayBookDetails();

            try {
                playPageFlipSound();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }

        }


        // EFFECTS: displays a pop-up window with book details, error window if book not found
        public void displayBookDetails() {
            String title = searchTitle.getText();
            JFrame bookDetails = new JFrame();
            if (myBookCollection.isBookInCollection(title)) {
                myBookCollection.findBook(title);
                JOptionPane.showMessageDialog(bookDetails,
                        "Title: " + title
                                + "\n"
                                + "Author(s): " + myBookCollection.findBook(title).getAuthor()
                                + "\n"
                                + "Date Published: " + myBookCollection.findBook(title).getDate()
                                + "\n"
                                + "Category: " + myBookCollection.findBook(title).getCategory()
                                + "\n"
                                + "Read Status: " + myBookCollection.findBook(title).getReadState()
                                + "\n"
                                + "Review: " + myBookCollection.findBook(title).getReview(),
                        "Book Details",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                errorWindow();
            }
        }

        // EFFECTs: displays pop-up window with error message
        public void errorWindow() {
            String title = searchTitle.getText();
            JFrame bookDetails = new JFrame();

            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(bookDetails,
                    "Oops, book " + "\"" + title + "\" " + " not found!",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: handles update inserts
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: handles removing updates
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: changes updates
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: button
        // EFFECTS: enables button if not already enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: button
        // EFFECTS: returns true if text field is <= 0 and disables button, otherwise false
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


    // represents a class that works with and observes the Save Collection JButton
    class SaveCollectionListener implements ActionListener {

        // EFFECTS: saves books to Book Collection file when Save Collection is clicked
        public void actionPerformed(ActionEvent e) {
            saveBooks();
            refreshBooks();

            try {
                playPageFlipSound();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }
        }

        // MODIFIES: myBookCollection
        // EFFECTS: saves books in scroll pane to BookCollection JSON file
        public void saveBooks() {
            try {
                jsonWriter.open();
                jsonWriter.write(myBookCollection);
                jsonWriter.close();
                System.out.println("Saved " + myBookCollection.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }

    }


    // represents a class that works with and observes the Load Collection JButton
    class LoadCollectionListener implements ActionListener {

        // EFFECTS: when Load Collection button is clicked, it loads books and refreshes scroll pane
        public void actionPerformed(ActionEvent e) {
            loadBooks();
            refreshBooks();

            try {
                playPageFlipSound();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }

        }

        // MODIFIES: myBookCollection
        // EFFECTS: loads books from BookCollection JSON file into scroll pane
        private void loadBooks() {
            try {
                myBookCollection = jsonReader.read();
                System.out.println("Loaded " + myBookCollection.getName() + " from " +  JSON_STORE);
            } catch (IOException e) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            listModel.clear();

        }
    }


    // represents a class that observes the remove book button
    class RemoveBookListener implements ActionListener {

        // MODIFIES: listModel
        // EFFECTS: when Remove Book Button is called, removes book from scroll pane and selects
        //          appropriate index
        public void actionPerformed(ActionEvent a) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable remove book.
                removeBook.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }


            try {
                playPageFlipSound();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }

        }

    }



    // represents a class that observes the add book button
    //This listener is shared by the text field and the add book button.
    class AddBookListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddBookListener(JButton button) {
            this.button = button;
        }

        // MODIFIES: myBookCollection
        // EFFECTS: adds book to book collection using input in JTextFields
        public void convertJTextToStringsAndAddBook() {
            String title = bookName.getText();
            String author = bookName.getText();
            String date = bookName.getText();
            String category = bookName.getText();
            String readState = bookName.getText();
            String review = bookName.getText();

            myBookCollection.addBook(title, author, date, category, readState, review);
        }

        // MODIFIES: listModel
        // EFFECTS: inserts book in scroll pane and selects index, refreshes text fields
        public void insertElementAndResetAndMakeNewItemVisible(int index) {
            listModel.insertElementAt(bookName.getText(), index);
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            resetTextFields();

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        // MODIFIES: bookName, bookAuthor, bookCategory, bookReadState, bookReview
        // EFFECTS: resets text fields used for each book addition
        public void resetTextFields() {
            bookName.requestFocusInWindow();
            bookName.setText("Book Title");

            bookAuthor.requestFocusInWindow();
            bookAuthor.setText("Author(s)");

            bookDate.requestFocusInWindow();
            bookDate.setText("Publish Date");

            bookCategory.requestFocusInWindow();
            bookCategory.setText("Category");

            bookReadState.requestFocusInWindow();
            bookReadState.setText("Read State");

            bookReview.requestFocusInWindow();
            bookReview.setText("Book Review");
        }


        // Required by ActionListener.
        // EFFECTS: when Add Book Button is clicked, checks for duplicate book titles and adds book
        public void actionPerformed(ActionEvent e) {
            String name = bookName.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                bookName.requestFocusInWindow();
                bookName.selectAll();
                return;
            }

            selectIndexAndAddBook();

            try {
                playPageFlipSound();
            } catch (LineUnavailableException lineUnavailableException) {
                lineUnavailableException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (UnsupportedAudioFileException unsupportedAudioFileException) {
                unsupportedAudioFileException.printStackTrace();
            }


        }

        // MODIFIES: index
        // EFFECTS: selects relevant index when adding a book
        private void selectIndexAndAddBook() {
            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            convertJTextToStringsAndAddBook();
            insertElementAndResetAndMakeNewItemVisible(index);
        }

        // EFFECTS: This method tests for string equality
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: handles update inserts
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: handles removing updates
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        // MODIFIES: button
        // EFFECTS: changes updates
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        // MODIFIES: button
        // EFFECTS: enables button if not already enabled
        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        // MODIFIES: button
        // EFFECTS: returns true if text field is <= 0 and disables button, otherwise false
        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    //This method is required by ListSelectionListener.
    // MODIFIES: removeBook
    // EFFECTS: disables/ enables removeBook button
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable remove book button.
                removeBook.setEnabled(false);

            } else {
                //Selection, enable the remove book button.
                removeBook.setEnabled(true);
            }
        }
    }

    /** EFFECTS:
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("BookwormOrganizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new BookCollectionGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}