package examples;
import java.util.Objects;

@Marker
public class AnnotationShowCase<@Marker T>
  extends @Marker Object
  implements @Marker Comparable<@Marker AnnotationShowCase<T>> {

  @Marker private final String name;

  @Marker public AnnotationShowCase(@Marker final String name)
  throws @Marker NullPointerException {
    this.name = Objects.requireNonNull(name);
  }
  @Marker public String getName() {
    return name;
  }
  @Override
  public int compareTo(@Marker final AnnotationShowCase<T> o) {
    return getName().compareTo(o.getName());
  }
  @Override public boolean equals(final Object o) {
    if (!(o instanceof @Marker AnnotationShowCase)) {
      return false;
    }
    @Marker final AnnotationShowCase<?> that = (@Marker AnnotationShowCase<?>) o;
    return name != null ? name.equals(that.name) : that.name == null;
  }
  @Override public int hashCode() {
    return Objects.hashCode(name);
  }
}
