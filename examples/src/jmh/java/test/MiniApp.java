package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.logging.Level;

/**
 * -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * -Dimpl=haxxor
 * <br/>Created by a.hofmann on 11.12.2017 at 17:33.
 */
public class MiniApp {
  public static void main(String[] args) {
    EntityManagerFactory managerFactory = null;
    try {
      managerFactory = Persistence.createEntityManagerFactory("pu");
      final EntityManager entityManager = managerFactory.createEntityManager();
      EntityTransaction tx = entityManager.getTransaction();

      checkClassModification(tx);
      checkClassModification(managerFactory);
      checkClassModification(entityManager);

      tx.begin();
      entityManager.persist(new Persistable("Ok"));
      tx.commit();
      entityManager.flush();
      entityManager.close();
    } catch (Exception e) {

    } finally {
      if(managerFactory != null && managerFactory.isOpen()) {
        managerFactory.close();
      }
    }
  }

  private static void checkClassModification(final Object element) {
    if(element instanceof LoggableInterface) {
      LoggableInterface loggableInterface = (LoggableInterface) element;
      loggableInterface.log().log(Level.WARNING, "Modification was successfully done on: "+element.getClass().getName());
    } else {
      throw new IllegalStateException("Class isn't LoggableInterface: "+element.getClass());
    }
  }
}
