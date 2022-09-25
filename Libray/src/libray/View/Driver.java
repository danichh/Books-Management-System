/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.View;

import com.sun.imageio.plugins.jpeg.JPEG;
import libray.Models.IssuedBooks;
import libray.Models.Librarian;
import libray.Models.Student;
import libray.Models.Books;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import libray.Controllers.BookController;
import libray.Controllers.StudentController;
import libray.DBConnection;

/**
 *
 * @author Danich Hang
 */
public class Driver {
    static String languageChoice;
    static ResourceBundle res;
    static Librarian librarian;
    static Student student;
    static StudentController studentController = new StudentController();
    static BookController bookController = new BookController();
     
    
    
    public static void main(String[] args) throws Exception {
        Scanner console = new Scanner(System.in);
        DBConnection.getSingleInstance();
        
        //ask for which language the user prefer
        do{
            System.out.println("English or Français");
            languageChoice = console.next();
        }while(!validOption(languageChoice.toUpperCase()));
        
        //create the Local object depende of the input
        Locale locale = languageChoice.toUpperCase().contains("EN") ? 
                new Locale("en", "CA") :  new Locale("fr", "CA");
        res = ResourceBundle.getBundle("Libray/Source", locale);
        System.out.println(res);
        
        String userOption = askWhichUser();
        if(userOption.equals("1")){
            int ID = askStudentID();
            student = studentController.getStudent(ID);
            studentMenu();
        }else if(userOption.equals("2")) {
            librarianMenu();
        } else {
            System.out.println("error");
        }
 
    }
    
/*================================RETRIEVE DATA==================================*/    
    /**
     * Return an ArrayList of all the book in the database
     * @return
     * @throws Exception 
     */
    public static ArrayList<Books> retrieveData() throws Exception{
        ArrayList<Books> books  = new ArrayList<>();
    
         Map<String,String> map = bookController.viewCatalog();
         for(String m : map.keySet()){
            String key = m;
            String[] value = map.get(m).split("-");
             books.add(new Books(key,value[0],value[1],value[2],
                     Double.parseDouble(value[3]),Integer.parseInt(value[4]),
                     Integer.parseInt(value[5])));
        }
         return books;
    }
    
    /**
     * Return book  base on the given SN
     * @param SN is the book SN
     * @return
     * @throws Exception 
     */
    private static Books retriveBooks(String SN) throws Exception{
        Map<String,String> booksMap = bookController.viewCatalog();
        Books book = new Books();
        if(booksMap.containsKey(SN)){
            String[] value = booksMap.get(SN).split("-");
             book = new Books(SN,value[0],value[1],value[2],
                     Double.parseDouble(value[3]),Integer.parseInt(value[4]),
                     Integer.parseInt(value[5]));
        }
        return book;
    }
    /**
     * return a IssuedBook base on the given SN
     * @param SN is the book SN
     * @return
     * @throws Exception 
     */
    public static IssuedBooks retriveIssuedBooks(String SN) throws Exception {
        Map<String,String> booksMap = bookController.viewIssuedBooks();
        IssuedBooks issuedBook = new IssuedBooks();
        if(booksMap.containsKey(SN)){
             String[] value = booksMap.get(SN).split("-");
             issuedBook.setID(Integer.parseInt(value[0]));
             issuedBook.setSN(SN);
             issuedBook.setStudent(new Student(Integer.parseInt(value[1]), value[2], value[3]));
             issuedBook.setIssuedDate(value[4]);
        }
        return issuedBook;
    }
/*=====================================END=========================================*/
    /**
     * Valid the Language Input
     * @param str
     * @return 
     */ 
    private static boolean validOption(String str){
        ArrayList<String> validOption = new ArrayList<>();
        validOption.add("EN");
        validOption.add("FR");
        validOption.add("ENGLISH");
        validOption.add("FRENCH");
        validOption.add("FRANCAIS");
        validOption.add("FRANÇAIS");
        validOption.add("ANGLAISE");
        validOption.add("ANGLAIS");
        return validOption.contains(str);
    }
    
