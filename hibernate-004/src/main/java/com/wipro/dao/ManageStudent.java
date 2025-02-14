package com.wipro.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import org.hibernate.service.ServiceRegistry;

import com.wipro.entities.Student;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ManageStudent {
	private static SessionFactory factory;

	public static void main(String[] args) {
		try {
			Configuration config = new Configuration();
            config.addAnnotatedClass(Student.class);
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            config.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/demo_database");
            config.setProperty("hibernate.connection.username", "root");
            config.setProperty("hibernate.connection.password", "Shanmukhasai43@");
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("hibernate.show_sql", "true");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();

            factory = config.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}

		ManageStudent ms = new ManageStudent();

		ms.addStudent("Vishwa Charan");
		ms.addStudent("Chandra");
		ms.addStudent("Somesh");
		ms.addStudent("Mahathi");

		ms.listEmployees();
	}

	public Integer addStudent(String name) {
		Session session = factory.openSession();
		Transaction tx = null;
		Integer studentRollNumber = null;

		try {
			tx = session.beginTransaction();
			Student student = new Student(name);
			studentRollNumber = (Integer) session.save(student);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
		catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
		finally {
			session.close();
		}
		return studentRollNumber;
	}

	public void listEmployees() {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List students = session.createQuery("FROM Student").list();
			for (Iterator iterator = students.iterator(); iterator.hasNext();) {
				Student employee = (Student) iterator.next();
				System.out.println("Name: " + employee.getName());
			}
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} 
		catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }finally {
			session.close();
		}
	}
}