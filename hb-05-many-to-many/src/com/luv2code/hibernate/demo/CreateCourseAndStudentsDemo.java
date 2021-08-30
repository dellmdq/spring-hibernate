package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Review;
import com.luv2code.hibernate.demo.entity.Student;

public class CreateCourseAndStudentsDemo {

	public static void main(String[] args) {
		
		//create session factory
		SessionFactory factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Instructor.class)
													.addAnnotatedClass(InstructorDetail.class)
													.addAnnotatedClass(Course.class)
													.addAnnotatedClass(Review.class)
													.addAnnotatedClass(Student.class)
													.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			//start transaction
			session.beginTransaction();
			
			//create course
			Course tempCourse = new Course("Pacman - How to Score One Million Points");
		
			//save the course
			System.out.println("\nSaving the course...");
			session.save(tempCourse);//will save the course and all its reviews (the cascade type does the work for us)
			System.out.println("\nSaved the course: " + tempCourse);
			
			//create the students
			Student tempStudent1 = new Student("John","Doe","johndoe@java.com");
			Student tempStudent2 = new Student("Marce","Pala","marcepala@tycsports.com");
			Student tempStudent3 = new Student("Pepe","Pompin","pepepompin@muppet.com");
		
			//add students to the course
			tempCourse.addStudent(tempStudent1);
			tempCourse.addStudent(tempStudent2);
			tempCourse.addStudent(tempStudent3);
			
			//save the students
			System.out.println("\nSaving students...");
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);
			System.out.println("Saved students: " + tempCourse.getStudents());
			
			//commit 
			session.getTransaction().commit();
			
			System.out.println("Done!");
			
		}
		finally {
			//add clean up code
			session.close();
			factory.close();
		}
		
		
		
	}

}
