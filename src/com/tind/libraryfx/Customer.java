package com.tind.libraryfx;


public class Customer {

    private String name;
    private int age;
    private int number;
    private String email;
    private String adress;

    public Customer(String name, int age, int number, String email, String adress) {
        this.name = name;
        this.age = age;
        this.number = number;
        this.email=email;
        this.adress=adress;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getAdress() {
        return adress;
    }
    public void setAdress(String adress) {
        this.adress = adress;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}



