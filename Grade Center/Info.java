/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

public class Info {

    private String subject;
    private double mark;
    private String grade;
    private String ins;
    private String code;
    
   
  
    
        
    public Info(){
        this.subject = "";
        this.mark = 0;
        this.grade = "";
        this.ins = "";
        this.code = "";
    }
    

    public Info(String subject, double mark, String grade, String ins, String code){
        this.subject = subject;
        this.mark = mark;
        this.grade = grade;
        this.ins = ins;
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public String getGrade() {
         String grade = "";
        if (mark >= 80.0)
            grade = "A";
        else if (mark >= 70.0)
            grade = "B";
        else if (mark >= 60.0)
            grade = "C";
        else if (mark >= 50.0)
            grade = "D";
        else
            grade = "F";
        
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
   
    
    
}