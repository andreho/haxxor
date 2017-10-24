package org.sandbox.secured;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * -ea -javaagent:C:/Workspace/idea/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * <br/>Created by a.hofmann on 04.07.2017 at 22:39.
 */
@DisplayName("Control access to specifically annotated elements of a class")
public class TestCase {
  static void login(String username,
                    String password,
                    String... permissions) {
    SubjectServiceImpl.securityManager().login(new User(username, password, permissions));
  }

  static void logout() {
    SubjectServiceImpl subjectService = SubjectServiceImpl.securityManager();
    subjectService.logout(subjectService.current().orElseThrow(IllegalStateException::new));
  }

  @Test
  @DisplayName("Access to a secured object with fully granted user")
  void successfulTest() {
    login("ralf", "123"
          ,SubjectImpl.SUBJECT_CREATION
          ,SubjectImpl.OWNERSHIP_ADDITION
          ,SubjectImpl.OWNERSHIP_REMOVAL
    );

    SubjectImpl subject = new SubjectImpl("test", "klaus", "ralf", "udo");
    subject.addOwnership("andre");
    subject.removeOwnership("udo");

    logout();
  }

  @Test
  @DisplayName("Access to a secured object without fully granted user")
  void failingTest() {
    login("andre", "123"
      ,SubjectImpl.SUBJECT_CREATION
//      ,SubjectImpl.OWNERSHIP_ADDITION
      ,SubjectImpl.OWNERSHIP_REMOVAL
    );

    IllegalAccessException exception = assertThrows(IllegalAccessException.class, () -> {
      SubjectImpl subject =
        new SubjectImpl("test", "klaus", "ralf", "udo"
                        ,"andre"
        );

      subject.addOwnership("andre");
      subject.removeOwnership("udo");
    });
  }
}
