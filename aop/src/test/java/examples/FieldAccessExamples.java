package examples;

import net.andreho.aop.api.Access;
import net.andreho.aop.api.Aspect;
import net.andreho.aop.api.Profile;
import net.andreho.aop.api.injectable.Current;
import net.andreho.aop.api.injectable.Intercepted;
import net.andreho.aop.api.injectable.This;
import net.andreho.aop.api.spec.ClassWith;
import net.andreho.aop.api.spec.Classes;
import net.andreho.aop.api.spec.Fields;
import net.andreho.aop.spi.Helpers;

import javax.annotation.Generated;
import java.lang.reflect.Field;

/**
 * <br/>Created by a.hofmann on 18.09.2017 at 03:27.
 */
public class FieldAccessExamples {
  private static int staticCounter;
  private int counter;
  private String name;

  public void printCounterField() {
    FieldAccessExamples aTarget = this;
    //System.out.println(aTarget.counter); =>
    System.out.println(GET__counter__(aTarget));
  }

  public void printStaticCounterField() {
    System.out.println(GET__staticCounter__());
  }

  @Profile(name = Observer.PROFILE,
     fields = @Fields(
       declaredBy = @Classes(@ClassWith(FieldAccessExamples.class))
     )
  )
  @Aspect(Observer.PROFILE)
  private static class Observer {
    static final String PROFILE = "allFieldsOfFieldAccessExamples";
    @Aspect.Factory
    public static Observer create() {
      return new Observer();
    }
    @Access.Get(PROFILE)
    public int observeFieldReadAccess(@Current int value,
                                      @This Object owner,
                                      @Intercepted Field field) {
      return value % 2;
    }
  }

  @Generated("")
  private /* final synthetic */ int GET__counter__(FieldAccessExamples target) {
    //We have here all things we may need.
    return Observer.create().observeFieldReadAccess(
      target.counter,
      target,
      Helpers.getFieldOf(FieldAccessExamples.class, "counter", "I"));
  }

  @Generated("")
  private static /* synthetic */ int GET__staticCounter__() {
    //We have here all things we may need.
    return Observer.create().observeFieldReadAccess(
      staticCounter,
      null,
      Helpers.getFieldOf(FieldAccessExamples.class, "staticCounter", "I"));
  }
}
