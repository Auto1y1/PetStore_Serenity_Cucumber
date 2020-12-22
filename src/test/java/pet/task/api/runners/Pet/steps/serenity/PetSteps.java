package pet.task.api.runners.Pet.steps.serenity;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.thucydides.core.annotations.Step;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yecht.Data;
import pet.task.api.utlis.GetPropertyFromPropertiesFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static net.serenitybdd.rest.SerenityRest.rest;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by Bapu Joshi on 12/22/2020.
 */
public class PetSteps {

    private static final Logger logger = LoggerFactory.getLogger(PetSteps.class);
    private static final String PET_TOKEN_PROPERTIES = "properties/pet_token.properties";
    private GetPropertyFromPropertiesFile PetAccessParamsProps = new GetPropertyFromPropertiesFile();
    public PetDetails petDetailsSet = new PetDetails();

    // Step to get Endpoint of REST SERVICE
    @Step
    public String getEndPointUrlForPetRequest() {

        String PetTypeEndpoints = PetAccessParamsProps.
                getPropertyValueFromPropertiesFile("Pet_base_url_PetDetails",
                        PET_TOKEN_PROPERTIES);
        return PetTypeEndpoints;
    }

    // Step to get Pet Properties from Properties file
    @Step
    public String getParamForPetRequest(String strProperty) {

        String PetParams = PetAccessParamsProps.
                getPropertyValueFromPropertiesFile(strProperty,
                        PET_TOKEN_PROPERTIES);
        return PetParams;
    }

    // Step to get Request Specification of REST SERVICE
    @Step
    public static RequestSpecification getGenericRequestSpec(){

        RequestSpecBuilder rspec = new RequestSpecBuilder();
        rspec.setContentType(ContentType.JSON);
        RequestSpecification requestSpecification = rspec.build();
        return requestSpecification;
    }

    // Step to extract GET response using REST ASSURED
    @Step
    public Response getPetDetails(String PetEndpoint) {

        DateTime dtBeforeAPIRequest = new DateTime();
        long current =  dtBeforeAPIRequest.getMillis();

        Response responsePetInformation = rest().
                given().
                when().
                get(PetEndpoint);

        DateTime dtAfterResponse = new DateTime();
        logger.info("Response Time - Pet Information: "
                + Long.toString(dtAfterResponse.getMillis() - current) + " ms");

        return responsePetInformation;
    }

    // Step to POST request using REST ASSURED
    @Step
    public int getPetPostJSON(String strPetEndpoint){
        String jsonPet = null;
        int intStatus = 0;
        int id = Math.abs(new Random().nextInt());
        jsonPet= "{\"id\":" + id + ",\"category\":{\"id\":0,\"name\":\"string\"}," +
                    "\"name\":\""+ petDetailsSet.getName() +"\",\"photoUrls\":[\""+ petDetailsSet.getPhotoURL() +"\"]," +
                    "\"tags\":[{\"id\":0,\"name\":\"string\"}]," +
                    "\"status\":\"" + petDetailsSet.getStatus() + "\"}";

        intStatus = rest().given().
                accept("application/json").
                contentType("application/json").
                body(jsonPet).post(strPetEndpoint).statusCode();

        petDetailsSet.setId(id);
        return intStatus;
    }

    // Step to Set Parameter
    @Step
    public void setPetJSON(String strName , String strStatus , String strPhotoURL){
        petDetailsSet.setName(strName);
        petDetailsSet.setStatus(strStatus);
        petDetailsSet.setPhotoURL(strPhotoURL);
    }

    // Step to Validate Response with details
    @Step
    public ValidatableResponse getPetForNewlyUpdated(String autoEndpoint , int intStatusCode){
        ValidatableResponse blnFlag  ;
        String strURL = autoEndpoint+petDetailsSet.getId();
        blnFlag =rest().get(strURL)
                .then().statusCode(intStatusCode)
                .and().body("name", equalTo(petDetailsSet.getName()));
        return blnFlag;
    }

    // Step to validate Response code
    @Step
    public ValidatableResponse getStatusCode(String autoEndpoint , int intStatusCode){
        ValidatableResponse blnFlag  ;
        String strURL = autoEndpoint+petDetailsSet.getId();
        blnFlag =rest().get(strURL)
                .then().statusCode(intStatusCode);
        return blnFlag;
    }

    // Step to create Put Request using Rest Assured
    @Step
    public int getPetPutDetails(String strPetEndpoint , String strCatName ,String strTag){
        String jsonPet = null;
        int intStatus = 0;
        int id = Math.abs(new Random().nextInt());
        int id1 = Math.abs(new Random().nextInt());
        jsonPet= "{\"id\":" + id + ",\"category\":{\"id\":" + id1 + ",\"name\":\""+ strCatName +"\"}," +
                "\"name\":\""+ petDetailsSet.getName() +"\",\"photoUrls\":[\""+ petDetailsSet.getPhotoURL() +"\"]," +
                "\"tags\":[{\"id\":0,\"name\":\""+ strTag +"\"}]," +
                "\"status\":\"" + petDetailsSet.getStatus() + "\"}";

        intStatus = rest().given().
                accept("application/json").
                contentType("application/json").
                body(jsonPet).put(strPetEndpoint).statusCode();

        petDetailsSet.setId(id);
        return intStatus;
    }

    // Step to Delete Pet Details using Rest Assured
    @Step
    public int getPetDeleteDetails(String strPetEndpoint){
        int intStatus = 0;
         String strURL = strPetEndpoint+petDetailsSet.getId();
        intStatus =	rest().given().when().delete(strURL).statusCode();
        return intStatus;
    }

}
