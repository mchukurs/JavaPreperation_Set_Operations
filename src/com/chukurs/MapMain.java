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
        System.out.println("-".repeat(20));
        System.out.println("Learning about .computer() and .computeIf()");
        //looping over ArrayList of Strings
        //1 exists in contact list ,the other 2 dont
//        for (String contactName : new String[]{"Daisy Duck", "Daffy Duck", "Scrooge McDuck"}) {
//            //compute() takes in K and bifunction
//            contacts.compute(contactName, (k, v) -> new Contact(k));
//        };
//        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        for (String contactName : new String[]{"Daisy Duck", "Daffy Duck", "Scrooge McDuck"}) {
            //compute() takes in K and bifunction
            contacts.computeIfAbsent(contactName, (k) -> new Contact(k));
        }
        ;
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));

        //family business part
        for (String contactName : new String[]{"Daisy Duck", "Daffy Duck", "Scrooge McDuck"}) {
            //compute() takes in K and bifunction
            contacts.computeIfPresent(contactName, (k, v) -> {
                v.addEmail("Fun Place");
                return v;
            });
        }
        ;
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        System.out.println("-".repeat(20));

        //replaceAll on map is similar to replaceAll on list
        contacts.replaceAll((k, v) -> {
            //for the KEY of type STRING we remove spaces and append domain
            String newEmail = k.replaceAll(" ", "") + "@funplace.com";
            //for the VALUE we replace email if it exists using our own method
            v.replaceEmailIfExists("DDuck@funplace.com", newEmail);
            return v;
        });
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        System.out.println("-".repeat(20));
        //contact name = Daisy Jane Duck, and email = daisyj@duck.com
        Contact daisy = new Contact("Daisy Jane Duck", "daisyj@duck.com");
        //now we want to add this contact to map for DAISY DUCK (ignoring middle name or initial)
        // call REPLACE on contacts map
        Contact replacedContact = contacts.replace("Daisy Duck", daisy);
        System.out.println("daisy =" + daisy);
        System.out.println("replacedContact =" + replacedContact);
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        //above the VALUE was changed. THere's overloaded method to replace only if KEY:VALUE match
        System.out.println("-".repeat(20));
        Contact updatedDaisy = replacedContact.mergeContactData(daisy);
        System.out.println("updatedDaisy =" + updatedDaisy);
        boolean success = contacts.replace("Daisy Duck", daisy, updatedDaisy);
        if (success) {
            System.out.println("Successfully replaced element");

        } else {
            System.out.printf("Did not match on both key: %s and value: %s %n".formatted("Daisy Duck", replacedContact));
        }
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));
        //also REMOVE is present on map
        //1st version takes KEY and return the VALUE that was removed (or null)
        //2nd version takes KEY and VALUE and returns boolean
        System.out.println("-".repeat(20));
        success = contacts.remove("Daisy Duck", daisy);
        if (success) {
            System.out.println("Successfully removed element");

        } else {
            System.out.printf("Did not match on both key: %s and value: %s %n".formatted("Daisy Duck", daisy));
        }
        contacts.forEach((k, v) -> System.out.println("key= " + k + ", value = " + v));



    }
}
