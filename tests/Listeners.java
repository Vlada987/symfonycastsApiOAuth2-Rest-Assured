package tests;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


public class Listeners implements ITestListener{

	@Override
	public void onTestStart(ITestResult result) {
		
	System.out.println("***Test Started");
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		
	System.out.println("***Test ended");
		
	}

	

}
