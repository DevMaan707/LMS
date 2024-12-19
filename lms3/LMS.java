package lms3;


import java.util.Scanner;

public class LMS {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DBConnector dbConnector = new DBConnector();
        Books bookManager = new Books();
        BorrowerManager borrowerManager = new BorrowerManager();
        LendingManager lendingManager = new LendingManager();

        Object[] dbStatus = dbConnector.dbConnect();
        if (!(Boolean) dbStatus[0]) {
            System.out.println("Database connection failed: " + dbStatus[1]);
            return;
        }

        while (true) {
            System.out.println("\nLMS Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Show All Books");
            System.out.println("5. Register Borrower");
            System.out.println("6. Update Borrower");
            System.out.println("7. Show All Borrowers");
            System.out.println("8. Lend Book");
            System.out.println("9. Return Book");
            System.out.println("10. Show Lending Records");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  

            switch (choice) {
                case 1:
                    System.out.print("Enter Book Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Publisher: ");
                    String publisher = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("Enter Inventory: ");
                    int inventory = scanner.nextInt();
                    Object[] addResult = bookManager.addBook(name, publisher, author, genre, inventory);
                    System.out.println(addResult[1]);
                    break;

                case 2:
                    System.out.print("Enter Book Name to Update: ");
                    String updateName = scanner.nextLine();
                    System.out.print("Enter Publisher: ");
                    String updatePublisher = scanner.nextLine();
                    System.out.print("Enter Author: ");
                    String updateAuthor = scanner.nextLine();
                    System.out.print("Enter Genre: ");
                    String updateGenre = scanner.nextLine();
                    System.out.print("Enter Inventory: ");
                    int updateInventory = scanner.nextInt();
                    Object[] updateResult = bookManager.updateBook(updateName, updatePublisher, updateAuthor, updateGenre, updateInventory);
                    System.out.println(updateResult[1]);
                    break;

                case 3:
                    System.out.print("Enter Book ID to Delete: ");
                    int deleteBookID = scanner.nextInt();
                    Object[] deleteResult = bookManager.deleteBook(deleteBookID);
                    System.out.println(deleteResult[1]);
                    break;

                case 4:
                    bookManager.showRecords();
                    break;

                case 5:
                    System.out.print("Enter Borrower's Name: ");
                    String borrowerName = scanner.nextLine();
                    Object[] registerResult = borrowerManager.registerBorrower(borrowerName);
                    System.out.println(registerResult[1]);
                    break;

                case 6:
                    System.out.print("Enter Borrower ID: ");
                    int borrowerID = scanner.nextInt();
                    System.out.print("Has Borrowed (true/false): ");
                    boolean hasBorrowed = scanner.nextBoolean();
            
                    Object[] updateBorrowerResult = borrowerManager.updateBorrower(borrowerID,hasBorrowed);
                    System.out.println(updateBorrowerResult[1]);
                    break;

                case 7:
                    borrowerManager.showRecords();
                    break;

                case 8:
                    System.out.print("Enter Borrower ID: ");
                    int lendingBorrowerID = scanner.nextInt();
                    System.out.print("Enter Book ID: ");
                    int lendingBookID = scanner.nextInt();
                    System.out.print("Enter Due Date (YYYY-MM-DD): ");
                    String lendDateStr = scanner.next();
                    java.sql.Date lendDate = null;
                    try {
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                        lendDate = new java.sql.Date(sdf.parse(lendDateStr).getTime());
                    } catch (Exception e) {
                        System.out.println("Invalid Date Format");
                    }
                    Object[] lendResult = lendingManager.addLender(lendingBorrowerID, lendingBookID, lendDate);
                    Object[] borrowerResult = borrowerManager.updateBorrower(lendingBorrowerID,true);
                    System.out.println(borrowerResult[1]);
                    System.out.println(lendResult[1]);
                    break;

                case 9:
                    System.out.print("Enter Lending ID to Return: ");
                    int returnLenderID = scanner.nextInt();
                    Object[] returnResult = lendingManager.deleteLender(returnLenderID);
                   
                    System.out.println(returnResult[1]);
                    break;

                case 10:
                    lendingManager.showRecords();
                    break;

                case 0:
                    System.out.println("Exiting LMS...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid Choice. Please try again.");
            }
        }
    }
}

