package petstore.tests;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.Concurrent;
import net.thucydides.junit.annotations.Qualifier;
import net.thucydides.junit.annotations.UseTestDataFrom;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import petstore.endpoints.PetEndPoint;
import petstore.models.CategoryModel;
import petstore.models.PetModel;
import petstore.models.TagModel;


@RunWith(SerenityParameterizedRunner.class)
@UseTestDataFrom(value = "src/test/java/petstore/tests/testdata/pets-spec.csv")
@Concurrent(threads = "4")
public class PetCreateTest {

    private String petName;
    private int petId;
    private int statusCode;
    @Steps
    private PetEndPoint petEndPoint;
    private PetModel petModel;

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetID(int petId) {
        this.petId = petId;
    }

    public void setPetStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Qualifier
    public String qualifier() {
        return petName + "=>" + petId + "=>" + statusCode;
    }

    @After
    public void postCondition() {
        petEndPoint
                .deletePetTest(petId)
                .statusCode(200);
    }

    @Test
    public void createPetTest() {
        petModel = new PetModel(
                this.petId,
                new CategoryModel(),
                this.petName,
                new String[]{"www.zoo.com"},
                new TagModel[]{new TagModel()},
                "AVAILABLE");

        petEndPoint.createPetTest(petModel)
                .statusCode(this.statusCode);
    }
}
