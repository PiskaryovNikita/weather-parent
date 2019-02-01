package main.tasks.n10.arraycollectionTask;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import interfaces.junit.JunitTester;
import junit.framework.TestSuite;

@RunWith(Suite.class)
//Классы каждый из которых предст набор тестов.
@SuiteClasses({ ArrayCollectionIteratorTest.class, ArrayCollectionTest.class })
public class JunitTesterImpl implements JunitTester {

	@Override
	public TestSuite suite() {
		TestSuite testSuite = new TestSuite(
				new Class[] { ArrayCollectionIteratorTest.class, ArrayCollectionTest.class });
		return testSuite;
	}

}
