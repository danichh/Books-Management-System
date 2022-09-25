/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libray.Controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;
import libray.DBConnection;
import libray.Models.Student;

/**
 *
 * @author Danich Hang
 */
public class StudentController {

    public StudentController() {
    }
    
    
     /**
     * Return for the database the search student
     * @param ID is the id identify the student   
     * @return the search student
     * @throws SQLException 
     */
    public Student getStudent(int ID) throws SQLException{
         String query = "SELECT * FROM STudents WHERE StudentID=" + ID + ";";
         Student student = new Student();
         ResultSet rs = DBConnection.excuteQuery(query);
         while(rs.next()){
             student.setStId(rs.getInt("StudentId"));
             student.setName(rs.getString("Name"));
             student.setName(rs.getString("Contact"));
        }
         
        return student;
    }
     /**
     * return all student in the record
     * @return
     * @throws Exception 
     */
    public Map<Integer, String> allStudent() throws Exception{
        Map<Integer, String> output = new TreeMap<>();
        String query = "SELECT * FROM Students;";
        ResultSet rs = DBConnection.excuteQuery(query);
        while(rs.next()){
            String value = String.format("Student Name: %s\n"
                    + "Student Contact: %s\n",
                    rs.getString("Name"),
                    rs.getString("Contact"));
            output.put(rs.getInt("StudentId"), value);
        } 
        return output;
    }
    
}
