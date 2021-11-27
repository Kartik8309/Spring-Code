package com.kartik.springtry20nov.DataBaseOperations;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.kartik.springtry20nov.DAO.Persons;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBOperations {
    public static volatile Connection connection;

    //Get connection from db
    public static Connection getConnection() throws SQLException {
        if(connection==null){
            synchronized(DBOperations.class){
                if(connection == null) {
                    connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/person", "root", "pass");
                }
            }
        }
        return connection;
    }

    //creating a new person
    public static boolean createPerson(int id , String name , int age , int salary) throws SQLException {
        getConnection();
        String query =  "INSERT INTO Person(id,name,age,salary) VALUES(?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.setString(2, name);
        statement.setInt(3, age);
        statement.setInt(4, salary);
        int rowsAffected =  statement.executeUpdate();
        System.out.println(rowsAffected);
        return (rowsAffected>0) ? true : false;
    }

    //fetching all persons
    public static List<Persons> allPersons() throws SQLException {
        getConnection();
        Statement statement = connection.createStatement();
        ResultSet persons = statement.executeQuery("SELECT * FROM Person");
        List<Persons> employeesList = new ArrayList<>();
        while(persons.next()){
            int id = persons.getInt(1);
            String name = persons.getString(2);
            int age = persons.getInt(3);
            int salary = persons.getInt(4);
            Persons employee = new Persons(id, name, age, salary);
            employeesList.add(employee);
        }
        return employeesList;
    }

    //delete a person with given id
    public static Boolean deletePerson(int id) throws SQLException {
        getConnection();
        String query = "DELETE FROM Person WHERE id=(?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        System.out.println(rowsAffected);
        if(rowsAffected > 0){
            return true;
        }
        else{
            return false;
        }
    }

    //updating a person's fields with given id
    public static Boolean updatePerson(int id,String field,String newValue) throws SQLException {
        getConnection();
        String query  = "UPDATE Person SET "+field+" = "+"'"+ newValue+ "'"+ " WHERE id = "+id+";";
        System.out.println(query);
        Statement statement = connection.createStatement();
        int isUpdated = statement.executeUpdate(query);
        if(isUpdated > 0){
            return true;
        }
        else{
            return false;
        }
    }
}
