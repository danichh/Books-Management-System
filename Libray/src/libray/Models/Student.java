/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Models;


import java.util.Objects;


/**
 * The student Class
 * @author Danich Hang
 */
public class Student {
    private int stId;
    private String name;
    private String contactNumber;

    public Student() {
    }
    
    public Student(int stId, String name, String contactNumber) {
        this.stId = stId;
        this.name = name;
        this.contactNumber = contactNumber;
    }


    @Override
    public String toString() {
        String str = "";
        
        str += String.format("Student ID: %s\n", stId);
        str += String.format("Student Name: %s\n", name);
        str += String.format("Student Contact Number: %s\n", contactNumber);
        
        return str;
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.stId);
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.contactNumber);
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
        final Student other = (Student) obj;
        if (!Objects.equals(this.stId, other.stId))
            return false;
        if (!Objects.equals(this.name, other.name))
            return false;
        if (!Objects.equals(this.contactNumber, other.contactNumber))
            return false;
        return true;
    }
    
    public int getStId() {
        return stId;
    }

    public void setStId(int stId) {
        this.stId = stId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

}
