/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import libray.Models.Books;
import libray.DBConnection;
import libray.Models.IssuedBooks;
import libray.Models.Student;


/**
 *
 * @author Danich Hang
 */
public class BookController {
    private StudentController studentController = new StudentController();

    public BookController() {
    }
    

    /**
     * method creates a new entry in the Books table to add a new book to 
     * the catalog, sets “Issued” attribute to zero and addedDate
     * to the current date.
     * @param book 
     */
    public void addBook (Books book) throws SQLException, Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        String query = "INSERT INTO Books(SN,Title,Author,Publisher,"
                + "Price,Quantity,Issued,AddedDate)VALUES('" 
                + book.getSN() + "','" 
                + book.getTitle() + "','" 
                + book.getAuthor() + "','" 
                + book.getPublisher() + "'," 
                + book.getPrice() + "," 
                + book.getQte() + "," 
                +  0 + ",'" 
                + formatter.format(new Date())+ "');";
 
            DBConnection.executeUpdate(query);
    }
    
    /**
     * To issue a book to a student, student information should be verified 
     * first. If the book is available (the value of “Issued” is greater than 
     * 0 in “Books” table), the number of copies(“Quantity”) will be decreased 
     * by one and the number of Copies issued (“Issued”) will be increased by 
     * one. A new entry in “IssuedBooks” table is added. The two methods 
     * return true if the book was successfully issued.
     * @param Book
     * @return 
     */
    public boolean issueBook (Books book, Student student) throws Exception{
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        Map<String,String> allBooks = viewCatalog();
        IssuedBooks issuedBook; 
        
        if(allBooks.containsKey(book.getSN())){
            book.setQte(book.getQte() - 1);
            book.setIssueQte(book.getIssueQte() + 1);
            updateBook(book);
            issuedBook = new IssuedBooks(book.getSN(), student, formatter.format(new Date()).toString());
            this.AddIssuedBook(issuedBook);
            return true;
        } else{
            return false;
        }
    }
    
     /**
     * Return book from the database base on the given SN
     * @param SN is the book SN
     * @return
     * @throws SQLException
     * @throws Exception 
     */
    public Books retriveBook(String SN) throws SQLException, Exception{
        String query = "SELECT * FROM BOOKS WHERE SN ='" + SN +"';";
        ResultSet rs = DBConnection.excuteQuery(query);
        Books book = new Books();
        while(rs.next()){
           book = new Books(rs.getString("SN"), 
                   rs.getString("Title"), 
                   rs.getString("Author"), 
                   rs.getString("Publisher"),
                   rs.getDouble("Price"), 
                   rs.getInt("Quantity"), 
                   rs.getInt("Issued"));
        } 
        return book;
    }
    
     /**
     * To return a book, check first if an entry in the issuedBooks table about
     * the book and the student exists that will verify the studentID. 
     * The number of copies will be increased by one and the number of copies
     * issued will be decreased by one. The corresponding record in IssuedBooks 
     * table is deleted from the table. The two methods return true if the book 
     * was successfully returned.
     * @param Book
     * @param student
     * @return 
     */
    public boolean returnBook (Books book, Student student) throws Exception{
        Map<String,String> issuedBooks = viewIssuedBooks();
        Map<Integer,String>  students = studentController.allStudent();
        Books updateDataBook  = retriveBook(book.getSN());
        
        if(issuedBooks.containsKey(book.getSN()) && students.containsKey(student.getStId())){
                updateDataBook.setQte(updateDataBook.getQte() - 1);
                updateDataBook.setIssueQte(updateDataBook.getIssueQte() + 1);
                updateBook(book);
                this.DeleteIssuedBook(book);
                return true;
        } else
            return false;
    }
    
     /**
     * Update the issued quantity and the quantity in the database  
     * @param book is the book needed to be update
     * @throws SQLException 
     */
    public void updateBook(Books book) throws SQLException{
        String query = "Update Books Set Quantity =" + book.getQte() + 
                ",Issued =" + book.getIssueQte() + " where SN = '" + book.getSN() + "';";
        
        DBConnection.executeUpdate(query);
    }
    
    
    /**
     * Return a list of book base on the given query
     * @param query 
     * @return list of books
     * @throws Exception 
     */
    public List<Books> SearchBook(String query) throws Exception {
        List<Books> output = new ArrayList<>();
        ResultSet rs = DBConnection.excuteQuery(query);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
        while(rs.next()){
            Books book = new Books( rs.getString("SN"),
                    rs.getString("Title"),
                    rs.getString("Author"),
                    rs.getString("Publisher"),
                    rs.getDouble("Price"),
                    rs.getInt("Quantity"),
                    rs.getInt("Issued"));
            book.setDateOfPurchase(formatter.parse(rs.getString("AddedDate")));
            output.add(book);
            
        }
        
        //sorted the lkist by SN
        output.stream().sorted((e1,e2) -> e1.getSN().compareToIgnoreCase(e2.getSN()));
        return output;
    }
    
    /**
     * Retrieve only records from Books table that satisfy the criteria and 
     * return them as a list sorted by “SN”.
     * @param title
     * @return 
     */
    public List<Books> SearchBookByTitle(String title) throws SQLException, Exception{
        String query = "SELECT * FROM BOOKS WHERE Title='" + title + "';";
        return SearchBook(query);
    } 
    
    /**
     * Retrieve only records from Books table that satisfy the criteria and 
     * return them as a list sorted by “SN”.
     * @param Name
     * @return 
     */
    public List<Books> SearchBookByName(String name) throws Exception{
        String query = "SELECT * FROM BOOKS WHERE Author='" + name + "';";
         return SearchBook(query);
    }
    
     
    /**
     * Retrieve only records from Books table that satisfy the criteria and 
     * return them as a list sorted by “SN”.
     * @param Publisher
     * @return 
     */
    public List<Books> SearchBookByPublisher(String Publisher) throws Exception{
        String query = "SELECT * FROM BOOKS WHERE Publisher='" + Publisher + "';";
        return SearchBook(query);
    }
    
    
             
    /**
     * This method returns a map containing all data retrieved from  
     * the Books table. The key in the map is “SN”. All books should be 
     * sorted by “SN”.Use the appropriate formatting for the date and currency
     * @return 
     * @throws java.sql.SQLException 
     */
    public Map<String, String> viewCatalog() throws SQLException, Exception{
        Map<String, String> output = new TreeMap<>();
        String query = "SELECT * FROM BOOKS;";
        ResultSet rs = DBConnection.excuteQuery(query);
        while(rs.next()){
            String value =  
                    rs.getString("Title") + "-" + 
                   rs.getString("Author") + "-" +
                    rs.getString("Publisher")+ "-" + 
                    rs.getDouble("Price")+ "-" +
                    rs.getInt("Quantity")+ "-" +
                    rs.getInt("Issued") + "-" +
                    rs.getString("AddedDate");
            output.put(rs.getString("SN"), value);
        } 

        return output;
    }
    
     /**
     * Retrieves all data from IssuedBooks table and returns them as a Map. 
     * The map is sorted by “SN”.
     * @return 
     * @throws java.lang.Exception 
     */
    public Map<String, String> viewIssuedBooks() throws Exception{
        Map<String, String> output = new TreeMap<>();
        String query = "SELECT * FROM IssuedBooks;";
        ResultSet rs = DBConnection.excuteQuery(query);
        while(rs.next()){
            String value = rs.getString("ID") + "-" +  
                    rs.getString("StudentId") + "-" + 
                    rs.getString("SudentName") + "-" + 
                    rs.getString("StudentContact") + "-" + 
                    rs.getString("IssueDate");
            output.put(rs.getString("SN"), value);
        } 

        return output;
    }
    
    
    /**
     * Add a IssuedBook in the Database
     * @param issuedBook is the given issuedBook
     * @throws SQLException
     * @throws Exception 
     */
     public void AddIssuedBook(IssuedBooks issuedBook) throws SQLException, Exception{
          String query = "INSERT INTO IssuedBooks(ID,SN,StudentId,SudentName,StudentContact,"
                + "IssueDate)VALUES(" 
                + issuedBook.getID() + ",'"   
                + issuedBook.getSN() + "','" 
                + issuedBook.getStudent().getStId() + "','" 
                + issuedBook.getStudent().getName() + "','" 
                + issuedBook.getStudent().getContactNumber() + "','" 
                + issuedBook.getIssuedDate() + "');";
        
         DBConnection.executeUpdate(query);
     }
    
    /**
     * To Delete a IssuedBooks 
     * @param book is the issuedBook that we want to delete
     * @throws Exception 
     */
    public void DeleteIssuedBook(Books book) throws Exception{
        String query = "Delete From IssuedBooks where SN='"+ book.getSN() + "';";
        DBConnection.executeUpdate(query);
    }
    
}
