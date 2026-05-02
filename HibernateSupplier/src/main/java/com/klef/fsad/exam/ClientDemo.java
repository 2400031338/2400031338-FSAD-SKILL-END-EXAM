package com.klef.fsad.exam;

import java.util.Date;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ClientDemo {

    public static void main(String[] args) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Supplier Hibernate Operations =====");
            System.out.println("1. Insert Supplier");
            System.out.println("2. Update Supplier Name or Status by ID");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    Session session1 = factory.openSession();
                    Transaction tx1 = session1.beginTransaction();

                    System.out.print("Enter Supplier Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Description: ");
                    String description = sc.nextLine();

                    System.out.print("Enter Status: ");
                    String status = sc.nextLine();

                    

                    Supplier supplier = new Supplier();
                    supplier.setName(name);
                    supplier.setDescription(description);
                    supplier.setDate(new Date());
                    supplier.setStatus(status);
                    

                    session1.save(supplier);
                    tx1.commit();
                    session1.close();

                    System.out.println("Supplier inserted successfully.");
                    break;

                case 2:
                    Session session2 = factory.openSession();
                    Transaction tx2 = session2.beginTransaction();

                    System.out.print("Enter Supplier ID to update: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    Supplier s = session2.get(Supplier.class, id);

                    if (s != null) {
                        System.out.print("Enter New Supplier Name: ");
                        String newName = sc.nextLine();

                        System.out.print("Enter New Status: ");
                        String newStatus = sc.nextLine();

                        s.setName(newName);
                        s.setStatus(newStatus);

                        session2.update(s);
                        tx2.commit();

                        System.out.println("Supplier updated successfully.");
                    } else {
                        System.out.println("Supplier not found with ID: " + id);
                        tx2.rollback();
                    }

                    session2.close();
                    break;

                case 3:
                    factory.close();
                    sc.close();
                    System.out.println("Application closed.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}