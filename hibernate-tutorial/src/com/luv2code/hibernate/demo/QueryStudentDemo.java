package com.luv2code.hibernate.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class QueryStudentDemo {

	public static void main(String[] args) {
		
		//create session factory
		SessionFactory factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Student.class)
													.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			
			//start a transaction
			session.beginTransaction();
			
			//query students giving a list of all student objects
			List<Student> theStudents = session.createQuery("from Student").getResultList();
			
			//display the students
			displayStudents(theStudents);
			
			//query students: lastName = 'Doe'
			theStudents = session.createQuery("from Student s where s.lastName='Medina'").getResultList();
			
			//display the students
			System.out.println("\nStudents who have last name of Medina");
			displayStudents(theStudents);

			//query students: lastName='Medina' OR firstName='Marce'
			System.out.println("Students with lastname Medina or firstname Marce");
			theStudents = session.createQuery("from Student s where s.lastName='Medina' OR s.firstName='Marce'").getResultList();
			displayStudents(theStudents);
			
			//query students where email LIKE '%luv2code.com'
			System.out.println("Students with email ending with @javadev.com");
			theStudents = session.createQuery("from Student s  where s.email LIKE '%@javadev.com'").getResultList();
			displayStudents(theStudents);
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		}
		finally {
			factory.close();
		}
		
		
		
	}

	private static void displayStudents(List<Student> theStudents) {
		for(Student tempStudent : theStudents) {
			System.out.println(tempStudent);
		}
	}

}
