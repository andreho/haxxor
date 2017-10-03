package examples;

/**
 * ADD VM START PARAMETERS:
 * -ea -javaagent:C:/Workspace/java/haxxor/examples/lib/aspectjweaver-1.8.11.jar
 * MAY BE THIS ONE TOO: -Daj.weaving.loadersToSkip=sun.misc.Launcher$AppClassLoader
 * https://stackoverflow.com/questions/18194719/aspectj-classloading-issue-when-trying-to-use-external-aop-xml-file

 * <br/>Created by a.hofmann on 01.10.2017 at 13:17.
 */
public class AspectJMain {
  public static void main(String[] args) {
    System.out.println(getMessage());
  }
  public static String getMessage() {
    return "Hello World!";
  }
}
