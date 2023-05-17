import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookDataFactory {

    public static void checkAndCreateFile(String filePath) {
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("File created at: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Failed to create file: " + e.getMessage());
            }
        } else {
            System.out.println("File already exists at: " + file.getAbsolutePath());
        }
    }
    public static List<Book> getListBooks(String filePath) {

        List<Book> listBooks = new ArrayList<>();
        try (
                FileInputStream fileInputStream = new FileInputStream(filePath);
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        ) {
            String dataLine = bufferedReader.readLine();
            while (dataLine != null) {
                String[] bookData = dataLine.split(";");
                String isbn = bookData[0];
                String title = bookData[1];
                String author = bookData[2];
                int year = Integer.parseInt(bookData[3]);
                Book book = new Book(isbn, title, author, year);
                listBooks.add(book);
                dataLine = bufferedReader.readLine();
            }

        } catch (FileNotFoundException e) {
            System.out.println("[ERR] File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listBooks;
    }
    public static void saveBookList(List<Book> bookList, String filePath) {

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        ) {
            for (Book book : bookList) {
                String dataLine = book.getIsbn() + ";" + book.getTitle() + ";" + book.getAuthor() + ";" + book.getYear();
                bufferedWriter.write(dataLine);
                bufferedWriter.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("[ERR] File not found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isBookExistInList(List<Book> bookList, String isbn) {

        if (bookList.size() == 0 ) {
            return false;
        }

        for (Book book : bookList) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                return true;
            }
        }
        return false;
    }
    public static List<Book> addNewBook(Book newBook, String filePath) {

        if (isBookExistInList(getListBooks(filePath), newBook.getIsbn())) {
            throw new RuntimeException("Book exist in list --- Please choose update function");
        }

        List<Book> newListBooksAdded = new ArrayList<>();
        newListBooksAdded.add(newBook); // add new book to first element
        newListBooksAdded.addAll(getListBooks(filePath)); // add all element in old list
        return newListBooksAdded;
    }
    public static List<Book> updateBook(Book updatedbook, String filePath) {

        List<Book> newListBookUpdated = new ArrayList<>(getListBooks(filePath));

        for (int indexToUpdate = 0; indexToUpdate < getListBooks(filePath).size(); indexToUpdate++) {

            if (getListBooks(filePath).get(indexToUpdate).getIsbn().equalsIgnoreCase(updatedbook.getIsbn())) {
                newListBookUpdated.set(indexToUpdate,updatedbook);
                break;
            }
        }
        return newListBookUpdated;
    }
    public static List<Book> deleteBook(String isbn, String filePath) {

        List<Book> newListBookDeleted = new ArrayList<>(getListBooks(filePath));

        for (int indexToDeleted = 0; indexToDeleted < getListBooks(filePath).size(); indexToDeleted++) {

            if (getListBooks(filePath).get(indexToDeleted).getIsbn().equalsIgnoreCase(isbn)) {
                newListBookDeleted.remove(indexToDeleted);
                System.out.printf("Book with ISBN<%s> is deleted successfully\n", isbn);
                break;
            }
        }

        return newListBookDeleted;
    }
    public static void findBook(String isbn, String filePath) {

        if (!isBookExistInList(getListBooks(filePath), isbn)) {
            System.out.printf("Book with ISBN %s is not found\n",isbn);
        } else {
            int indexBook = 0;
            while (indexBook < getListBooks(filePath).size()){

                if (getListBooks(filePath).get(indexBook).getIsbn().equalsIgnoreCase(isbn)) {
                    break;
                }
                indexBook++;
            }

            Book bookFound = getListBooks(filePath).get(indexBook);
            System.out.printf("Book {%s, %s, %s, %d}\n",bookFound.getIsbn(),bookFound.getTitle(),bookFound.getAuthor(),bookFound.getYear());
        }

    }
}
