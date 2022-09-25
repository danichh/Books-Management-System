/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Models;


import java.util.Random;
import libray.Models.Student;
import libray.Models.Books;

/**
 * Record of the Book that are Issued
 * @author Danich Hang
 */
public class IssuedBooks extends Books{
    private int ID; 
    private String SN;
    private Student student; 
    private String IssuedDate;

    public IssuedBooks() {
    }
    
    public IssuedBooks(String SN, Student student, String IssuedDate) {
        this.ID = getRandomID();
        this.SN = SN;
        this.student = student;
        this.IssuedDate = IssuedDate;
    }
    
    /**
     * Generate a Random Id for the Issued Book ID
     * @return Random Number
     */
    public int getRandomID(){
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);

    }
    
    @Override
    public String toString() {
        return "IssuedBooks{" + "ID=" + ID + ", SN=" + SN + ", student=" + student + ", IssuedDate=" + IssuedDate + '}';
    }
    
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getIssuedDate() {
        return IssuedDate;
    }

    public void setIssuedDate(String IssuedDate) {
        this.IssuedDate = IssuedDate;
    }

}
