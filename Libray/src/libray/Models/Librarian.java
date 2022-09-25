/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Models;

import libray.Models.Books;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import libray.Controllers.BookController;

/**
 * The controller class of Books
 * @author Danich Hang
 */
public class Librarian {
    ArrayList<Books> model;
    BookController bookController = new BookController();

    public Librarian() {
    }

    public Librarian(ArrayList<Books>model, BookController bookController) {
        this.model = model;
        this.bookController = bookController;
    }

    /**
     * Print all the Books records  
     * @param res
     * @throws Exception 
     */
    public void printBook(ResourceBundle res) throws Exception{
        Map<String,String> map = bookController.viewCatalog();
         for(String key : map.keySet()){
            String[] value = map.get(key).split("-");
            
            System.out.println("SN: "+ key);
            System.out.println(res.getString("title") + value[0]);
            System.out.println(res.getString("author")+ value[1]);
            System.out.println(res.getString("publisher")+ value[2]);
            System.out.println(res.getString("price") + value[3]);
            System.out.println(res.getString("quantity") + value[4]);
            System.out.println(res.getString("issedqte") + value[5]);
            System.out.println(res.getString("addedDate")+ value[6] + "\n");
        }
    }
    
    /**
     * Print all the IssuedBooks records 
     * @param res
     * @throws Exception 
     */
    public void printIssuedBook(ResourceBundle res) throws Exception{
        Map<String,String> map = bookController.viewIssuedBooks();
        for(String key : map.keySet()){
            String[] value = map.get(key).split("-");
            
            System.out.println("ID: " + value[0]);
            System.out.println("SN:" + key);
            System.out.println(res.getString("studentID") + value[1]);
            System.out.println(res.getString("studentName") + value[2]);
            System.out.println(res.getString("studentContact") + value[3]); 
            System.out.println(res.getString("issuedDate") + value[4] + "\n");
            
        }
    }
}

