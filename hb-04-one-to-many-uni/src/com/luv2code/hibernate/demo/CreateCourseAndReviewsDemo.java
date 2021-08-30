package com.luv2code.hibernate.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Course;
import com.luv2code.hibernate.demo.entity.Instructor;
import com.luv2code.hibernate.demo.entity.InstructorDetail;
import com.luv2code.hibernate.demo.entity.Review;

public class CreateCourseAndReviewsDemo {

	public static void main(String[] args) {
		
		//create session factory
		SessionFactory factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Instructor.class)
													.addAnnotatedClass(InstructorDetail.class)
													.addAnnotatedClass(Course.class)
													.addAnnotatedClass(Review.class)
													.buildSessionFactory();
		//create session
		Session session = factory.getCurrentSession();
		
		try {
			//start transaction
			session.beginTransaction();
			
			//create course
			Course tempCourse = new Course("Pacman - How to Score One Million Points");
			
			//add some reviews
			tempCourse.addReview(new Review("Great Course... Loved it\n"));
			tempCourse.addReview(new Review("Cool Course... job well done\n"));
			tempCourse.addReview(new Review("What a dumb course. You are an idiot!\n"));
			
			//save the course... and leverage the cascade all 
			System.out.println("Saving the course");
			System.out.println(tempCourse);
			System.out.println(tempCourse.getReviews());
			
			
			session.save(tempCourse);//will save the course and all its reviews (the cascade type does the work for us)
			
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
