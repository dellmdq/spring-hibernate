package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Student;

public class DeleteInstructorDetailDemo {

	public static void main(String[] args) {
		
		//create session factory
		SessionFactory factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Instructor.class)
													.addAnnotatedClass(InstructorDetail.class)
													.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
		
			//start transaction
			session.beginTransaction();
			
			//get the instructor detail object
			int theId = 3;
			InstructorDetail tempInstructorDetail = session.get(InstructorDetail.class, theId);
					
			//print instructor detail
			System.out.println("tempInstructor Detail: " + tempInstructorDetail);
			
			//print associated instructor
			System.out.println("The associated instructor: " + tempInstructorDetail.getInstructor());
			
			//delete instructor detail
			System.out.println("Deleting tempInstructorDetail: " + tempInstructorDetail);
			session.delete(tempInstructorDetail);
			
			//remove the associated object reference
			//break bi-directional link. if it is not done hibernate launches an exception
			tempInstructorDetail.getInstructor().setInstructorDetail(null);

			//delete instructor detail
			System.out.println("Deleting tempInstructorDetail: " + tempInstructorDetail);
			session.delete(tempInstructorDetail);
			
			//commit transaction
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			//handle connection leak issue if a exception occurs
			session.close();
			factory.close();
		}
		
		
		
	}

}
