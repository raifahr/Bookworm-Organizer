package ui;

public class Main {
    public static void main(String[] args) {

        /* GUI Implementation
        Schedule a job for the event-dispatching thread:
        creating and showing this application's GUI.
        */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                BookCollectionGUI.createAndShowGUI();
            }
        });

        /* Console UI Implementation
        try {
            new BookCollectionConsoleUI();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
        */

    }
}