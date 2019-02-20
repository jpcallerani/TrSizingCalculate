package DAO;

import java.io.PrintStream;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class NewHibernateUtil
{
  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory()
  {
    return sessionFactory;
  }

  static
  {
    try
    {
      sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }
    catch (Throwable ex) {
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}