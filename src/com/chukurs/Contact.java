package com.chukurs;

import java.util.HashSet;
import java.util.Set;

public class Contact {
    String name;
    Set<String> emails = new HashSet<>();
    Set<String> phones = new HashSet<>();


    public void addEmail(String companyName) {
        String[] names = name.split(" ");
        String email = "%c%s@%s.com".formatted(
                name.charAt(0),
                names[names.length - 1],
                companyName.replaceAll(" ", "").toLowerCase());
        if (!emails.add(email)) {
            System.out.println(email + " email already in the set\n");
        } else {
            System.out.println(email + " added to the set\n");
        }

    }

    public void replaceEmailIfExists(String oldEmail, String newEmail) {
        //hashSet does not include replace method, so this is a workaround
        if (emails.contains(oldEmail)) {
            emails.remove(oldEmail);
            emails.add(newEmail);
        }
    }

    public Contact(String name) {
        this(name, null);
    }

    public Contact(String name, String email) {
        this(name, email, 0);

    }

    public Contact(String name, long phone) {
        this(name, null, phone);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        return name.equals(contact.name);
    }

    @Override
    public int hashCode() {
        return 33 * name.hashCode();
    }

    public Contact(String name, String email, long phone) {
        this.name = name;
        if (email != null) {
            this.emails.add(email);
        }
        if (phone > 0) {
            String phoneString = Long.toString(phone);
            if (phoneString.length() == 10) {
                String result = "(%s) %s-%s".formatted(phoneString.substring(0, 3), phoneString.substring(3, 6), phoneString.substring(6));
                this.phones.add(result);
            }
        }

    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "%s: %s %s".formatted(name, emails, phones);
    }

    public Contact mergeContactData(Contact contact) {
        Contact newContact = new Contact(name);
        newContact.emails = new HashSet<>(this.emails);
        newContact.phones = new HashSet<>(this.phones);
        newContact.emails.addAll(contact.emails);
        newContact.phones.addAll(contact.phones);


        return newContact;
    }
}
