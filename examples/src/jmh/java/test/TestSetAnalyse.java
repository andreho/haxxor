package test;

import net.andreho.haxxor.HaxxorBuilder;
import net.andreho.haxxor.Hx;
import net.andreho.haxxor.api.HxType;
import net.andreho.resources.Resource;

import java.io.IOException;
import java.util.Map;

/**
 * <br/>Created by a.hofmann on 24.11.2017 at 23:39.
 */
public class TestSetAnalyse {
  public static void main(String[] args)
  throws IOException {
    int classes = 0;
    int abstracts = 0;
    int interfaces = 0;
    int annotations = 0;
    int enums = 0;

    final Map<String, Resource> resources = AbstractBenchmark.getResources();
    final Hx hx = HaxxorBuilder.newBuilder().build();

    for(Resource resource : resources.values()) {
      String className = resource.getName().replace('/', '.');
      className = className.substring(0, className.length() - ".class".length());
      HxType type = hx.resolve(className, resource.toByteArray(), 0);

      if(type.isInterface()) {
        if(type.isAnnotation()) {
          annotations++;
        }
        interfaces++;
        continue;
      } else if(type.isAbstract()) {
        abstracts++;
      } else if(type.isEnum()) {
        enums++;
      }
      classes++;
    }

    System.out.println("Klassen: "+classes+
                       "\nAbstract: "+abstracts+
                       "\nEnums: "+enums+
                       "\nInterfaces: "+interfaces+
                       "\nAnnotations: "+annotations+
                       "\nTotal: "+(interfaces+classes));
  }
}
