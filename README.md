# PetStore_Serenity_Cucumber


#The project directory structure

The project has build scripts for both Maven  and follows the standard directory structure used in most Serenity projects:

src
  + main
  + test
    + java                        Test runners and supporting code
    + resources
      + features                  Feature files
        + Pet                     Feature file subdirectories
             pets_get.feature     Pet Feature
             
#Serenity 2.0.91 introduced integration with Maven, Cucumber , rest-assured

#The sample scenario

Both variations of the sample project uses the sample Cucumber scenario. In this scenario, We are searching PET using PET ID:

Feature: Search by keyword

  Scenario Outline: A api GET call with Pet API with valid end point and parameters as petId should returns pet details
    Given get endpoint of Pet API
    When a request is sent to Pet API with pet id <PET ID>
    Then Pet API returns response response code <ResponseCode>
    Then Pet API returns the valid pet details of <name> and <status>
    Examples:
      | PET ID |ResponseCode|name                |status         |
      |5       | 200        | My Updated Pet Name|    pending    |
      


    @Given("^get endpoint of Pet API$")
    public void getEndpointOfPetAPI() {
        autoEndpoint = petSteps.getEndPointUrlForPetRequest();
        logger.info("auto endpoint" + autoEndpoint);
    }

    @When("^a request is sent to Pet API with pet id (-?\\d+)$")
    public void aRequestIsSentToPetAPIWithPetIdPETID(int intPET) {
        String strURL = autoEndpoint + intPET;
        logger.info("api exposed with parameters" + autoEndpoint);
        responseAuto = petSteps.getPetDetails(strURL);
        logger.info("response here" + responseAuto.asString());
    }

    @Then("^Pet API returns response response code (-?\\d+)$")
    public void petAPIReturnsResponseResponseCodeResponseCode(int resCode) {
        assertEquals(resCode, responseAuto.getStatusCode());
    }

    @Then("^Pet API returns the valid pet details of (.*) and (.*)$")
    public void petAPIReturnsTheValidPetDetailsOfNameAndStatus(String strName , String strStatus) {
        String actualValueName = responseAuto.jsonPath().getString("name").toString();
        String actualValueStatus = responseAuto.jsonPath().getString("status").toString();
        assertEquals(strName, actualValueName);
        assertEquals(strStatus, actualValueStatus);
    }


#These classes are declared using the Serenity @Steps annotation, shown below:

    @Steps
    // Step to get Endpoint of REST SERVICE

    @Steps
    // Step to get Pet Properties from Properties file

    @Steps  : Other Steps 
    // Step to extract GET response using REST ASSURED
    // Step to POST request using REST ASSURED
    // Step to Set Parameter
    // Step to Validate Response with details
    // Step to validate Response code
    // Step to create Put Request using Rest Assured
    // Step to Delete Pet Details using Rest Assured
    
# POJO Class as Setter and getter for creating JSON 
  These classes are designed to be small and self-contained, which makes them more stable and easier to maintain.
  Classs : PetDetails

# Executing the tests

To run the project, you can either just run the CucumberTestSuite test runner class, or run either mvn verify from the command line.
We can manage the scenarios using tags associated with scenario in cucumber feature file

$ mvn clean verify

#Test Report
The test results will be recorded in the target/site/serenity directory.

#To Add New Feature 
1. Create New Feature File under along with Scenarios, Tags

 + resources
      + features                  Feature files

2. Implement the Step Definations with requried logic and assertions 
3. Implement the Serenity Steps which all are requried 

 + java                        Test runners and supporting code


