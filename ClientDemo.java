package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;

import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        cfg.addAnnotatedClass(Project.class);

        SessionFactory sessionFactory = cfg.buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Project p1 = new Project("Project Alpha", 6, 100000, "Alice");
        Project p2 = new Project("Project Beta", 8, 150000, "Bob");
        Project p3 = new Project("Project Gamma", 12, 200000, "Charlie");

        session.save(p1);
        session.save(p2);
        session.save(p3);

        
        Criteria criteria = session.createCriteria(Project.class);

        criteria.setProjection(Projections.rowCount());
        System.out.println("Total Projects: " + criteria.uniqueResult());

        criteria.setProjection(Projections.max("budget"));
        System.out.println("Max Budget: " + criteria.uniqueResult());

        criteria.setProjection(Projections.min("budget"));
        System.out.println("Min Budget: " + criteria.uniqueResult());

        criteria.setProjection(Projections.sum("budget"));
        System.out.println("Sum of Budgets: " + criteria.uniqueResult());

        criteria.setProjection(Projections.avg("budget"));
        System.out.println("Average Budget: " + criteria.uniqueResult());

        transaction.commit();
        session.close();
        sessionFactory.close();
    }
}
