package com.chukurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        System.out.println("-".repeat(20));

        //start with map
        Map<String, Contact> contacts = new HashMap<>();
        //loop through arrayList and use .put() to insert an entry into the map
        for (Contact contact : fullList) {
            //using name as the key, contact object as the value
            contacts.put(contact.name, contact);
        }
        //to print a MAP we can use forEach
        //no duplicates as KEY is always unique
        //no specific order
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        //in MAP the .put() always PUTs it there. It replaces the existing record and
        //returns the previous value
        //The last ELEMENT in list being added to the MAP will be in MAP

        System.out.println("-".repeat(20));
        //can use .get() to retrieve a key-value using the key

        System.out.println(contacts.get("Charlie Brown"));
        System.out.println(contacts.get("Matiss Cukurs"));
        //returns NULL if not found using the KEY
        //jdk8 introduced a "DEFAULT VALUE" that is returned if NULL found
        System.out.println(contacts.getOrDefault("Matiss Cukurs", new Contact("RANDOM")));
        //can use .clear() to remove all entries
        contacts.clear();

        for (Contact contact : fullList) {
            Contact duplicate = contacts.put(contact.name, contact);
            //if .put() found that the KEY is present, it replaces the VALUE with new object and returns OLD value
            //if .put() did not find KEY, it adds it, and returns NULL
            if (duplicate != null) {
                //System.out.println("The duplicate is "+ duplicate+" and is replaced by "+contact);
                //Instead of replacing, we can call to MergeContact and add it
                System.out.println("Duplicate found so merging " + contact);
                contacts.put(contact.name, contact.mergeContactData(duplicate));
            } else {
                System.out.println("The new object added is " + contact + " as no duplicate was found");
            }
        }
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        //If want to stick with 1st KEY:VALUE then use putIfAbsent
        contacts.clear();
        System.out.println("-".repeat(20));
        for (Contact contact : fullList) {
            Contact duplicate = contacts.putIfAbsent(contact.name, contact);
        }
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));

        contacts.clear();
        System.out.println("-".repeat(20));
        for (Contact contact : fullList) {
            Contact duplicate = contacts.putIfAbsent(contact.name, contact);
            if (duplicate != null) {
                contacts.put(contact.name, contact.mergeContactData(duplicate));
            }
        }
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));

        contacts.clear();
        System.out.println("-".repeat(20));
        //can do the same with lambda
        //merge from ARRAYLIST to MAP with single statement
        fullList.forEach(contact -> contacts.merge(contact.name, contact,
                Contact::mergeContactData
                ));

        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
    }
}
