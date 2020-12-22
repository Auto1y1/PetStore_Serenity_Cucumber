package pet.task.api.runners.Pet.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import net.thucydides.core.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pet.task.api.runners.Pet.steps.serenity.PetDetails;
import pet.task.api.runners.Pet.steps.serenity.PetSteps;


import static org.junit.Assert.assertEquals;

/**
 * Created by Bapu Joshi on 12/22/2020.
 */
public class PetStepDefinition {
    public PetDetails petDetailsSet = new PetDetails();
    private static final Logger logger = LoggerFactory.getLogger(PetStepDefinition.class);
    private static final int SUCCESS_200 = 200;
    private static final int NOT_FOUND_404 = 404;

    // Steps Class of Serenity To get methods
    @Steps
    PetSteps petSteps;

    // Values to define paramters for rest call
    String autoEndpoint;
    Response responseAuto;

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

    @Then("^Pet API returns Error Message (.*)$")
    public void petAPIReturnsErrorMessageErrorMessage(String errMessage) {
        String actualValueMessage = responseAuto.jsonPath().getString("message").toString();
        assertEquals(errMessage, actualValueMessage);
    }

    @When("^a request is sent to Pet API with pet Status (.*)$")
    public void aRequestIsSentToPetAPIWithPetStatusStatus(String strStatus) {
        String strURL = autoEndpoint+petSteps.getParamForPetRequest("PetFindByStatus")+strStatus;
        logger.info("api exposed with parameters" + strURL);
        responseAuto = petSteps.getPetDetails(strURL);
        logger.info("response here" + responseAuto.asString());
    }

    @Then("^Validate the Pet (.*)$")
    public void validateThePetName(String strName) {
        assertThat(responseAuto.jsonPath().getString("name").toString().contains(strName));
    }

    @When("^I add the pet to the store with details (.*) , (.*) and (.*)$")
    public void iAddThePetToTheStoreWithDetailsNameStatusAndPhotoURL(String strName , String strStatus , String strPhotoURL) {
        petSteps.setPetJSON(strName , strStatus , strPhotoURL);
        String strURL = petSteps.getParamForPetRequest("Pet_Add_New");
        int intCode = petSteps.getPetPostJSON(strURL);
        assertEquals(SUCCESS_200, intCode);
    }

    @Then("^The Pet details should be present with (.*) , (.*)$")
    public void thePetDetailsShouldBePresentWithNameStatus(String strName , String strStatus) {
        assertThat(petSteps.getPetForNewlyUpdated(autoEndpoint, SUCCESS_200));
    }


    @Then("^I have updated the (.*) and (.*)$")
    public void iHaveUpdatedTheTagNameAndCategoryName(String strCatName , String strTag) {
        petSteps.getPetPutDetails(petSteps.getParamForPetRequest("Pet_Add_New") , strCatName , strTag);
    }

    @Then("^The Pet details are not present$")
    public void thePetDetailsAreNotPresent() {
        petSteps.getStatusCode(petSteps.getEndPointUrlForPetRequest() , NOT_FOUND_404);
    }

    @When("^I have deleted the Newly Added Pet$")
    public void iHaveDeletedTheNewlyAddedPet() {
        int statusActual = petSteps.getPetDeleteDetails(petSteps.getEndPointUrlForPetRequest());
        assertEquals(SUCCESS_200, statusActual);
    }
}
