package main.tasks.n10.serialization;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CyclicCollectionTest.class, CyclicItemTest.class, ForSerializationOuter.class, NotSerializable.class,
		PathClassLoaderTest.class, SerializableUtilsTest2.class })
public class AllTests {

}
