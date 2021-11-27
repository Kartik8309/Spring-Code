package com.kartik.springtry20nov.Controller;

import java.sql.SQLException;
import java.util.List;

import com.kartik.springtry20nov.DAO.Persons;
import com.kartik.springtry20nov.DataBaseOperations.DBOperations;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @PostMapping("/createPerson")
    public ResponseEntity<Boolean> createPerson(@RequestParam int id , @RequestParam String name , @RequestParam int age , @RequestParam int salary ) throws SQLException {
        boolean personCreated = DBOperations.createPerson(id , name , age , salary);
        if(personCreated) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Successful", "Person created Successfully");
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<Boolean>(true,headers,status);
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error","Person could not be created");
            HttpStatus status = HttpStatus.BAD_GATEWAY;
            return new ResponseEntity<Boolean>(false,headers,status);
        }
    }

    @GetMapping("/allPersons")
    public ResponseEntity<List<Persons>> allPersons() throws SQLException {
        List<Persons> personsList = DBOperations.allPersons();
        if(!personsList.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Fetched Successfully", "Persons fetched sucessfully");
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<>(personsList,headers,status); 
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error","Could not fetch");
            HttpStatus status = HttpStatus.BAD_REQUEST;
            return new ResponseEntity<>(null,headers,status);
        }
    }

    @DeleteMapping("/deletePerson/{id}")
    public ResponseEntity<Boolean> deletePerson(@PathVariable int id) throws SQLException {
        Boolean isDeleted = DBOperations.deletePerson(id);
        if(isDeleted) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Successful", "Person deleted Successfully");
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<Boolean>(true,headers,status);
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error","Person could not be deleted");
            HttpStatus status = HttpStatus.BAD_GATEWAY;
            return new ResponseEntity<Boolean>(false,headers,status);
        }
    }

    @PostMapping("/updatePerson")
    public ResponseEntity<Boolean> updatePerson(@RequestParam int id , @RequestParam String field , @RequestParam String newValue) throws SQLException {  
        boolean isUpdated = DBOperations.updatePerson(id, field, newValue);
        if(isUpdated){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Successful", "Person updated Successfully");
            HttpStatus status = HttpStatus.OK;
            return new ResponseEntity<Boolean>(true,headers,status);
        }
        else{
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error","Person could not be updated");
            HttpStatus status = HttpStatus.BAD_GATEWAY;
            return new ResponseEntity<Boolean>(false,headers,status);
        }
    }

}
