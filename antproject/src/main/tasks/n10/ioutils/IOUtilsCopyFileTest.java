package main.tasks.n10.ioutils;
 
 import static junit.framework.Assert.*;
 import static junit.framework.Assert.assertTrue;
 import interfaces.task6.IOUtils;
import main.tasks.n6.IOUtilsImpl;

import java.io.BufferedOutputStream;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.util.Arrays;
 import java.util.Random;
 
 import org.junit.After;
 import org.junit.AfterClass;
 import org.junit.Before;
 import org.junit.BeforeClass;
 import org.junit.Test;
 
 public class IOUtilsCopyFileTest {
     private static String sourceFileName = "source.dat";
     private static String destFileName = "dest.dat";
     private static String sourseFilePath;
     private static String destFilePath;
     private static Class<?> testingClass = IOUtilsImpl.class;
 
     @BeforeClass
     public static void beforeClass() throws Exception {
         sourseFilePath = System.getProperty("java.io.tmpdir") + File.separator
                 + sourceFileName;
         destFilePath = System.getProperty("java.io.tmpdir") + File.separator
                 + destFileName;
 
         OutputStream outputStream = new BufferedOutputStream(
                 new FileOutputStream(sourseFilePath));
         Random random = new Random();
         byte[] buffer = new byte[1000];
         for (int i = 0; i < 5000; i++) {
             random.nextBytes(buffer);
             outputStream.write(buffer);
         }
         outputStream.write(1);
         outputStream.close();
     }
 
     @AfterClass
     public static void afterClass() {
         new File(sourseFilePath).delete();
     }
 
     private IOUtils utils;
 
     @Before
     public void before() throws Exception {
         utils = (IOUtils) testingClass.newInstance();
     }
 
     @After
     public void after() {
         new File(destFilePath).delete();
     }
 
     @Test(timeout = 60000)
     public void testCopyFileNull() throws Exception {
         try {
             utils.copyFile(null, destFilePath);
             fail("source should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("source should be tested for null");
         }
 
         try {
             utils.copyFile(sourseFilePath, null);
             fail("dest should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("dest should be tested for null");
         }
     }
 
     @Test(timeout = 60000)
     public void testCopyFileWrongParams() throws Exception {
         try {
             utils.copyFile(System.getProperty("java.io.tmpdir"), destFilePath);
             fail("source should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("source should be tested for wrong value");
         }
 
         try {
             utils.copyFile(sourseFilePath, System.getProperty("java.io.tmpdir"));
             fail("dest should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("dest should be tested for wrong value");
         }
     }
 
     @Test(timeout = 60000)
     public void testCopyFile() throws Exception {
         utils.copyFile(sourseFilePath, destFilePath);
         compareFiles();
     }
 
     @Test(timeout = 60000)
     public void testCopyFileBufferedNull() throws Exception {
         try {
             utils.copyFileBuffered(null, new File(destFilePath));
             fail("source should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("source should be tested for null");
         }
 
         try {
             utils.copyFileBuffered(new File(sourseFilePath), null);
             fail("dest should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("dest should be tested for null");
         }
     }
 
     @Test(timeout = 60000)
     public void testCopyFileBufferedWrongParams() throws Exception {
         try {
             utils.copyFileBuffered(
                     new File(System.getProperty("java.io.tmpdir")), new File(
                             destFilePath));
             fail("source should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("source should be tested for wrong value");
         }
 
         try {
             utils.copyFileBuffered(new File(sourseFilePath),
                     new File(System.getProperty("java.io.tmpdir")));
             fail("dest should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("dest should be tested for wrong value");
         }
     }
 
     @Test(timeout = 60000)
     public void testCopyFileBuffered() throws Exception {
         utils.copyFileBuffered(new File(sourseFilePath), new File(destFilePath));
         compareFiles();
     }
 
     @Test(timeout = 60000)
     public void testCopyFileBestNull() throws Exception {
         try {
             utils.copyFileBest(null, destFilePath);
             fail("source should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("source should be tested for null");
         }
 
         try {
             utils.copyFileBest(sourseFilePath, null);
             fail("dest should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("dest should be tested for null");
         }
     }
 
     @Test(timeout = 60000)
     public void testCopyFileBestWrongParams() throws Exception {
         try {
             utils.copyFileBest(System.getProperty("java.io.tmpdir"),
                     destFilePath);
             fail("source should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("source should be tested for wrong value");
         }
 
         try {
             utils.copyFileBest(sourseFilePath,
                     System.getProperty("java.io.tmpdir"));
             fail("dest should be tested for wrong value");
         } catch (IllegalArgumentException e) {
             assertNotNull("cause should be in IllegalArgumentException",
                     e.getCause());
         } catch (Exception e) {
             fail("dest should be tested for wrong value");
         }
     }
 
     @Test
     public void testCopyFileBest() throws Exception {
         utils.copyFileBest(sourseFilePath, destFilePath);
         compareFiles();
     }
 
     @Test
     public void testCopyFileTime() throws Exception {
         long start = System.currentTimeMillis();
         utils.copyFile(sourseFilePath, destFilePath);
         long copyFile = System.currentTimeMillis() - start;
 
         start = System.currentTimeMillis();
         utils.copyFileBuffered(new File(sourseFilePath), new File(destFilePath));
         long copyFileBuffered = System.currentTimeMillis() - start;
 
         start = System.currentTimeMillis();
         utils.copyFileBest(sourseFilePath, destFilePath);
         long copyFileBest = System.currentTimeMillis() - start;
 
         assertTrue("copyFile times < copyFileBuffered times",
                 copyFile > copyFileBuffered);
         assertTrue("copyFile times < copyFileBest times",
                 copyFile > copyFileBest);
         assertTrue("copyFileBuffered times < copyFileBest times",
                 copyFileBuffered > copyFileBest);
     }
 
     private void compareFiles() throws Exception {
         InputStream inSourse = null;
         InputStream inDest = null;
         byte[] bufferSource = new byte[100000];
         byte[] bufferDest = bufferSource.clone();
         try {
             inSourse = new FileInputStream(sourseFilePath);
             inDest = new FileInputStream(destFilePath);
             assertEquals("wrong dest length", inSourse.available(),
                     inDest.available());
 
             int count;
             while ((count = inSourse.read(bufferSource)) != -1) {
                 assertEquals("wrong dest length", count,
                         inDest.read(bufferDest));
                 assertTrue("files are different",
                         Arrays.equals(bufferSource, bufferDest));
             }
         } finally {
             if (null != inSourse)
                 inSourse.close();
             if (null != inDest)
                 inDest.close();
         }
     }
 }
