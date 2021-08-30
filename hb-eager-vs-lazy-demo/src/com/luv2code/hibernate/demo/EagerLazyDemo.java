package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Student;

public class EagerLazyDemo {

	public static void main(String[] args) {
		
		//Accesing data with lazy fetch after session closed. 2 Options 
		//1) getting data in memory through a session.get
		//2) the same but through a HQL query
		
		//create session factory
		SessionFactory factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Instructor.class)
													.addAnnotatedClass(InstructorDetail.class)
													.addAnnotatedClass(Course.class)
													.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			//start transaction
			session.beginTransaction();
			
			//option 2: hibernate query with HQL
			
			
			//get the instructor from db
			int theId = 1;
			Query<Instructor> query = session.createQuery("Select i from Instructor i " 
																							+ "JOIN FETCH i.courses " 
																							+ "where i.id=:theInstructorId", 
																							Instructor.class);
			//set parameter on query
			query.setParameter("theInstructorId", theId);		
			
			//execute query and get the instructor
			Instructor tempInstructor = query.getSingleResult();
			
			System.out.println("luv2code Instructor: " + tempInstructor);



			//commit 
			session.getTransaction().commit();
	
			
			//close session
			session.close();
			System.out.println("\nluv2code Closing session...\n");

			//get course for the instructor
			System.out.println("luv2code Courses: " + tempInstructor.getCourses());
	
			
			System.out.println("luv2code Done!");
			
		}
		finally {
			//add clean up code
			session.close();
			factory.close();
		}
		
		
		
	}

}
