package com.example.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {
    public static SessionFactory provideSessionFactory()
    {
        Configuration config = new Configuration();
        config.configure("resources/hibernate.cfg.xml");
        return config.buildSessionFactory();
    }
}