package main.tasks.n10.ioutils;

 import static junit.framework.Assert.assertEquals;
 import static junit.framework.Assert.fail;
 import interfaces.task6.IOUtils;
import main.tasks.n6.IOUtilsImpl;

import java.io.CharArrayReader;
 import java.io.CharArrayWriter;
 
 import org.junit.Before;
 import org.junit.Test;
 
 public class IOUtilsTest {
     private static Class<?> testingClass = IOUtilsImpl.class;
 
     private IOUtils utils;
 
     @Before
     public void before() throws Exception {
         utils = (IOUtils) testingClass.newInstance();
     }
 
     @Test
     public void testReplaceCharsNull() throws Exception {
         char[] buffer = new char[0];
         CharArrayReader reader = null;
         CharArrayWriter writer = null;
         try {
             reader = new CharArrayReader(buffer);
             try {
                 utils.replaceChars(reader, null, "", "");
                 fail("replaceChars should throw NullPointerException if out == null");
             } catch (NullPointerException e) {
             } catch (Exception e) {
                 fail("replaceChars should throw NullPointerException if out == null");
             }
 
             writer = new CharArrayWriter(0);
             try {
                 utils.replaceChars(null, writer, "", "");
                 fail("replaceChars should throw NullPointerException if in == null");
             } catch (NullPointerException e) {
             } catch (Exception e) {
                 fail("replaceChars should throw NullPointerException if in == null");
             }
         } finally {
             if (reader != null)
                 reader.close();
 
             if (writer != null)
                 writer.close();
         }
     }
 
     @Test
     public void testReplaceCharsDifferentLength() throws Exception {
         char[] buffer = "1234567890".toCharArray();
         CharArrayReader reader = null;
         CharArrayWriter writer = null;
         try {
             reader = new CharArrayReader(buffer);
             writer = new CharArrayWriter(0);
             utils.replaceChars(reader, writer, "12", "3");
             fail("inChars and outChars have different lenght "
                     + "IllegalArgumentException should be throwed");
         } catch (IllegalArgumentException e) {
         } finally {
             if (reader != null)
                 reader.close();
 
             if (writer != null)
                 writer.close();
         }
     }
 
     @Test
     public void testReplaceCharsCharsNull() throws Exception {
         String str = "1234567890";
         CharArrayReader reader = null;
         CharArrayWriter writer = null;
         try {
             reader = new CharArrayReader(str.toCharArray());
             writer = new CharArrayWriter(0);
             utils.replaceChars(reader, writer, null, null);
             assertEquals(
                     "input and output array streams should be equals for chars"
                             + " null and null", str, new String(writer
                             .toCharArray()));
         } finally {
             if (reader != null)
                 reader.close();
 
             if (writer != null)
                 writer.close();
         }
     }
 
     @Test
     public void testReplaceCharsCharsClear() throws Exception {
         String str = "1234567890";
         CharArrayReader reader = null;
         CharArrayWriter writer = null;
         try {
             reader = new CharArrayReader(str.toCharArray());
             writer = new CharArrayWriter(0);
             utils.replaceChars(reader, writer, "", "");
             assertEquals(
                     "input and output array streams should be equals for chars"
                             + " '' and ''", str, new String(writer
                             .toCharArray()));
         } finally {
             if (reader != null)
                 reader.close();
 
             if (writer != null)
                 writer.close();
         }
     }
 
     @Test
     public void testCopyStream() throws Exception {
         String str = "1234567890";
         CharArrayReader reader = null;
         CharArrayWriter writer = null;
         try {
             reader = new CharArrayReader(str.toCharArray());
             writer = new CharArrayWriter(0);
             utils.replaceChars(reader, writer, "135", "246");
             String outText = str.replace('1', '2').replace('3', '4')
                     .replace('5', '6');
             assertEquals("output stream wrong " + "in = " + str
                     + " chars '135' '246' " + "out="
                     + new String(writer.toCharArray()), outText,
                     new String(writer.toCharArray()));
         } finally {
             if (reader != null)
                 reader.close();
 
             if (writer != null)
                 writer.close();
         }
     }
 
 }
