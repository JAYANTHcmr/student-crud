package com.student.app;

import java.util.List;
import java.util.Scanner;

public class App {
    private static final Scanner sc = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("Student CRUD App (console)");
        while (true) {
            printMenu();
            String input = sc.nextLine().trim();
            if (input.isBlank()) continue;
            int choice;
            try { choice = Integer.parseInt(input); } catch (NumberFormatException e) { System.out.println("Enter a number."); continue; }
            try {
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> updateStudent();
                    case 3 -> deleteStudent();
                    case 4 -> viewStudent();
                    case 5 -> viewAll();
                    case 0 -> { System.out.println("Bye."); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Add Student");
        System.out.println("2. Update Student");
        System.out.println("3. Delete Student");
        System.out.println("4. View Student by ID");
        System.out.println("5. View All Students");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    private static void addStudent() throws Exception {
        System.out.print("Name: "); String name = sc.nextLine().trim();
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Course: "); String course = sc.nextLine().trim();
        System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine().trim());
        dao.addStudent(new Student(name, email, course, age));
        System.out.println("Student added.");
    }

    private static void updateStudent() throws Exception {
        System.out.print("ID to update: "); int id = Integer.parseInt(sc.nextLine().trim());
        Student s = dao.getStudentById(id);
        if (s == null) { System.out.println("Not found."); return; }
        System.out.print("Name ("+s.getName()+"): "); String name = sc.nextLine();
        System.out.print("Email ("+s.getEmail()+"): "); String email = sc.nextLine();
        System.out.print("Course ("+s.getCourse()+"): "); String course = sc.nextLine();
        System.out.print("Age ("+s.getAge()+"): "); String ageStr = sc.nextLine();

        if (!name.isBlank()) s.setName(name);
        if (!email.isBlank()) s.setEmail(email);
        if (!course.isBlank()) s.setCourse(course);
        if (!ageStr.isBlank()) s.setAge(Integer.parseInt(ageStr));

        dao.updateStudent(s);
        System.out.println("Updated.");
    }

    private static void deleteStudent() throws Exception {
        System.out.print("ID to delete: "); int id = Integer.parseInt(sc.nextLine().trim());
        dao.deleteStudent(id);
        System.out.println("Deleted.");
    }

    private static void viewStudent() throws Exception {
        System.out.print("ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        Student s = dao.getStudentById(id);
        System.out.println(s == null ? "Not found." : s);
    }

    private static void viewAll() throws Exception {
        List<Student> list = dao.getAllStudents();
        if (list.isEmpty()) { System.out.println("No students."); return; }
        list.forEach(System.out::println);
    }
}
