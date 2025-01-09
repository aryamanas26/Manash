package org.stepdefinitions;

import java.io.IOException;

import org.baseclass.TestBase;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FISAssignment extends TestBase{
	
	
	private String WebElement;

	@Given("I open the ebay site")
	public void i_open_the_ebay_site() {
		browserOpen();
	}

	@When("I search for a book")
	public void i_search_for_a_book() throws IOException {
		enterText("//input[@id='gh-ac']","Book","Book is typed in serach bar");
		click("//input[@id='gh-btn']","Search key is pressed");
	}

	@When("I select he first book")
	public void i_select_he_first_book() throws WebDriverException, IOException {
		click("//span[contains(text(),'Arknights Official Artworks VOL.1 Reset Painting S')]","clicked first book");
	
	}

	@When("i add to cart")
	public void i_add_to_cart() throws WebDriverException, IOException {
	    click("//span[contains(text(),'Add to cart')]", "added to cart");
	}

	@Then("I verify the cart")
	public void i_verify_the_cart() throws WebDriverException, IOException {
	   click("//i[@id='gh-cart-n']","cart icon");
	String expected="1";
	String actual= getText(" //select[@id='dropdown-1929498897-09131f95-270e-4683-aab8-3961b850f88a']","retreiving quantity");
	   AssertValidation(expected,actual);
	}


}
