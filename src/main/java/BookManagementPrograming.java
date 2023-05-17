import java.util.Scanner;

public class BookManagementPrograming extends UtilsBook {

    public static void main(String[] args) {

        BookDataFactory.checkAndCreateFile(IFileInfo.absolutePath);

        int option = 0;
        boolean isContinue = true;
        Scanner scanner = new Scanner(System.in);

        while (isContinue) {
            System.out.println("======= Book Management Program (CRUD)============\n" +
                    "1. Add new book\n" +
                    "2. Find a book(ISBN)\n" +
                    "3. Update a book\n" +
                    "4. Delete a book\n" +
                    "5. Print the book list\n" +
                    "0. Exit");
            System.out.print("---- Please choose the above option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1:
                    System.out.println("=== Add new book ===");
                    Book newBook = UtilsBook.inputBookInfo();

                    // add new book & save
                    BookDataFactory.saveBookList(BookDataFactory.addNewBook(newBook, IFileInfo.absolutePath), IFileInfo.absolutePath);
                    System.out.printf("The book is saved into DB wit info: Book { %s, %s, %s, %d}\n", newBook.getIsbn(), newBook.getTitle(), newBook.getAuthor(), newBook.getYear());
                    break;

                case 2:
                    if (BookDataFactory.getListBooks(IFileInfo.absolutePath).size() == 0) {
                        System.out.println("=== There are no any books in DB --- Please add new book to do find book !!!");
                    } else {
                        System.out.println("=== Find Book ===");
                        String isbnToFind = UtilsBook.inputISBN();
                        if (isbnToFind.equalsIgnoreCase("exit")) {
                            break;
                        }

                        BookDataFactory.findBook(isbnToFind, IFileInfo.absolutePath);
                    }
                    break;

                case 3:
                    if (BookDataFactory.getListBooks(IFileInfo.absolutePath).size() == 0) {
                        System.out.println("=== There are no any books in DB --- Please add new book to do update !!!");
                    } else {
                        System.out.println("=== Update Book ===");
                        Book updatedBook = UtilsBook.inputBookUpdated();
                        if (updatedBook == null) {
                            break;
                        }
                        // update book and save
                        BookDataFactory.saveBookList(BookDataFactory.updateBook(updatedBook, IFileInfo.absolutePath), IFileInfo.absolutePath);
                        System.out.printf("The book is saved into DB wit info: Book { %s, %s, %s, %d}\n", updatedBook.getIsbn(), updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYear());
                    }
                    break;

                case 4:
                    if (BookDataFactory.getListBooks(IFileInfo.absolutePath).size() == 0) {
                        System.out.println("=== There are no any books in DB --- Please add new book to do delete !!!");
                    } else {
                        System.out.println("=== Delete Book ====");
                        String isbnToDelete = UtilsBook.inputISBN();
                        if (isbnToDelete.equalsIgnoreCase("exit")) {
                            break;
                        }
                        // delete book & save
                        BookDataFactory.saveBookList(BookDataFactory.deleteBook(isbnToDelete, IFileInfo.absolutePath), IFileInfo.absolutePath);
                    }
                    break;

                case 5:
                    System.out.println("======= List Book in DB =======");
                    if (BookDataFactory.getListBooks(IFileInfo.absolutePath).size() == 0) {
                        System.out.println("There are no any books in DB !!!");
                    } else {
                        System.out.println(BookDataFactory.getListBooks(IFileInfo.absolutePath));
                    }
                    break;

                case 0:
                    isContinue = false;
                    break;

                default:
                    System.out.println("Option is not in menu ---- Please choose right option");
                    break;
            }
        }

    }
}
