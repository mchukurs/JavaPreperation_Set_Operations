package com.chukurs;

import java.util.*;

public class MapViewsMain {
    public static void main(String[] args) {
        Map<String, Contact> contacts = new HashMap<>();
        ContactData.getData("phone").forEach(c -> contacts.put(c.name, c));
        ContactData.getData("email").forEach(c -> contacts.put(c.name, c));

        Set<String> keysView = contacts.keySet();
        System.out.println(keysView);

        //when using constructor you get COPY not a VIEW
        Set<String> copyOfKeys = new TreeSet<>(contacts.keySet());
        System.out.println(copyOfKeys);

        if (contacts.containsKey("Linus Van Pelt")) {
            System.out.println("Linus got info ");
        }
        //removing this from KEYSVIEW
        keysView.remove("Daffy Duck");
        System.out.println(keysView);
        System.out.println("-".repeat(20));
        contacts.forEach((k, v) -> System.out.println(v));

        //removing this from copy of keys
        copyOfKeys.remove("Linus Van Pelt");
        System.out.println(copyOfKeys);
        System.out.println("-".repeat(20));
        contacts.forEach((k, v) -> System.out.println(v));

        //removing multiple people from keysView
        keysView.retainAll(List.of("Linus Van Pelt", "Charlie Brown", "Robin Hood", "Mickey Mouse"));
        System.out.println(keysView);
        System.out.println("-".repeat(20));
        contacts.forEach((k, v) -> System.out.println(v));

        keysView.clear();
        System.out.println("Cleared keysView: " + keysView);
        System.out.println("Cleared contacts: " + contacts);
        //adding to 'contacts' hashmap
        ContactData.getData("email").forEach(c -> contacts.put(c.name, c));
        ContactData.getData("phone").forEach(c -> contacts.put(c.name, c));
        System.out.println();
        System.out.println("Contacts after adding email and phone: ");
        contacts.forEach((k, v) -> System.out.println(v));
        System.out.println();
        System.out.println("keys view " + keysView);
        System.out.println();
        var values = contacts.values();
        values.forEach(System.out::println);
        System.out.println("now retaining the values WHICH ARE EMAIL");
        values.retainAll(ContactData.getData("email"));
        System.out.println("keys view " + keysView);
        contacts.forEach((k, v) -> System.out.println(v));
        System.out.println("-".repeat(20));

        //
        List<Contact> list = new ArrayList<>(values);
        list.sort(Comparator.comparing(Contact::getNameLastFirst));
        list.forEach(c -> System.out.println(c.getNameLastFirst() + ": " + c));

        System.out.println("-".repeat(20));
        //adding duplicate contact under different KEY name
        Contact first = list.get(0);
        contacts.put(first.getNameLastFirst(), first);
        values.forEach(System.out::println);
        keysView.forEach(System.out::println);
        //hashset
        Set<Contact> set = new HashSet<>(values);
        set.forEach(System.out::println);
        if (set.size() < contacts.keySet().size()) {
            System.out.println("Duplicates found");
        }
        //entry set
        var nodeSet = contacts.entrySet();
        for (var node : nodeSet) {
            System.out.println(nodeSet.getClass().getName());
            if(!node.getKey().equals(node.getValue().getName())){
                System.out.println(node.getClass().getName());
                System.out.println(node + " is a duplicate");
            }
        }

    }
}
