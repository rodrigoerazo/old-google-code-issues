package org.apache.ibatis.reflection;

import domain.jpetstore.Product;
import domain.misc.RichType;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class MetaObjectTest {

  @Test
  public void shouldGetAndSetField() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richField", "foo");
    assertEquals("foo", meta.getValue("richField"));
  }

  @Test
  public void shouldGetAndSetNestedField() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richType.richField", "foo");
    assertEquals("foo", meta.getValue("richType.richField"));
  }

  @Test
  public void shouldGetAndSetProperty() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richProperty", "foo");
    assertEquals("foo", meta.getValue("richProperty"));
  }

  @Test
  public void shouldGetAndSetNestedProperty() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richType.richProperty", "foo");
    assertEquals("foo", meta.getValue("richType.richProperty"));
  }

  @Test
  public void shouldGetAndSetMapPair() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richMap.key", "foo");
    assertEquals("foo", meta.getValue("richMap.key"));
  }

  @Test
  public void shouldGetAndSetMapPairUsingArraySyntax() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richMap[key]", "foo");
    assertEquals("foo", meta.getValue("richMap[key]"));
  }

  @Test
  public void shouldGetAndSetNestedMapPair() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richType.richMap.key", "foo");
    assertEquals("foo", meta.getValue("richType.richMap.key"));
  }

  @Test
  public void shouldGetAndSetNestedMapPairUsingArraySyntax() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richType.richMap[key]", "foo");
    assertEquals("foo", meta.getValue("richType.richMap[key]"));
  }

  @Test
  public void shouldGetAndSetListItem() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richList[0]", "foo");
    assertEquals("foo", meta.getValue("richList[0]"));
  }

  @Test
  public void shouldSetAndGetSelfListItem() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richList[0]", "foo");
    assertEquals("foo", meta.getValue("richList[0]"));
  }

  @Test
  public void shouldGetAndSetNestedListItem() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    meta.setValue("richType.richList[0]", "foo");
    assertEquals("foo", meta.getValue("richType.richList[0]"));
  }

  @Test
  public void shouldGetReadablePropertyNames() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    String[] readables = meta.getGetterNames();
    assertEquals(5, readables.length);
    for (String readable : readables) {
      assertTrue(meta.hasGetter(readable));
      assertTrue(meta.hasGetter("richType." + readable));
    }
    assertTrue(meta.hasGetter("richType"));
  }

  @Test
  public void shouldGetWriteablePropertyNames() {
    RichType rich = new RichType();
    MetaObject meta = MetaObject.forObject(rich);
    String[] writeables = meta.getSetterNames();
    assertEquals(5, writeables.length);
    for (String writeable : writeables) {
      assertTrue(meta.hasSetter(writeable));
      assertTrue(meta.hasSetter("richType." + writeable));
    }
    assertTrue(meta.hasSetter("richType"));
  }

  @Test
  public void shouldSetPropertyOfNullNestedProperty() {
    MetaObject richWithNull = MetaObject.forObject(new RichType());
    richWithNull.setValue("richType.richProperty", "foo");
    assertEquals("foo", richWithNull.getValue("richType.richProperty"));
  }

  @Test
  public void shouldSetPropertyOfNullNestedPropertyWithNull() {
    MetaObject richWithNull = MetaObject.forObject(new RichType());
    richWithNull.setValue("richType.richProperty", null);
    assertEquals(null, richWithNull.getValue("richType.richProperty"));
  }

  @Test
  public void shouldGetPropertyOfNullNestedProperty() {
    MetaObject richWithNull = MetaObject.forObject(new RichType());
    assertNull(richWithNull.getValue("richType.richProperty"));
  }

  @Test
  public void shouldVerifyHasReadablePropertiesReturnedByGetReadablePropertyNames() {
    MetaObject object = MetaObject.forObject(new Product());
    for (String readable : object.getGetterNames()) {
      assertTrue(object.hasGetter(readable));
    }
  }

  @Test
  public void shouldVerifyHasWriteablePropertiesReturnedByGetWriteablePropertyNames() {
    MetaObject object = MetaObject.forObject(new Product());
    for (String writeable : object.getSetterNames()) {
      assertTrue(object.hasSetter(writeable));
    }
  }

  @Test
  public void shouldSetAndGetProperties() {
    MetaObject object = MetaObject.forObject(new Product());
    for (String writeable : object.getSetterNames()) {
      if (!writeable.contains("$")) {
        object.setValue(writeable, "test");
        assertEquals("test", object.getValue(writeable));
      }
    }
  }

  @Test
  public void shouldVerifyPropertyTypes() {
    MetaObject object = MetaObject.forObject(new Product());
    for (String writeable : object.getSetterNames()) {
      if (!writeable.contains("$")) {
        assertEquals(String.class, object.getGetterType(writeable));
        assertEquals(String.class, object.getSetterType(writeable));
      }
    }
  }

  @Test
  public void shouldDemonstrateDeeplyNestedMapProperties() {
    HashMap map = new HashMap();
    MetaObject metaMap = MetaObject.forObject(map);

    assertTrue(metaMap.hasSetter("id"));
    assertTrue(metaMap.hasSetter("name.first"));
    assertTrue(metaMap.hasSetter("address.street"));

    assertFalse(metaMap.hasGetter("id"));
    assertFalse(metaMap.hasGetter("name.first"));
    assertFalse(metaMap.hasGetter("address.street"));

    metaMap.setValue("id", "100");
    metaMap.setValue("name.first", "Clinton");
    metaMap.setValue("name.last", "Begin");
    metaMap.setValue("address.street", "1 Some Street");
    metaMap.setValue("address.city", "This City");
    metaMap.setValue("address.province", "A Province");
    metaMap.setValue("address.postal_code", "1A3 4B6");

    assertTrue(metaMap.hasGetter("id"));
    assertTrue(metaMap.hasGetter("name.first"));
    assertTrue(metaMap.hasGetter("address.street"));

    assertEquals(3, metaMap.getGetterNames().length);
    assertEquals(3, metaMap.getSetterNames().length);

    Map name = (Map) metaMap.getValue("name");
    Map address = (Map) metaMap.getValue("address");

    assertEquals("Clinton", name.get("first"));
    assertEquals("1 Some Street", address.get("street"));
  }


}
