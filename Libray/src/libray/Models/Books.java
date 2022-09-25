/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Models;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import libray.DBConnection;

/**
 * Books class
 * @author Danich Hang
 */
public class Books {
    private String SN;
    private String title;
    private String author;
    private String publisher;
    private double price;
    private int qte;
    private int issueQte;
    private Date DateOfPurchase;
    static Connection con;
    
    /**
     * Default constructor 
     */
    public Books(){
        
    }
    
    /**
     * constructor with those parameter 
     * @param SN
     * @param title
     * @param author
     * @param publisher
     * @param price
     * @param qte
     * @param issueQte 
     */
    public Books(String SN, String title, String author, String publisher, double price, int qte, int issueQte) {
        this.SN = SN;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.price = price;
        this.qte = qte;
        this.issueQte = issueQte;
        try {
            this.con = DBConnection.getSingleInstance();
        } catch (Exception ex) {
            Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Generate a Random Id for the Issued Book ID
     * @return Random Number
     */
    public int getRandomID(){
        Random r = new Random( System.currentTimeMillis() );
        return 10000 + r.nextInt(20000);

    }

    public String toString(ResourceBundle res) {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String str = "";
        str += String.format("SN: %s\n", SN);
        str += String.format("%s: %s\n",res.getString("title"), title);
        str += String.format("%s: %s\n",res.getString("author"), author);
        str += String.format("%s: %s\n",res.getString("publisher"),publisher);
        str += String.format("%s: %s\n",res.getString("price"),price);
        str += String.format("%s: %s\n",res.getString("quantity"),qte);
        str += String.format("%s: %s\n",res.getString("issedqte"),issueQte);
        str += String.format("%s: %s\n",res.getString("addedDate"),DateOfPurchase);
        
    return str;
    }
   
  
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.SN);
        hash = 59 * hash + Objects.hashCode(this.title);
        hash = 59 * hash + Objects.hashCode(this.author);
        hash = 59 * hash + Objects.hashCode(this.publisher);
        hash = 59 * hash + Objects.hashCode(this.qte);
        hash = 59 * hash + Objects.hashCode(this.issueQte);
        hash = 59 * hash + Objects.hashCode(this.DateOfPurchase);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Books other = (Books) obj;
        if (!Objects.equals(this.SN, other.SN))
            return false;
        if (!Objects.equals(this.title, other.title))
            return false;
        if (!Objects.equals(this.author, other.author))
            return false;
        if (!Objects.equals(this.publisher, other.publisher))
            return false;
        if (!Objects.equals(this.qte, other.qte))
            return false;
        if (!Objects.equals(this.issueQte, other.issueQte))
            return false;
        if (!Objects.equals(this.DateOfPurchase, other.DateOfPurchase))
            return false;
        return true;
    }
    
    

    public String getSN() {
        return SN;
    }

    public void setSN(String SN) {
        this.SN = SN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getIssueQte() {
        return issueQte;
    }

    public void setIssueQte(int issueQte) {
        this.issueQte = issueQte;
    }

    public Date getDateOfPurchase() {
        return DateOfPurchase;
    }

    public void setDateOfPurchase(Date DateOfPurchase) {
        this.DateOfPurchase = DateOfPurchase;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    } 
}

/**
 * Too Sorted all the book by SN
 * @author Danich Hang
 */
class SortedBySN implements Comparator<Books>{

    @Override
    public int compare(Books t, Books t1) {
        return t.getSN().compareToIgnoreCase(t1.getSN());
    }
    
}
