import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.testobject.impl.HttpTextBodyContent
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

// Send GET request to fetch user details
def getResponse = WS.sendRequest(findTestObject('GET single user'))

// Parse the GET response
def jsonResponse = new JsonSlurper().parseText(getResponse.getResponseBodyContent())

// Extract the email from the response
def email = jsonResponse.data.email

// Store the email in a Global Variable
GlobalVariable.email = email

// Prepare the POST request object
def postRequest = findTestObject('POST register successful')

// Replace the placeholder with the actual email
postRequest.setBodyContent(new HttpTextBodyContent('{"email": "' + GlobalVariable.email + '", "password": "pistol"}', "UTF-8", "application/json"))

// Send POST request to register user
def postResponse = WS.sendRequest(postRequest)

// Verify the POST request response
WS.verifyResponseStatusCode(postResponse, GlobalVariable.successCode)
