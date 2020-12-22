package pet.task.api.runners.Pet;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Created by Bapu Joshi on 12/22/2020.
 */
//Cucumber With Serenity Runner to Execute Rest ASSURED TEST
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features="src\\test\\resources\\features")
      //  tags = {"@DryRunOnly"})
public class PetTest {
}
