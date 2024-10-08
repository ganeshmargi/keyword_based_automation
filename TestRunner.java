import org.testng.TestNG;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        XmlSuite suite = new XmlSuite();
        suite.setName("MySuite");

        XmlTest test = new XmlTest(suite);
        test.setName("MyTest");
        test.setXmlClasses(List.of(new XmlClass("your.package.TestClass")));

        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);
        testng.run();
    }
}
