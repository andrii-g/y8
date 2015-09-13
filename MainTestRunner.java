package y8counter;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTestRunner {

	public static void main(String[] args) {
		
		Result result = JUnitCore.runClasses(JUnitSuite.class);
		
		for(Failure failure : result.getFailures()){
			System.out.println(failure.toString());
		}
		
		System.out.println("Test wasSuccessfull: " + result.wasSuccessful());
		
	}
	
}
