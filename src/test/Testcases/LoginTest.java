package Testcases;

import Base.TestBase;
import PageObjects.LoginPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
public class LoginTest extends TestBase{

	LoginPage loginPage;
	
	private static final Logger log = LogManager.getLogger(LoginTest.class);
	TestBase base=new TestBase();

	@Test
	public void loginTest() throws Exception{
		
		log.info("Initialising Driver");
		loginPage = new LoginPage(driver);
		loginPage.login("username", "password");
		base.takeScreennshot("C:\\Users\\ssp34\\OneDrive\\Desktop\\Images\\1.png");

	}

	@Test
	public void aloginTest1() throws Exception{
		
		System.out.println("aloginTest1 caled");
		base.takeScreennshot("C:\\Users\\ssp34\\OneDrive\\Desktop\\Images\\2.png");
	}
	
	@AfterClass
	public void afterClass() throws Exception{
		System.out.println("after c;ass");
		base.imageDiff("C:\\Users\\ssp34\\OneDrive\\Desktop\\Images\\1.png", "C:\\Users\\ssp34\\OneDrive\\Desktop\\Images\\2.png",loginPage.getClass().getSimpleName());
	}	
}