    /**
     * get the user to identify themself as a student or the Librarian
     * @return 1 to represent student; 2 to represent Librarian
     */
    private static String askWhichUser(){
        Scanner console = new Scanner(System.in);
         String valid = "1-2";
        String option = ""; 
        
        do{
             System.out.println(res.getString("user"));
             System.out.println(res.getString("enter"));
             System.out.println(res.getString("aksUserChoice1"));
             System.out.println(res.getString("aksUserChoice2"));
             System.out.println(res.getString("option"));
             option = console.next();

        }while(!valid.contains(option + ""));
        
        return option;
    }
    /**
     * Ask the user the necessary info to issued a Book 
     * and issued it
     * @param flag to identify if we use it with the student or Librarian
     * s for student , l for librarian
     * @throws Exception 
     */
    private static void askInfoIssueBook(String flag) throws Exception{
        Scanner console = new Scanner(System.in);
        System.out.println(res.getString("askSn"));
        String SN = console.next();
        
        if(flag.equalsIgnoreCase("s")){
            bookController.issueBook(retriveBooks(SN), student);
        }else if (flag.equalsIgnoreCase("l")){
            System.out.println(res.getString("askStudentID"));
            int ID = console.nextInt();
            System.out.println(studentController.getStudent(ID));

            bookController.issueBook(retriveBooks(SN), studentController.getStudent(ID));
        }
        
    }
    
    /**
     * Ask the user the necessary info to return a Book 
     * and return it
     * @param flag to identify if we use it with the student or Librarian
     * s for student , l for librarian
     * @throws Exception 
     */
    private static void askInfoReturnBook(String flag) throws Exception{
        Scanner console = new Scanner(System.in);
        System.out.println(res.getString("askSNReturn"));
        String SN = console.next();
         if(flag.equalsIgnoreCase("s")){
            bookController.returnBook(retriveBooks(SN), student);
        }else if (flag.equalsIgnoreCase("l")){
            System.out.println(res.getString("askStudentID"));
            int ID = console.nextInt();
            System.out.println(studentController.getStudent(ID));

           bookController.returnBook(retriveBooks(SN), studentController.getStudent(ID));
        }
    }

/*=====================================Librarian==================================*/
    /**
     * get the user to enter the wanted option and preform the task
     * @throws Exception 
     */ 
    private static void librarianMenu() throws Exception{
        Scanner console = new Scanner(System.in);
        int optionLibarain = 0;
        do {      
            // choice librarian 
            librarianMenuOption();  
            String valid = "123456";
        
            do{
                System.out.println(res.getString("option"));
                optionLibarain = console.nextInt();
            }while(!valid.contains(optionLibarain + ""));
        
            ArrayList<Books> model = retrieveData();
            librarian = new Librarian(model, bookController);
            switchThroughOptionLibarian(optionLibarain);
            
        } while (optionLibarain != 6);
        
    }
    
     /**
      * Output the Menu Option 
      */
    private static void librarianMenuOption(){
        System.out.println("Menu:");
        System.out.println(res.getString("addBook")); 
        System.out.println(res.getString("issueBook")); 
        System.out.println(res.getString("returnBook")); 
        System.out.println(res.getString("viewCatalog")); 
        System.out.println(res.getString("viewIssuedBooks")); 
        System.out.println("6." + res.getString("exit")); 
    }
    
    /**
     * perform the necessary task base on the user input
     * @param option is the user input
     * @throws Exception 
     */
    private static void switchThroughOptionLibarian(int option) throws Exception {
        switch(option){
            case 1:
                bookController.addBook(askBookInfo());
                System.out.println(res.getString("finishAdd") + "\n");
                break;
            case 2:
                askInfoIssueBook("l");
                System.out.println(res.getString("finishIssued") + "\n");
                break;
            case 3:
                askInfoReturnBook("l");
                System.out.println(res.getString("finishReturn"));
                break;
            case 4: 
                librarian.printBook(res);
                break;
            case 5:
                librarian.printIssuedBook(res);
                break;
        }
    }
    
