package com.hibernatepractice.demo;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernatepractice.entity.Employee;


public class EmployeeDemo {

	private static SessionFactory factory;
	private static Session session;
	
	public static void main(String[] args) {

		
		//create session factory
		factory = new Configuration()
													.configure("hibernate.cfg.xml")
													.addAnnotatedClass(Employee.class)
													.buildSessionFactory();
		//create session
		session = factory.getCurrentSession();
		
		try {
			//create statements
			//createEmployee("Ramon","Diaz", "River Plate");
			//createEmployee("Marce","Pala", "Open Sports");
			//createEmployee("Fernando","Miembro", "Fox Sports");
			
			//retrieve object by primary key
			int employeeId = 3;
			//Employee employedGotById = getEmployeeById(employeeId);
			//System.out.println("Employee retrieved: " + employedGotById);
			
			//get employee list
			//List<Employee> employeeList = getEmployeeList();
			
			//display list
			//displayEmployeeList(employeeList);
			
			//display list from specific company
			//displayEmployeeList(getEmployeeListByCompany("River Plate"));
			
			//deleting employee and show it
			System.out.println(deleteEmployeeById(employeeId));
			
			
			
			
		}
		finally {
			factory.close();
		}
		

		
	}//main end
	
	//METHODS *****************************************
	
	public static void createEmployee(String firstName, String lastName, String company) {
		//create session
		session = factory.getCurrentSession();
		
		//begin transaction
		session.beginTransaction();
		
		//create employee
		System.out.println("\nCreating new employee...");
		Employee tempEmployee = new Employee(firstName, lastName, company);
		
		//save the employees
		session.save(tempEmployee);
		
		//commit transaction
		session.getTransaction().commit();
		System.out.println("\nEmployee saved to DB");
	}
	
	public static Employee getEmployeeById(int id) {
		session = factory.getCurrentSession();
		
		session.beginTransaction();
		
		//get employee by id
		Employee tempEmployee = session.get(Employee.class, id);
		
		System.out.println("Get Complete:  "+ tempEmployee);
		
		session.getTransaction().commit();
		
		System.out.println("Finished getting Employee...");
		return tempEmployee;
				
	}
	
	//get employee list
	public static List<Employee> getEmployeeList(){
		
		session = factory.getCurrentSession();
		session.beginTransaction();
		
		List<Employee> employeeList = session.createQuery("from Employee").getResultList();
		
		session.getTransaction().commit();
		
		return employeeList;
	}
	//display employee list
	public static void displayEmployeeList(List<Employee> employeeList) {
		
		for(Employee tempEmployee : employeeList) {
			System.out.println(tempEmployee);
		}
	}
	
	//getEmployeesByCompany
	public static List<Employee> getEmployeeListByCompany(String company) {
		session = factory.getCurrentSession();
		session.beginTransaction();
		
		List<Employee> employeeListByCompany = session.createQuery("from Employee e where e.company ='River Plate'").getResultList();
		
		session.getTransaction().commit();
		return employeeListByCompany;
		
	}
	
	//delete employee by id
	public static Employee deleteEmployeeById(int id) {

		Employee deleted = getEmployeeById(id);
		
		session = factory.getCurrentSession();
		session.beginTransaction();
		
		System.out.println("Deleting employee id: " + deleted + "\n");
		session.createQuery("delete from Employee e where e.id ='" + id +"'" ).executeUpdate();
		
		session.getTransaction().commit();
		return deleted;
	}


}//EmployeeDemo Class end

