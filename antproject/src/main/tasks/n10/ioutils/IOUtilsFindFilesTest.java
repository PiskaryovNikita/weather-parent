 package main.tasks.n10.ioutils;
 
 import static junit.framework.Assert.assertEquals;
 import static junit.framework.Assert.assertTrue;
 import static junit.framework.Assert.fail;
 import interfaces.task6.IOUtils;
import main.tasks.n6.IOUtilsImpl;

import java.io.File;
 import java.io.FileOutputStream;
 import java.util.ArrayList;
 import java.util.List;
 
 import org.junit.After;
 import org.junit.Before;
 import org.junit.Test;
 
 public class IOUtilsFindFilesTest {
     private static String TEMP_DIR = "findFiles";
     private static String TEMP_SUB1_DIR = "sub1";
     private static String TEMP_SUB2_DIR = "sub2";
     private static String TEMP_SUB3_DIR_CLEAR = "sub3";
     private static String TEMP_SUB1_SUB1_DIR = "sub1";
     private static String NAME = "file";
     private static String EXT1 = ".txt";
     private static String EXT2 = ".dat";
     private static String[][] PATH = new String[][] {
             { "", "file" + EXT1 },
             { TEMP_SUB1_DIR + File.separator, NAME + "1" + EXT1 },
             {
                     TEMP_SUB1_DIR + File.separator + TEMP_SUB1_SUB1_DIR
                             + File.separator, NAME + "2" + EXT1 },
             { TEMP_SUB2_DIR + File.separator, NAME + "3" + EXT2 } };
 
     private static Class<?> testingClass = IOUtilsImpl.class;
 
     private IOUtils utils;
     private File tempDir;
 
     @Before
     public void before() throws Exception {
         utils = (IOUtils) testingClass.newInstance();
 
         String tempDirPath = System.getProperty("java.io.tmpdir")+ File.separatorChar + TEMP_DIR
                 + File.separatorChar;
 
         tempDir = new File(tempDirPath);
 
         for (String[] item : PATH) {
             String fileOutputDir = tempDirPath + item[0];
             new File(fileOutputDir).mkdirs();
             new FileOutputStream(fileOutputDir + item[1]).close();
         }
         new File(tempDirPath + TEMP_SUB3_DIR_CLEAR).mkdirs();
     }
 
     @After
     public void after() throws Exception {
         delTree(tempDir);
         tempDir.delete();
     }
 
     private void delTree(File dir) {
         for (File file : dir.listFiles()) {
             if (file.isDirectory()) {
                 delTree(file);
             }
             file.delete();
         }
     }
 
     @Test
     public void testFindFilesNull() throws Exception {
         try {
             utils.findFiles(null);
             fail("dir should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("dir should be tested for null");
         }
     }
 
     @Test
     public void testFindFilesClean() throws Exception {
         try {
             utils.findFiles("");
             fail("dir should be tested for wrong value and throw "
                     + "IllegalArgumentException");
         } catch (IllegalArgumentException e) {
         } catch (Exception e) {
             fail("dir should be tested for wrong value and throw "
                     + "IllegalArgumentException");
         }
     }
 
     @Test
     public void testFindFilesClearDir() throws Exception {
         String[] result = utils.findFiles(tempDir.getAbsolutePath()
                 + File.separator + TEMP_SUB3_DIR_CLEAR);
 
         assertEquals("result should be clear", 0, result.length);
     }
 
     @Test
     public void testFindFiles() throws Exception {
         String[] result = utils.findFiles(tempDir.getAbsolutePath());
         assertResult(result, null);
     }
 
     @Test
     public void testFindFilesExtNull() throws Exception {
         try {
             utils.findFiles(null, "");
             fail("dir should be tested for null");
         } catch (NullPointerException e) {
         } catch (Exception e) {
             fail("dir should be tested for null");
         }
     }
 
     @Test
     public void testFindFilesExtClean() throws Exception {
         try {
             utils.findFiles("", "");
             fail("dir should be tested for wrong value and throw "
                     + "IllegalArgumentException");
         } catch (IllegalArgumentException e) {
         } catch (Exception e) {
             fail("dir should be tested for wrong value and throw "
                     + "IllegalArgumentException");
         }
     }
 
     @Test
     public void testFindFilesExtNullExt() throws Exception {
         try {
             String[] result = utils.findFiles(tempDir.getAbsolutePath(), null);
             assertResult(result, null);
         } catch (Exception e) {
             fail("Error:" + e
                     + " if extention == null your should return all files");
         }
     }
 
     @Test
     public void testFindFilesExtClearExt() throws Exception {
         String[] result = utils.findFiles(tempDir.getAbsolutePath(), "");
         assertResult(result, "");
     }
 
     @Test
     public void testFindFilesExtClear() throws Exception {
         String[] result = utils.findFiles(tempDir.getAbsolutePath()
                 + File.separator + TEMP_SUB3_DIR_CLEAR, "");
         assertEquals("result should be clear", 0, result.length);
     }
 
     @Test
     public void testFindFilesExt() throws Exception {
         String[] result = utils.findFiles(tempDir.getAbsolutePath(), EXT1);
         assertResult(result, EXT1);
     }
 
     private void assertResult(String[] result, String ext) {
         int countOfFiles = 0;
         List<String> list = new ArrayList<String>();
         for (String str : result) {
             list.add(str.toLowerCase());
         }
         for (String[] item : PATH) {
             String path = (tempDir.getAbsolutePath() + File.separator + item[0] + item[1])
                     .toLowerCase();
             if (null == ext || "".equals(ext) || path.endsWith(ext)) {
                 assertTrue("file '" + path + "' should be in list" + list, list
                         .contains(path));
                 countOfFiles++;
             }
         }
         assertEquals("wrong count of files", countOfFiles, result.length);
     }
 }
