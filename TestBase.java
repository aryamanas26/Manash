package org.baseclass;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import com.mashape.unirest.http.JsonNode;
import com.fasterxml.jackson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.*;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestBase {

	public static Properties prop;
	public static WebDriver driver;
	public static ExtentReports report = new ExtentReports(
			System.getProperty("user.dir") + "\\Reports\\index.html", true);
	public static ExtentTest logger;
	public static ArrayList<String> descriptionList= new ArrayList<String>();
	//public static String accessToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjQ4YTYzYmM0NzY3Zjg1NTBhNTMyZGM2MzBjZjdlYjQ5ZmYzOTdlN2MiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDUzOTQwNTU3OTQyMTU5MTI2NzciLCJoZCI6ImF0b3MubmV0IiwiZW1haWwiOiJtYW5hc2gta3VtYXIubWlzaHJhQGF0b3MubmV0IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNjE2NzA4MSwibmFtZSI6Ik1hbmFzaCBLdW1hciBNaXNocmEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jS3A1R3BwV0RHc212Zm9GMnUtOTNVU1RZWTB3VVBWZlEzM243R0xsemx3PXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik1hbmFzaCBLdW1hciIsImZhbWlseV9uYW1lIjoiTWlzaHJhIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE3MDYxNjczODEsImV4cCI6MTcwNjE3MDk4MSwianRpIjoiNDg2NDk2ZWUzNWQ2YTIzNTRmMTUzOGQzYWQ4YjA5NTU1NjJjOTMxZSJ9.lofC1VxGp4AhwB3Ks2q_6hsJHHnYgCG5zeS-yRPJl6AVbebhTvU1FwbWKxyk2--1-X-tPLFzJf48SQg6aND79ZiL-X8sFZ6ZKQI6KjyEsxppbZLIwj3ordTBj0AxEBosUecVkvZdeoSYBRkY7igJelVmSqnKoZG499Dm7IGI73sYM4aZ7FruqSyKWbHMmRYNHqqMiWaXxRRBUO3AduPV0tmSvOsR2tscrUC3GJJ9CvkvYgp1937uA_prGUp9gq6r09Qtf5b9AD6NvA0jZhBjZ2pciiyqmvxmvoXGHO2jeqzw3kRVpCF8LQS-wkY26TrIdULKQh33ftKoQth7Dej9Tg";
	//**token for KP**///
	public static String accessToken="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ik1hbmFzaCBLdW1hciBNaXNocmEiLCJlbWFpbCI6Im1hbmFzaC1rdW1hci5taXNocmFAYXRvcy5uZXQiLCJ1c2VydHlwZSI6ImtwX3VzZXIiLCJmdWxsX25hbWUiOm51bGwsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NLcDVHcHBXREdzbXZmb0YydS05M1VTVFlZMHdVUFZmUTMzbjdHTGx6bHc9czk2LWMiLCJkaXNhYmxlZCI6ZmFsc2UsIm93bmVyIjpmYWxzZSwicm9sZXMiOltdLCJtb2RlbF9jb25maWciOnsiZXh0cmEiOiJhbGxvdyJ9LCJleHAiOjE3MDkzNjg1OTJ9.q0s47atiKhnLdNh4Qq3v_Om7YFWJ1U8hVq8CFMDEKAw";
	protected static  String tokenType = "Bearer";
	//***for retreiving the query from KP***???
	protected static String apiUrl = "https://knowledgegenie.eastus.cloudapp.azure.com";
	public static String email = "manash-kumar.mishra@atos.net";
	public static  String question = "Show ticket details for TSHAD-3";
	public static String botId = "agent738771";//880255//319319
	public static String reqId = "stream8650766744706923";////stream9708804718310612//stream7812328177051155
	public static  String chatMode = "balanced";
	//for uploading the dataset
	public static String agentId = "agent879479";  // provide the agent_id
	public static String agentName = "moodys_demo";
	public static String createdBy = "manash-kumar.mishra@atos.net";
	public static String resourceGroup = "rg691395";
	public static String lf ;
	//to retreive data from JIRA
	public static String jiraBaseUrl = "https://id.atlassian.com/login?errorCode=unknown&prompt=true&continue=https%3A%2F%2Fkpai.atlassian.net%2Fwelcome%2Fsoftware%3FatlOrigin%3DeyJpIjoiZTE5NDdiMTk5NTdlNGU0MmExMjg5YWE3ZDA0N2IwOWQiLCJwIjoiYWRtaW4ifQ&state=eyJoYXNoZWRDc3JmVG9rZW4iOiI1Yjg4ZDczYmJjNTExMTA5YTgyMDAzZDRjMzAzMjViNWUwYmI5MDUxOWI2Y2Q3MTEyOGNlMzgxNTQ3NjliZjQ5In0%3D";
	public static  String apiEndpoint = "rest/api/2/search";
	public static String boardID="1";
	public static String username = "manash-kumar.mishra@eviden.com";
	public static String jiraapiToken = "ATATT3xFfGF0ATwP5BX-D8ZVpT3Hx3yjsI84c-t1jVMo8aytjiVhnsc6Rzhgb-VCounD5GtLrxrgHZhg8rDpk4oxEXElDIb9MW6v94KnUrSXE7KOnXCdqDSSNfuw59uKd8fHfZ2tiZOUS45o0fCsOBhOUsgjEquBe94xe8NRn9ZYC1xvL6zohUU=B7C6875D";
	public static String filePath="C:\\Users\\manas\\eclipse-workspace11\\TicketIQ\\requirement.csv";
	//to retreive data from confluence//
	public static String confluenceUrl="https://kpai.atlassian.net/wiki/rest/api/content/";
	public static String pageId="98442";
	public static String filePathConfluence="C:\\Users\\manas\\eclipse-workspace11\\TicketIQ\\confluence.csv";


	//https://kpai.atlassian.net/rest/api/3/issue/TSHAD-1



	public static void browserOpen() 
	{//C:\Users\manas\eclipse-workspace11\CucumberFramework\msedgedriver.exe
		System.setProperty("WebDriver.chrome.driver", "C:\\Users\\manas\\eclipse-workspace11\\CucumberFramework\\chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		//	options.addArguments("--headless");
		driver = new ChromeDriver(options);
		//driver.navigate().to("https://id.atlassian.com/login?errorCode=unknown&prompt=true&continue=https%3A%2F%2Fkpai.atlassian.net%2Fwelcome%2Fsoftware%3FatlOrigin%3DeyJpIjoiZTE5NDdiMTk5NTdlNGU0MmExMjg5YWE3ZDA0N2IwOWQiLCJwIjoiYWRtaW4ifQ&state=eyJoYXNoZWRDc3JmVG9rZW4iOiI1Yjg4ZDczYmJjNTExMTA5YTgyMDAzZDRjMzAzMjViNWUwYmI5MDUxOWI2Y2Q3MTEyOGNlMzgxNTQ3NjliZjQ5In0%3D");
		driver.navigate().to("https://www.ebay.com/");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}
	public static void open() throws IOException, InterruptedException {
		//log log = log.getlog("TestBase.class");
		prop = new Properties();
		try {//C:\Users\manas\eclipse-workspace11\CucumberFramework\Config\config.properties
			FileInputStream fi = new FileInputStream(
					"C://Users//manas//eclipse-workspace11//CucumberFramework//Config//config.properties");
			prop.load(fi);
			// log.info("File has been loaded");
			System.out.println(fi);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			e.getMessage();
		}
	}
	// fr xpath along with screenshotand highlight
	public static void click(String xpath, String actionInfo) throws IOException, WebDriverException {
		try {
			WAitUntilPageLoad();
			WebElement element = driver.findElement(By.xpath(xpath));
			highlightElement(element);
			element.click();
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo);
			GetScreenShot(actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			//logger.log(LogStatus.FAIL, "RUNTIME_ERROR : : Not able to click :" + actionInfo);
			//GetScreenShot(actionInfo);
			report.endTest(logger);
			report.flush();
			//throw new java.lang.RuntimeException(
			//"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
	}
	public static void enterText(String xpath, String value, String actionInfo) throws IOException {
		try {
			WAitUntilPageLoad();
			WebElement element = driver.findElement(By.xpath(xpath));
			highlightElement(element);                           
			element.sendKeys(value);
			System.out.println(value);
			System.out.println("Successfully: " + actionInfo);
			logger.log(LogStatus.INFO, "Successfully: " + actionInfo + "  :" + value);
			GetScreenShot(actionInfo);
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to  : " + actionInfo);
			//   logger.log(LogStatus.FAIL, "RUNTIME_ERROR : : Not able to enter :" + actionInfo + "  :" + value);
			//GetScreenShot(actionInfo);
			report.endTest(logger);
			report.flush();
			//             throw new java.lang.RuntimeException(
			//                      "RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
	}
	public static void dropdown(String xpath,String value ,String actionInfo)throws IOException, InterruptedException
	{
		WebElement element=driver.findElement(By.xpath(xpath));
		Select select= new Select(element);
		highlightElement(element); 
		select.selectByValue(value);
		System.out.println("Successfully: " + actionInfo);
		logger.log(LogStatus.INFO, "Successfully: " + actionInfo + "  :" + value);
		GetScreenShot(actionInfo);
	}

	public static java.util.List<WebElement> getElementList(String xpath, String actionInfo) throws IOException {
		try {
			WAitUntilPageLoad();
			java.util.List<WebElement> getelemList=driver.findElements(By.xpath(xpath));
			//   WebElement element = driver.findElement(By.xpath(xpath));
			//   highlightElement(element);
			//   element.click();
			for(int i=0;i<getelemList.size();i++){
				String getColumnnmae=getelemList.get(i).getText().toString();
				System.out.println(getColumnnmae);
				System.out.println("Successfully print element value is ---> " + getColumnnmae);
				logger.log(LogStatus.INFO, "Successfully print element value is ----> " + getColumnnmae);
			}
			GetScreenShot(actionInfo);
			return getelemList;
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to get elementList : " + actionInfo);
			logger.log(LogStatus.FAIL, "RUNTIME_ERROR : : Not able to get elementList :" + actionInfo);
			GetScreenShot(actionInfo);
			report.endTest(logger);
			report.flush();
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to  : " + actionInfo + "--------------->" + e);
		}
	}
	public static void GetScreenShot(String filename) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenShotPath = System.getProperty("user.dir") + "\\ExtentReport\\Screenshots\\";
		FileUtils.copyFile(scrFile, new File(screenShotPath + filename.trim() + ".png"));
		String imgeHtmlPath = logger.addScreenCapture(screenShotPath + filename.trim() + ".png");
		logger.log(LogStatus.INFO, "Screenshot :" + imgeHtmlPath);
	}
	//assertions
	public static void AssertValidation(String expected, String actual) {
		try {
			Assert.assertEquals(expected, actual);
		} catch (Error e) {
			logger.log(LogStatus.FAIL, e);
			report.endTest(logger);
			report.flush();
			throw new java.lang.RuntimeException(e);
		}
	}
	public static void AssertValidationUsingBooleanCondition(boolean condition) {
		try {
			Assert.assertTrue(condition);
		} catch (Error e) {
			logger.log(LogStatus.FAIL, e);
			report.endTest(logger);
			report.flush();

			throw new java.lang.RuntimeException(e);
		}
	}
	public static void WAitUntilPageLoad() {
		new WebDriverWait(driver, 30);
		try {
			//             wait.until(new ExpectedCondition<Boolean>() {
			//             public Boolean apply(WebDriver d) {return ("document.readyState").equals("complete");
			//             }
			//             });
		} catch (Exception e) {
			throw new RuntimeException("PAGE LOAD Exception : page not load on given interval of time");
		}
	}
	public static void highlightElement(WebElement element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		try {
			jse.executeScript("arguments[0].setAttribute('style', arguments[1]);", element,
					"color: brown; border: 4px solid black;");
			Thread.sleep(100);
		} catch (Exception e) {
			e.getMessage();
		}
	}
	public static String getText(String xpath, String actionInfo) throws IOException {
		String getTxt="";
		try {
			WAitUntilPageLoad();
			WebElement element = driver.findElement(By.xpath(xpath));
			//   highlightElement(element);
			String strnumber = element.getText();
			System.out.println("Successfully gettxt : " + strnumber + actionInfo);
			logger.log(LogStatus.INFO, "Successfully gettxt : " + actionInfo);
			GetScreenShot(actionInfo);
			return getTxt;
		} catch (Exception e) {
			System.out.println("RUNTIME_ERROR : : Not able to gettxt : " + actionInfo);
			logger.log(LogStatus.FAIL, "RUNTIME_ERROR : : Not able to gettxt :" + actionInfo);
			GetScreenShot(actionInfo);
			report.endTest(logger);
			report.flush();
			throw new java.lang.RuntimeException(
					"RUNTIME_ERROR : : Not able to gettxt : " + actionInfo + "--------------->" + e);
		}
	}
	public static void StartTest(String TestName) {
		logger = report.startTest(TestName);
	}
	public static void EndTest() {
		report.endTest(logger);
		report.flush();
	}

	protected static void showMessageDialog(String message, String title) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}



	public static void writeValueToCsv(ArrayList<String> value, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {
			for(String des:value)
			{
				writer.append(des);
				writer.append("\n");
			}
			System.out.println("Value has been written to the CSV file: " + filePath);
		} catch (IOException e) {
			System.err.println("Error writing to the CSV file: " + e.getMessage());
		}
	}
	public static String connectToEndpoint(String apiUrl, String tokenType, String accessToken) {
		try {
			// Create URL object
			System.out.println(apiUrl);
			URL url = new URL(apiUrl);

			// Open connection
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set the request method to GET (or POST, etc.)
			connection.setRequestMethod("GET");

			// Set the authorization header with the token type and access token
			connection.setRequestProperty("Authorization", tokenType + " " + accessToken);

			// Get the response code
			int responseCode = connection.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			// Read the response
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			StringBuilder response = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				response.append(line);
			}
			reader.close();

			// Close the connection
			connection.disconnect();

			// Return the response as a string
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			// Handle exceptions or return an appropriate error message
			return "Error connecting to the endpoint: " + e.getMessage();
		}
	}
	//code for retrieve response from KP
	public static String makeApiRequest(String email, String question, String botId, String reqId, String chatMode, String accessToken) throws IOException {
		OkHttpClient client = new OkHttpClient();

		// Prepare the request body
		MediaType mediaType = MediaType.parse("text/event-api; charset=utf-8");
		String requestBody = String.format("{\n  \"email\": \"%s\",\n  \"question\": \"%s\",\n  \"bot_id\": \"%s\",\n  \"history\": [],\n  \"req_id\": \"%s\",\n  \"chat_mode\": \"%s\"\n}",
				email, question, botId, reqId, chatMode);
		RequestBody body = RequestBody.create(mediaType, requestBody);

		// Build the request
		Request request = new Request.Builder()
				.url("https://knowledgegenie.eastus.cloudapp.azure.com/api/rag_search_stream")
				.method("POST", body)
				.addHeader("Access-Control-Allow-Credentials", "true")
				.addHeader("Access-Control-Allow-Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("Content-Type", "text/event-api; charset=utf-8")
				.addHeader("Authorization", "Bearer " + accessToken)
				.addHeader("authority", "knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("method", "POST")
				.addHeader("path", "/api/rag_search_stream")
				.addHeader("scheme", "https")
				.addHeader("Accept", "\"/\"")
				.addHeader("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDUzOTQwNTU3OTQyMTU5MTI2NzciLCJoZCI6ImF0b3MubmV0IiwiZW1haWwiOiJtYW5hc2gta3VtYXIubWlzaHJhQGF0b3MubmV0IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNzExODc3NCwibmFtZSI6Ik1hbmFzaCBLdW1hciBNaXNocmEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jS3A1R3BwV0RHc212Zm9GMnUtOTNVU1RZWTB3VVBWZlEzM243R0xsemx3PXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik1hbmFzaCBLdW1hciIsImZhbWlseV9uYW1lIjoiTWlzaHJhIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE3MDcxMTkwNzQsImV4cCI6MTcwNzIwNTQ3NSwianRpIjoiODNjNjAwM2ZiZmU0NWMyNmM4ODZmMjJiOTcwYThkYjc1M2NlYWQ5ZSJ9.M5gJ0j_x4H_C1_befBm8FQmqavaSWLWHvToY23oRTZk")
				.addHeader("Content-Type", "application/json")
				.addHeader("Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("Referer", "https://knowledgegenie.eastus.cloudapp.azure.com/support")
				.build();


		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				// Return the response body as a string
				return response.body().string();

			} else {
				// Handle non-successful response
				return "Error: " + response.code() + " - " + response.message();
			}

		}
	}

	//code to upload file to KP
	public static String uploadFile(String agentId, String agentName, String createdBy, String resourceGroup,
			String lf, String accessToken) throws IOException {
		OkHttpClient client = new OkHttpClient();

		// Prepare the request body
		File file = new File(lf);
		RequestBody requestBody = new MultipartBody.Builder()
				.setType(MultipartBody.FORM)
				.addFormDataPart("agent_id", agentId)
				.addFormDataPart("agent_type", "unstructured")
				.addFormDataPart("agent_name", agentName)
				.addFormDataPart("suggestions", "[]")
				.addFormDataPart("avatar_color", "#5A1A27")
				.addFormDataPart("source", "Upload")
				.addFormDataPart("created_by", createdBy)
				.addFormDataPart("creation_date", "")
				.addFormDataPart("modification_date", "")
				.addFormDataPart("resource_group", resourceGroup)
				.addFormDataPart("files", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file))
				.addFormDataPart("training_status", "")
				.addFormDataPart("config", "{\"welcome_message\":\"Hello there! 👋🏻\\r\\nI am Eviden Knowledge Pilot\\r\\nyour very own AI companion, crafted to enhance and simplify your life.\",\"next_step\":true,\"show_thoughts\":true,\"show_sources\":true,\"pipeline\":\"semantic\",\"chunk_size\":1000,\"n_sources\":3,\"chat_modes\":[\"focused\"],\"system_prompt\":\"You are Knowledge Pilot, a helpful, respectful, honest and an intelligent assistant built by EVIDEN to help answers queries.\\r\\nYou have access to the existing conversation history and a list of relevant sources which contain the answer to the user query, look at the sources and the conversation history and provide a detailed response to the user while adhering to the rules below.\",\"columns\":[]}")
				.build();

		// Build the request
		Request request = new Request.Builder()
				.url("https://knowledgegenie.eastus.cloudapp.azure.com/api/rag_search_stream")  // Replace with your actual API endpoint
				.addHeader("Authorization", "Bearer " + accessToken)
				.post(requestBody)
				.build();

		// Execute the request
		try (Response response = client.newCall(request).execute()) {
			if (response.isSuccessful()) {
				// Return the response body as a string
				return response.body().string();
			} else {
				// Handle non-successful response
				return "Error: " + response.code() + " - " + response.message();
			}
		}
	}

	public static File getLatestDownloadedFile(String folderPath, String baseFileName) {
		File folder = new File(folderPath);
		File[] matchingFiles = folder.listFiles((dir, name) -> name.startsWith(baseFileName));

		if (matchingFiles != null && matchingFiles.length > 0) {
			for(int i=0;i<matchingFiles.length;i++)
			{
				// Sort files based on their names in descending order
				Arrays.sort(matchingFiles, Comparator.comparing(File::lastModified).reversed());
			}
			return matchingFiles[0]; // The first file is the latest one
		}
		else {
			return null; // No matching files found
		}
	}



	//**method to bind the response from Jira to Arraylist***///
	public static ArrayList<String> executeJiraRequestAndStoreResponse() {
		ArrayList<String> responseList = new ArrayList<>();

		try {
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			Request request = new Request.Builder()
					.url("https://kpai.atlassian.net/rest/agile/1.0/board/1/issue")
					.method("GET", null)
					.addHeader("Authorization", "Basic bWFuYXNoLWt1bWFyLm1pc2hyYUBldmlkZW4uY29tOkFUQVRUM3hGZkdGMEFUd1A1QlgtRDhaVnBUM0h4M3lqc0k4NGMtdDFqVk1vOGF5dGppVmhuc2M2UnpoZ2ItVkNvdW5ENUd0THJ4cmdIWmhnOHJEcGs0b3hFWEVsREliOU1XNnY5NEtuVXJTWEU3S09uWENkcURTU05mdXc1OXVLZDhmSGZaMnRpWk9VUzQ1bzBmQ3NPQmhPVXNnakVxdUJlOTR4ZThOUm45WllDMXh2TDZ6b2hVVT1CN0M2ODc1RA==")
					.addHeader("Cookie", "atlassian.xsrf.token=86f6cdb4ec6481c49d758bf64751d8890c319feb_lin")
					.build();
			Response response = client.newCall(request).execute();
			responseList.add(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseList;
	}
	//**storing all feed to csv files**8///
	public static void storeResponseListToCSV(ArrayList<String> responseList, String filePath) {
		try (FileWriter writer = new FileWriter(filePath)) {
			for (String response : responseList) {
				writer.write(response);
				writer.write("\n");
			}
			System.out.println("Data has been written to " + filePath);
		} catch (IOException e) {
			System.err.println("Error writing CSV file: " + e.getMessage());
		}
	}	

	//*** method for retreival from Confluence***???

	public static ArrayList<String> getPageContent(String confluenceUrl, String pageId, String jiraapiToken) throws IOException {
		OkHttpClient client1 = new OkHttpClient();
		MediaType mediaType = MediaType.parse("text/plain");
		ArrayList<String> responseList1 = new ArrayList<>(); 
		RequestBody body = RequestBody.create(mediaType, "");

		Request request = new Request.Builder()
				.url(confluenceUrl + pageId + "?expand=body.storage")
				.method("GET", null)
				//.addHeader("Authorization", jiraapiToken)
				.addHeader("Authorization", "Basic bWFuYXNoLWt1bWFyLm1pc2hyYUBldmlkZW4uY29tOkFUQVRUM3hGZkdGMEFUd1A1QlgtRDhaVnBUM0h4M3lqc0k4NGMtdDFqVk1vOGF5dGppVmhuc2M2UnpoZ2ItVkNvdW5ENUd0THJ4cmdIWmhnOHJEcGs0b3hFWEVsREliOU1XNnY5NEtuVXJTWEU3S09uWENkcURTU05mdXc1OXVLZDhmSGZaMnRpWk9VUzQ1bzBmQ3NPQmhPVXNnakVxdUJlOTR4ZThOUm45WllDMXh2TDZ6b2hVVT1CN0M2ODc1RA==")
				.addHeader("Cookie", "atlassian.xsrf.token=86f6cdb4ec6481c49d758bf64751d8890c319feb_lin")
				.build();

		Response response = client1.newCall(request).execute();
		responseList1.add(response.body().string());
		return responseList1;

	}

//	//**storing API feed from JIRA to CSv**////
//	public static void writeCsvvalues(JSONArray issues, Set<String> allFields) {
//		// Extract relevant information
//		String[] headers = {
//				"Summary", "Issue key", "Issue Type", "Status", "Project key",
//				"Project name", "Priority", "Assignee",
//				"Reporter", "Creator", "Description", "Sprint"
//		};
//		// Print the data for each field
//		try (FileWriter csvWriter = new FileWriter(filePath)) {
//			for (int i = 0; i < headers.length ; i++) {
//				csvWriter.write(headers[i] + ",");
//			}
//			csvWriter.write("\n");
//			for (int i = 0; i < issues.length(); i++) {
//				JSONObject fields = issues.getJSONObject(i).getJSONObject("fields");
//				csvWriter.write(fields.getString("summary")+ ",");
//				csvWriter.write(issues.getJSONObject(i).getString("key")+ ",");
//				csvWriter.write(fields.getJSONObject("issuetype").getString("name")+ ",");
//				csvWriter.write(fields.getJSONObject("status").getString("name")+ ",");
//				csvWriter.write(fields.getJSONObject("project").getString("key")+ ",");
//				csvWriter.write(fields.getJSONObject("project").getString("name")+ ",");
//				csvWriter.write(fields.getJSONObject("priority").getString("name")+ ",");
//				csvWriter.write(fields.getJSONObject("assignee").getString("displayName")+ ",");
//				csvWriter.write(fields.getJSONObject("reporter").getString("displayName")+ ",");
//				csvWriter.write(fields.getJSONObject("creator").getString("displayName")+ ",");
//				csvWriter.write(fields.getString("description")+ ",");
//				//csvWriter.write(fields.getJSONObject("sprint").getString("name")+ ",");
//				//csvWriter.write(fields.getString("sprint")+ ",");
//				/* JSONArray comments =fields.getJSONObject("comment").getJSONArray("comments");
//            	 System.out.println("comments" +comments);
//
//            	 for (int j = 0; j < comments.length(); j++) {
//            	        String com = comments.getString(j);
//            	        csvWriter.write(com+"\t");
//            	 }*/
//
//				csvWriter.write("\n");
//				//csvWriter.write("\t" +issue.getJSONObject("fields").getJSONObject("comment").g("comments")+ ",");
//
//			}
//
//		}
//
//		catch (IOException e) {
//			System.err.println("Error writing CSV file: " + e.getMessage());
//		}
//
//
//		System.out.println(); // Move to the next line
//
//	}
	
	
	//**latest code for transcribing it to CSv**29 Feb??
	
	  /* function to Store Jira json response into csv*/
		    public static void writeCsvvalues(JSONArray issues, Set<String> allFields) {
		        // Extract relevant information
		    	 String[] headers = {
		                 "Summary", "Issue key", "Issue Type", "Status", "Project key",
		                 "Project name", "Priority", "Assignee",
		                 "Reporter", "Creator", "description", "Sprint","comment"
		         };
		        // Print the data for each field
		        try (FileWriter csvWriter = new FileWriter(filePath)) {
		        	  for (int i = 0; i < headers.length ; i++) {
		        		  csvWriter.write(headers[i] + ",");
		              }
		        	  csvWriter.write("\n");
		        	for (int i = 0; i < issues.length(); i++) {
		        		JSONObject fields = issues.getJSONObject(i).getJSONObject("fields");
		        		 String summary = fields.getString("summary").replace("\n", " ");
		        		csvWriter.write(summary+ ",");
		        		csvWriter.write(issues.getJSONObject(i).getString("key")+ ",");
		        		csvWriter.write(fields.getJSONObject("issuetype").getString("name")+ ",");
		        		csvWriter.write(fields.getJSONObject("status").getString("name")+ ",");
		        		csvWriter.write(fields.getJSONObject("project").getString("key")+ ",");
		        		csvWriter.write(fields.getJSONObject("project").getString("name")+ ",");
		        		csvWriter.write(fields.getJSONObject("priority").getString("name")+ ",");
		        		
		        		 if (fields.has("assignee") && fields.get("assignee") instanceof JSONObject) {
		                     JSONObject assigneeObject = fields.getJSONObject("assignee");
		                     csvWriter.write(assigneeObject.getString("displayName")+ ",");
		                 }
		        		
		        		csvWriter.write(fields.getJSONObject("reporter").getString("displayName")+ ",");
		        		csvWriter.write(fields.getJSONObject("creator").getString("displayName")+ ",");
		        		String description = '"'+fields.getString("description")+'"';
		            	csvWriter.write(description+ ",");
		            	csvWriter.write(fields.getJSONObject("sprint").getString("name")+ ",");
		            	 JSONArray comments =fields.getJSONObject("comment").getJSONArray("comments");
		            	
		            	
		            	 for (int j = 0; j < comments.length(); j++) {
		            	        String com = comments.getJSONObject(j).getString("body");
		            	        String replacedString = com.replace("\n", " ");
		            	        csvWriter.write(replacedString+"\t");
		            	 }
		            	
		            	 csvWriter.write("\n");
		            	//csvWriter.write("\t" +issue.getJSONObject("fields").getJSONObject("comment").g("comments")+ ",");
		            	
		            }
		           
		        }
		        
		        catch (IOException e) {
					System.err.println("Error writing CSV file: " + e.getMessage());
				}
		        
	 
		        System.out.println(); // Move to the next line
		    
		    }

	public static String convertJsonToPlainText(String jsonString) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.readTree(jsonString);
			String name = jsonNode.path("body").path("storage").path("value").asText();
			return name;

		} catch (Exception e) {
			e.printStackTrace();
			return "Error converting JSON to plain text.";
		}
	}
	public static String extractHtmlContent(String jsonString) {
		// Extract the content field from JSON (replace this with your actual JSON parsing logic)
		// For simplicity, we assume a simple JSON structure here
		int startIndex = jsonString.indexOf("\"content\": \"") + 12;
		int endIndex = jsonString.lastIndexOf("\"");
		return jsonString.substring(startIndex, endIndex);
	}

	public static StringBuilder convertHtmlToPlainText(String htmlContent) {
		// Use Jsoup to parse HTML and extract text
		Document document = Jsoup.parse(htmlContent);
		StringBuilder plainText = new StringBuilder();
		// Traverse the HTML structure and extract text
		for (Element element : document.select("*")) {
			// Handle different HTML tags
			switch (element.tagName()) {
			case "p":
				plainText.append(element.text()).append("\n");
				break;
			case "br":
				plainText.append("\n");
				break;
			case "li":
				plainText.append("• ").append(element.text()).append("\n");
				break;
				// You can add more cases for other tags as needed
			}
		}

		return plainText;
	}

	public static ArrayList<String> convertStringBuilderToArrayList(StringBuilder stringBuilder) {
		// Split the StringBuilder content based on newline characters
		String[] linesArray = stringBuilder.toString().split("\\n");

		// Convert the array to an ArrayList
		List<String> linesList = Arrays.asList(linesArray);
		return new ArrayList<>(linesList);
	}

	//*805feb retreival from KPnew agent//latest code for retreival//

	public static String makeHttpRequest() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				.build();
		MediaType mediaType = MediaType.parse("application/ecmascript");
		RequestBody body = RequestBody.create(mediaType, "{\r\n  \"email\": \"manash-kumar.mishra@atos.net\",\r\n  \"question\": \"show details for ticket TSHAD-6\",\r\n  \"bot_id\": \"agent738771\",\r\n  \"history\": [],\r\n  \"req_id\": \"stream8650766744706923\",\r\n  \"session_id\": \"session7388028468276457\",\r\n  \"chat_mode\": \"focused\",\r\n  \"regen_flag\": false\r\n}");
		Request request = new Request.Builder()
				.url("https://knowledgegenie.eastus.cloudapp.azure.com/api/rag_search_stream")
				.method("POST", body)
				.addHeader("Access-Control-Allow-Credential", "true")
				.addHeader("Access-Control-Allow-Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("authority", "knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("method", "POST")
				.addHeader("path", "/api/rag_search_stream")
				.addHeader("scheme", "https")
				.addHeader("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ik1hbmFzaCBLdW1hciBNaXNocmEiLCJlbWFpbCI6Im1hbmFzaC1rdW1hci5taXNocmFAYXRvcy5uZXQiLCJ1c2VydHlwZSI6ImtwX3VzZXIiLCJmdWxsX25hbWUiOm51bGwsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vYS9BQ2c4b2NLcDVHcHBXREdzbXZmb0YydS05M1VTVFlZMHdVUFZmUTMzbjdHTGx6bHc9czk2LWMiLCJkaXNhYmxlZCI6ZmFsc2UsIm93bmVyIjpmYWxzZSwicm9sZXMiOltdLCJtb2RlbF9jb25maWciOnsiZXh0cmEiOiJhbGxvdyJ9LCJleHAiOjE3MDkzNjg1OTJ9.q0s47atiKhnLdNh4Qq3v_Om7YFWJ1U8hVq8CFMDEKAw")
				.addHeader("Content-Type", "application/ecmascript")
				.addHeader("Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
				.addHeader("Referer", "https://knowledgegenie.eastus.cloudapp.azure.com/support")
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			// Return the response body as a string
			return response.body().string();

		} else {
			// Handle non-successful response
			return "Error: " + response.code() + " - " + response.message();
		}
	}
	
	
	

}
//    OkHttpClient client = new OkHttpClient().newBuilder()
//            .build();
//    MediaType mediaType = MediaType.parse("text/event-api; charset=utf-8,application/json");
//    RequestBody body = RequestBody.create(mediaType, "{\r\n  \"email\": \"manash-kumar.mishra@atos.net\",\r\n  \"question\": \"Show detailsa bout ticket TSHAD-6\",\r\n  \"bot_id\": \"agent142124\",\r\n  \"history\": [],\r\n  \"req_id\": \"stream9192682735396920\",\r\n  \"session_id\": \"session7322196544950611\",\r\n  \"chat_mode\": \"balanced\",\r\n  \"regen_flag\": false\r\n}");
//    Request request = new Request.Builder()
//            .url("https://knowledgegenie.eastus.cloudapp.azure.com/api/rag_search_stream")
//            .method("POST", body)
//            .addHeader("Access-Control-Allow-Credentials", "true")
//            .addHeader("Access-Control-Allow-Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
//            .addHeader("Content-Type", "text/event-api; charset=utf-8")
//            .addHeader("authority", "knowledgegenie.eastus.cloudapp.azure.com")
//            .addHeader("method", "POST")
//            .addHeader("path", "/api/rag_search_stream")
//            .addHeader("scheme", "https")
//            .addHeader("Accept", "\"/\"")
//            .addHeader("Authorization", "bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI2Nzk0NTc3MjEwNTItN3U1ZmF1M28yN2pzN2Q0cXZxb2kxNXQ1dGIzYnZtYW4uYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDUzOTQwNTU3OTQyMTU5MTI2NzciLCJoZCI6ImF0b3MubmV0IiwiZW1haWwiOiJtYW5hc2gta3VtYXIubWlzaHJhQGF0b3MubmV0IiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5iZiI6MTcwNzIxNjMzMSwibmFtZSI6Ik1hbmFzaCBLdW1hciBNaXNocmEiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jS3A1R3BwV0RHc212Zm9GMnUtOTNVU1RZWTB3VVBWZlEzM243R0xsemx3PXM5Ni1jIiwiZ2l2ZW5fbmFtZSI6Ik1hbmFzaCBLdW1hciIsImZhbWlseV9uYW1lIjoiTWlzaHJhIiwibG9jYWxlIjoiZW4iLCJpYXQiOjE3MDcyMTY2MzEsImV4cCI6MTcwNzMwMzAzMiwianRpIjoiNGE1MGQ4ZDIyNTczMmY2MzdiOThlZmMxOWM4YTcxMWNjZWM3NDBlMCJ9.LE1vU7I3NgDJXM0DJclLx0D2HTFjdFUxUZAvz9U4mec")
//            .addHeader("Content-Type", "application/json")
//            .addHeader("Origin", "https://knowledgegenie.eastus.cloudapp.azure.com")
//            .addHeader("Referer", "https://knowledgegenie.eastus.cloudapp.azure.com/support")
//            .build();
//    try (Response response = client.newCall(request).execute()) {
//    	if (response.isSuccessful()) {
//    		// Return the response body as a string
//    		return response.body().string();
//
//    	} else {
//    		// Handle non-successful response
//    		return "Error: " + response.code() + " - " + response.message();
//    	}
//   
//}
//}
//}










