package dynamicreports.reports;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CustomerReportTest {
	private CustomerReport target;
	
	@BeforeMethod
	public void beforeMethod()
	{
		target = new CustomerReport();
	}
	
	@Test
	public void report()
	{
		target.report();
	}
}
