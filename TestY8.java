package y8counter;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class TestY8 {
	
	private WebDriver driver;
	private String baseURL = "http://y8.com";
	private String cssPathToCategory = "a[href='/categories/sports']";
	private String csspathToQuantity = "#quantity";
	private int elementCounter = 1;
	private String cssToFirstElement = "#items_container>div:nth-child(1) img";
	private int firstElementHeight;
	private int firstElementWidth;
	private String cssToEachElement = "#items_container>div:nth-child(" + elementCounter + ") img";
	private ArrayList<Integer> listNotStandardSize = new ArrayList<Integer>();
	
	@Before
	public void setUp(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);										//implicitly wait
		driver.manage().window().maximize();
	}
	
	@Test
	public void testY8Darft2(){
		driver.get(baseURL);																					//Open base URL		

		driver.findElement(By.cssSelector(cssPathToCategory)).click();											//click to category
		
		String declaredQuantityString = driver.findElement														//get text of games expected quantity
				(By.cssSelector(csspathToQuantity)).getText();
		declaredQuantityString = declaredQuantityString.replaceAll("\\D", "");
		int declaredQuantityInt = Integer.parseInt(declaredQuantityString);										//parseInt of games quantity
		System.out.println("Expected amount of games: " + declaredQuantityInt);									//print expected amount of games
		
		firstElementHeight = driver.findElement(By.cssSelector(cssToFirstElement)).getSize().getHeight();		//image height of first game
		firstElementWidth = driver.findElement(By.cssSelector(cssToFirstElement)).getSize().getWidth();			//image width of first game
		
		try{
			while(isElementPresent(By.cssSelector(cssToEachElement))){												//loop through each element
				
				WebElement eachElementToHover = driver.findElement(By.cssSelector(cssToEachElement));
				Actions hoverAction = new Actions(driver);
				hoverAction.moveToElement(eachElementToHover).perform();											//perform action - move to an element
				
				int height 	= driver.findElement(By.cssSelector(cssToEachElement)).getSize().getHeight();			//get image height
				int width = driver.findElement(By.cssSelector(cssToEachElement)).getSize().getWidth();				//get image width
				if(height != firstElementHeight || width != firstElementWidth){										//suppose that standard image size is size of first image
					listNotStandardSize.add(elementCounter);
				}			
				
				System.out.println("Current element index: " + elementCounter + ", height: "						//print each loop iteration current element index and it's size
						+ height + ", width: " + width);
				elementCounter++;
				
				cssToEachElement = "#items_container>div:nth-child(" + elementCounter + ")>a>div>img";
				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		if(!listNotStandardSize.isEmpty()){																		//print information about images size
			System.out.println("Not standard image size have next elements: " + listNotStandardSize.toString());
		}else{
			System.out.println("All images have standard height and width (like first image)"); 			
		}
		
		if(elementCounter == declaredQuantityInt){																//print information about games quantity
			System.out.println("Declared and actual games amount is equal. Declared amount: " + 
					declaredQuantityInt + ". Actual amount: " + elementCounter);
		}else{
			System.out.println("Declared and actual games amount is not equal. Declared amount: " +
					declaredQuantityInt + ". Actual amount: " + elementCounter);
		}
		
	}		
	
	@After
	public void tearDown(){
		driver.quit();
	}
	
	private boolean isElementPresent(By locator){																//method to determine whether the element is present on a page
		try{
			driver.findElement(locator);
			return true;
		}catch(NoSuchElementException noSuchElement){
			return false;
		}
	}
}
