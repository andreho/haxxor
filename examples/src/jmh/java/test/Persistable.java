package test;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * <br/>Created by a.hofmann on 11.12.2017 at 17:37.
 */
@Entity
public class Persistable {
  @Id
  @GeneratedValue
  private int id;

  @Column
  private String name;

  public Persistable() {
  }

  public Persistable(final String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }
}
