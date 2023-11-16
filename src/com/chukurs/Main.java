package com.chukurs;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        List<Contact> emails = ContactData.getData("email");
        printData("email data", emails);
        List<Contact> phones = ContactData.getData("phone");
        printData("phone data", phones);
        System.out.println("-".repeat(10) + " NOW moving from Lists to Sets");
        //Crate hashSets from the Lists created by reading the String (can be created this way as both ar form of Collection)
        Set<Contact> emailContacts = new HashSet<>(emails);
        Set<Contact> phoneContacts = new HashSet<>(phones);
        /*
        printData method takes any Collection,
        looks like duplicates are present in HashSet but that is not true. They are not duplicates as Hash is different
        + .equal() is false
        */
        printData("Email contacts", emailContacts);
        printData("Phone contacts", phoneContacts);
        int index = emails.indexOf(new Contact("Robin Hood"));
        Contact robinHood = emails.get(index);
        robinHood.addEmail("Sherwood forest");
        //test if we can add a duplicate
        robinHood.addEmail("Sherwood forest");
        //test the replace method
        robinHood.replaceEmailIfExists("RHood@sherwoodforest.com", "RHood@sherwoodforest.net");
        System.out.println(robinHood);
 /*
        Collection interfaces bulk operations:
        1- addAll
        2- retainAll
        3- removeAll
        4- containAll
        can be used to get data thats in every set, or the unique data

        UNION - unique data from all sets (java does not have UNION function on Collections but addAll can do the job)
        We can get union of phones and emails (based on name)

        INTERSECTION - only data found in all SETS
         */
        //UNION
        Set<Contact> unionAB = new HashSet<>();
        unionAB.addAll(emailContacts);
        System.out.println(unionAB);
        unionAB.addAll(phoneContacts);
        System.out.println("email and phone \u222A " + unionAB);
        System.out.println(unionAB.size());
        //INTERSECTION
        Set<Contact> intersectionAB = new HashSet<>(emailContacts);
        //will remove the items that are not found in the parameter list
        intersectionAB.retainAll(phoneContacts);
        System.out.println("email and phone \u2229 " + intersectionAB);
        //operation is SYMMETRIC because AB and BA yield the same results (based on hash)

        //DIFFERENCE (subtract) (only unique items from 1st set are left), this is ASYMMETRIC operation
        Set<Contact> subtractAB = new HashSet<>(emailContacts);
        subtractAB.removeAll(phoneContacts);
        System.out.println(subtractAB);
        Set<Contact> subtractBA = new HashSet<>(phoneContacts);
        subtractBA.removeAll(emailContacts);
        System.out.println(subtractBA);
        //symmetric difference (union of symmetric differences)

        Set<Contact> symtericDifferenceEmail = new HashSet<>(emailContacts);
        symtericDifferenceEmail.removeAll(phoneContacts);

        Set<Contact> symtericDifferencePhone = new HashSet<>(phoneContacts);
        symtericDifferencePhone.removeAll(emailContacts);

        Set<Contact> symtericDifferenceUnion = new HashSet<>(symtericDifferenceEmail);
        symtericDifferenceUnion.addAll(symtericDifferencePhone);

        System.out.println("only the items which were not in both "+ symtericDifferenceUnion);

        Set<Contact> symmetricDifferenceUnion2 = new HashSet<>(unionAB);
        symmetricDifferenceUnion2.removeAll(intersectionAB);
        System.out.println(symmetricDifferenceUnion2);

    }

    public static void printData(String header, Collection<Contact> contacts) {
        System.out.println("-".repeat(10));
        System.out.println(header);
        System.out.println("-".repeat(10));
        contacts.forEach(System.out::println);
    }
}
