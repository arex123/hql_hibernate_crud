package org.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
//        Configuration confg = new Configuration().configure("hibernate.cfg.xml");
//        SessionFactory sf = confg.buildSessionFactory();
//        Session session = sf.openSession();

        Session session = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory().openSession();

        System.out.println("for creating press 1 ");
        System.out.println("for showing record by its roll press 2 ");
        System.out.println("for updating press 3 ");
        System.out.println("for deleting press 4 ");
        System.out.println("for all records press 5 ");

        int choice = scn.nextInt();

        Transaction txn = session.beginTransaction();
        switch (choice){
            case 1:
                //create a record
                System.out.println("enter roll number");
                int roll = scn.nextInt();
                System.out.println("enter name");
                String name = scn.next();
                System.out.println("enter college");
                String college = scn.next();
                System.out.println("enter cgpa");
                float cgpa = scn.nextFloat();


                student st = new student();

                st.setRoll(roll);

                st.setName(name);

                st.setCgpa((float) cgpa);

                st.setCollege(college);

                session.save(st);

                txn.commit();
                System.out.println("record saved");
                break;

            case 2:
                //show all records

//                List<student> list = session.createQuery("from student",).list();
//                list.forEach(s->{
//                    System.out.println(s.getName());
//                });

                System.out.println("enter roll number");
                int rollno = scn.nextInt();
                student stu = session.load(student.class,rollno);
                System.out.println("Student name is "+stu.getName()+"\n"+
                        "student cgpa is "+stu.getCgpa()+"\n"+
                        "student is from "+stu.getCollege());


                txn.commit();
                break;

            case 3:
                //updating the record
                System.out.println("enter roll to update record");
                int r = scn.nextInt();
                student st1 = session.get(student.class,r);

                if(st1!=null){
                    System.out.println("1. name\n2. cgpa\n3. college");
                    int ch = scn.nextInt();
                    switch (ch){
                        case 1:
                            System.out.println("enter new name");
                            st1.setName(scn.next());
                            break;
                        case 2:
                            System.out.println("enter new cgpa");
                            st1.setCgpa(scn.nextFloat());
                            break;
                        case 3:
                            System.out.println("enter new college");
                            st1.setCollege(scn.next());
                            break;
                        default:
                            System.out.println("wrong choice");
                    }
                    session.update(st1);
                    session.flush();
                    txn.commit();
                    System.out.println("successfully updated");

                }else{
                    System.out.println("student not found with your given roll no");
                }
                session.close();
                break;
            case 4:
                System.out.println("enter roll number to delete it ");
                student stud = session.get(student.class,scn.nextInt());
                if(stud!=null){
                    session.delete(stud);
                    session.flush();
                    System.out.println("student deleted successfully");
                }else{
                    System.out.println("student is not present in table");
                }
                txn.commit();
                session.close();
                break;

//            case 5:
//                Query query = session.createQuery("select from");
//
//                List<student> list = query.getResultList();
//                System.out.println("Number of students present--> "+list.size());
//                for (student s : list) {
//                    System.out.println(s.getName());
//                    System.out.println(s.getCgpa());
//                    System.out.println(s.getCollege());
//                }


//                break;
            default:
                System.out.println("not valid choice");



        }



    }
}
