 package main.tasks.n10.serialization;
 
 import static junit.framework.Assert.assertEquals;

 import static junit.framework.Assert.assertFalse;
 import static junit.framework.Assert.assertNull;
 import static junit.framework.Assert.assertTrue;
 import static junit.framework.Assert.fail;
 import interfaces.task8.CyclicCollection;
 import interfaces.task8.CyclicItem;
import main.tasks.n8.CyclicCollectionImpl;

import org.junit.Before;
 import org.junit.Test;
 
 import com.mockobjects.dynamic.C;
 import com.mockobjects.dynamic.Mock;
 
 /**
  * @author yura
  */
 public class CyclicCollectionTest {
     private static Class<?> testingClass = CyclicCollectionImpl.class;
 
     private Mock mockCyclicItemFirst;
     private Mock mockCyclicItemSecond;
     private Mock mockCyclicItemThird;
     private CyclicItem first;
     private CyclicItem second;
     private CyclicItem third;
 
     private CyclicCollection collection;
 
     @Before
     public void before() throws Exception {
         collection = (CyclicCollection) testingClass.newInstance();
         mockCyclicItemFirst = new Mock(CyclicItem.class);
         mockCyclicItemSecond = new Mock(CyclicItem.class);
         mockCyclicItemThird = new Mock(CyclicItem.class);
 
         first = (CyclicItem) mockCyclicItemFirst.proxy();
         second = (CyclicItem) mockCyclicItemSecond.proxy();
         third = (CyclicItem) mockCyclicItemThird.proxy();
 
         resetMocks();
     }
 
     private void resetMocks() {
         mockCyclicItemFirst.reset();
         mockCyclicItemSecond.reset();
         mockCyclicItemThird.reset();
         mockCyclicItemFirst.matchAndReturn("toString", "first elem");
         mockCyclicItemSecond.matchAndReturn("toString", "second elem");
         mockCyclicItemThird.matchAndReturn("toString", "third elem");
     }
 
     private void addTwoElements() {
         mockCyclicItemFirst.expect("setNextItem", first);
         assertTrue("first element not added", collection.add(first));
 
         try {
             mockCyclicItemFirst.verify();
         } catch (Exception e) {
             fail("setNextItem for first item not was called");
         }
 
         resetMocks();
         mockCyclicItemFirst.matchAndReturn("nextItem", first);
         mockCyclicItemSecond.expect("setNextItem", first);
         mockCyclicItemFirst.expect("setNextItem", second);
         assertTrue("second element not added", collection.add(second));
 
         try {
             mockCyclicItemFirst.verify();
         } catch (Exception e) {
             fail("setNextItem for first item not was called");
         }
         try {
             mockCyclicItemSecond.verify();
         } catch (Exception e) {
             fail("setNextItem for second item not was called");
         }
 
         resetMocks();
         mockCyclicItemFirst.matchAndReturn("nextItem", second);
         mockCyclicItemSecond.matchAndReturn("nextItem", first);
 
         assertEquals("Size should be 2 after 2 add", 2, collection.size());
     }
 
     @Test
     public void testClear() throws Exception {
         assertEquals("Collection after create should have 0 size", 0,
                 collection.size());
 
         assertNull("getFirst after create should return null", collection
                 .getFirst());
     }
 
     @Test
     public void testAddNull() throws Exception {
         try {
             collection.add(null);
             fail("item should be tested for null");
         } catch (NullPointerException ex) {
         } catch (Exception ex) {
             fail("item should be tested for null");
         }
     }
 
     @Test
     public void testAdd() throws Exception {
         mockCyclicItemFirst.expect("setNextItem", first);
         mockCyclicItemFirst.matchAndReturn("nextItem", null);
         collection.add(first);
         assertEquals("Collection size not equal to 1 after 1 add", 1,
                 collection.size());
 
         try {
             mockCyclicItemFirst.verify();
         } catch (Exception e) {
             fail("setNextItem for item not was called");
         }
 
         try {
             mockCyclicItemFirst.reset();
             mockCyclicItemFirst.matchAndReturn("nextItem", first);
             collection.add(first);
             fail("Can not insert same element");
         } catch (IllegalArgumentException ex) {
             assertEquals("Size of collection changed after wrong insert", 1,
                     collection.size());
         }
 
         assertEquals("Must not be able to add duplicated objects", 1,
                 collection.size());
     }
 
     @Test
     public void testAddSame() throws Exception {
         addTwoElements();
 
         try {
             collection.add(first);
             fail("item should be tested for presence in collection");
         } catch (IllegalArgumentException ex) {
         } catch (Exception ex) {
             fail("item should be tested for presence in collection");
         }
         assertEquals("Must not be able to add same objects", 2, collection
                 .size());
     }
 
     @Test
     public void testInsertAfterNull() throws Exception {
         addTwoElements();
 
         try {
             collection.insertAfter(null, third);
             fail("item should be tested for null");
         } catch (NullPointerException ex) {
         } catch (Exception ex) {
             fail("item should be tested for null");
         }
 
         try {
             collection.insertAfter(first, null);
             fail("newItem should be tested for null");
         } catch (NullPointerException ex) {
         } catch (Exception ex) {
             fail("newItem should be tested for null");
         }
     }
 
     @Test
     public void testInsertAfterWrong() throws Exception {
         addTwoElements();
 
         try {
             collection.insertAfter(first, second);
             fail("newItem should be tested for same");
         } catch (IllegalArgumentException ex) {
         } catch (Exception ex) {
             fail("newItem should be tested for same");
         }
         assertEquals("Must not be able to insert same objects", 2, collection
                 .size());
 
         Mock mockCyclicItemFourth = new Mock(CyclicItem.class);
         mockCyclicItemFourth.matchAndReturn("toString", "fourth elem");
         CyclicItem fourth = (CyclicItem) mockCyclicItemFourth.proxy();
 
         try {
             collection.insertAfter(third, fourth);
             fail("item should be tested for presence in collection");
         } catch (IllegalArgumentException ex) {
         } catch (Exception ex) {
             fail("item should be tested for presence in collection");
         }
     }
 
     @Test
     public void testInsertAfter() throws Exception {
         addTwoElements();
 
         mockCyclicItemThird.expect("setNextItem", second);
         mockCyclicItemFirst.expect("setNextItem", third);
         collection.insertAfter(first, third);
 
         try {
             mockCyclicItemFirst.verify();
         } catch (Exception e) {
             fail("setNextItem for item not was called");
         }
         try {
             mockCyclicItemThird.verify();
         } catch (Exception e) {
             fail("setNextItem for newItem not was called");
         }
     }
 
     @Test
     public void testRemoveNull() throws Exception {
         try {
             collection.remove(null);
             fail("item should be tested for null");
         } catch (NullPointerException ex) {
         } catch (Exception ex) {
             fail("item should be tested for null");
         }
     }
 
     @Test
     public void testRemoveWrong() throws Exception {
         try {
             assertFalse("should return false if collection not changed",
                     collection.remove(first));
         } catch (Exception e) {
             fail("should return false if collection not changed");
         }
 
         addTwoElements();
         try {
             assertFalse("should return false if collection not changed",
                     collection.remove(third));
         } catch (Exception e) {
             fail("should return false if collection not changed");
         }
     }
 
     @Test
     public void testRemove() throws Exception {
         addTwoElements();
 
         mockCyclicItemSecond.expect("setNextItem", second);
         mockCyclicItemFirst.matchAndReturn("setNextItem", C.IS_ANYTHING, null);
         assertTrue("Result of deletion not 'true'", collection.remove(first));
 
         mockCyclicItemSecond.reset();
         mockCyclicItemSecond.matchAndReturn("nextItem", second);
         mockCyclicItemSecond.matchAndReturn("setNextItem", C.IS_ANYTHING, null);
         assertEquals("Value of getFirst wrong after remove", second, collection
                 .getFirst());
 
         assertTrue("Deleting last element of collection", collection
                 .remove(second));
         assertEquals("Size doesn't equal to 0 after remove", 0, collection
                 .size());
         assertNull("Value of getFirst wrong after remove must be null",
                 collection.getFirst());
     }
 }