    /**
     * ask all the info about the added book and create a book base on the info
     * @return a book
     */
     private static Books askBookInfo() {
        Scanner console = new Scanner(System.in);
        System.out.println(res.getString("bookInfo"));
        System.out.print("SN:");
        String SN = console.nextLine();
        System.out.print(res.getString("title"));
        String Titre = console.nextLine();
        System.out.print(res.getString("author"));
        String author = console.nextLine();
        System.out.print(res.getString("publisher"));
        String publisher = console.nextLine();
        System.out.print(res.getString("price"));
        double price = Double.parseDouble(console.next());
        System.out.print(res.getString("quantity"));
        int quantity = Integer.parseInt(console.next());
        
        Books book = new Books(SN, Titre, author, publisher, price, quantity, 0); 
        return book;
    }
    
    
/*=====================================END=========================================*/    
    
/*=====================================Student==================================*/   
    /**
     * get the user to enter the wanted option and preform the task
     * @throws SQLException
     * @throws Exception 
     */
    private static void studentMenu() throws SQLException, Exception{
         Scanner console = new Scanner(System.in);
        
        int optionStudent = 0;
        do {      
            // choice librarian 
            studentMenuOption();  
            String valid = "12345";
        
            do{
                System.out.println(res.getString("option"));
                optionStudent = console.nextInt();
            }while(!valid.contains(optionStudent + ""));
            switchThroughStudentMainMenu(optionStudent);
        
        } while (optionStudent != 5);
    }
    /**
     * ask the Student ID Keep ask until they have the right id
     * @return
     * @throws Exception 
     */
    private static int askStudentID() throws Exception{
        Scanner console = new Scanner(System.in);
        System.out.println(res.getString("askStudentID"));
            int ID = console.nextInt();
            while(!studentController.allStudent().containsKey(ID)){   
                 System.out.println(res.getString("unknownId"));
                System.out.println(res.getString("askStudentID"));
                ID = console.nextInt();
            }
               return ID;
            }
    
    /**
     * display the student menu Option
     */
    private static void studentMenuOption(){
        System.out.println("Menu:");
        System.out.println(res.getString("search")); 
        System.out.println(res.getString("issueBook")); 
        System.out.println(res.getString("returnBook")); 
        System.out.println(res.getString("viewCatalog"));  
        System.out.println("5." + res.getString("exit")); 
    }
    
     private static void switchThroughStudentMainMenu(int option) throws Exception {
        switch(option){
            case 1:
                searchMenu();
                studentMenu();
                break;
            case 2:
                askInfoIssueBook("s");
                System.out.println(res.getString("finishIssued") + "\n");
                break;
            case 3:
                askInfoReturnBook("s");
                System.out.println(res.getString("finishReturn"));
                break;
            case 4: 
                System.out.println(res);
                Librarian l = new Librarian();
                l.printBook(res);
                break;
        }
    }
     
    private static void searchMenuOption(){
        System.out.println(res.getString("choices")); 
        System.out.println(res.getString("bSearch")); 
        System.out.println(res.getString("NSearch")); 
        System.out.println(res.getString("PSearch"));
        System.out.println("4." + res.getString("exit"));
    } 
     
    private static void searchMenu() throws Exception{
        Scanner console = new Scanner(System.in);
        int option = 0;
        do {      
            
            searchMenuOption();
            String valid = "1234";
        
            do{
                System.out.println(res.getString("option"));
                option = console.nextInt();
            }while(!valid.contains(option + "")); 
            switchThroughSearchMenu(option);
            
        
        } while (option != 4);
    
    }
    
    private static void switchThroughSearchMenu(int option) throws Exception{
        Scanner console = new Scanner(System.in);
        switch(option){
            case 1:
                System.out.println(res.getString("askTitle"));
                String title = console.nextLine();
                List<Books> result = bookController.SearchBookByTitle(title);
                for(Books book : result){
                    System.out.println(book.toString(res));
                }
                break;
            case 2:
                System.out.println(res.getString("askName"));
                String name = console.nextLine();
                List<Books> result1 = bookController.SearchBookByName(name);
                for(Books book : result1){
                    System.out.println(book.toString(res));
                }
             
                break;
            case 3:
                System.out.println(res.getString("askPublisher"));
                String publisher = console.nextLine();
                List<Books> result2 = bookController.SearchBookByPublisher(publisher);
                for(Books book : result2){
                    System.out.println(book.toString(res));
                }
                break;
        }
    }
/*=====================================END=========================================*/      
}
    



 