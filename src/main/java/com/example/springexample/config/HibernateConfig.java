package com.example.springexample.config;

import com.example.springexample.model.Locations;
import com.example.springexample.model.Sessions;
import com.example.springexample.model.Users;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateConfig {
//    private static HibernateConfig instance=new HibernateConfig();
//    private final SessionFactory sessionFactory;
//
//    private HibernateConfig() {
//        this.sessionFactory=configurationHibernate();
//    }
//    public static HibernateConfig getInstance() {
//        return instance;
//    }
//    public SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//
//    private SessionFactory configurationHibernate() {
//        try {
//            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//            Metadata metadata = new MetadataSources(serviceRegistry).addAnnotatedClass(Locations.class).addAnnotatedClass(Sessions.class).addAnnotatedClass(Users.class).getMetadataBuilder().build();
//            return  metadata.getSessionFactoryBuilder().build();
//        }catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
