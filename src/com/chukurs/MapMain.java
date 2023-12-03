package com.chukurs;

import java.util.ArrayList;
import java.util.List;

public class MapMain {
    public static void main(String[] args) {
        //get phones contacts
        List<Contact> phones = ContactData.getData("phone");
        //get emails contacts
        List<Contact> emails = ContactData.getData("email");
        //create arrayList of type Contact and pass into constructor a Collection
        List<Contact> fullList = new ArrayList<>(phones);
        //add them together in one ArrayList
        fullList.addAll(emails);
        //print them to see that there are duplicates as List allows that
        fullList.forEach(System.out::println);

    }
}
