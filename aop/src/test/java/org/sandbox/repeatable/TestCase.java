package org.sandbox.repeatable;

import org.junit.jupiter.api.Test;

/**
 * -ea -javaagent:C:/Workspace/java/haxxor/agent/build/libs/agent-1.0.0.jar
 * <br/>Created by a.hofmann on 10.07.2017 at 19:12.
 */
class TestCase {
  @Test
  void fails() {

    FailingObject object = new FailingObject(1);
    object.execute(1);
  }
}
