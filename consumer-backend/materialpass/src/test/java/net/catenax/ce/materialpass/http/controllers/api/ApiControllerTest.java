package net.catenax.ce.materialpass.http.controllers.api;

import net.catenax.ce.materialpass.models.ContractOffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class ApiControllerTest {

    @Test
    void getContract() {
        ApiController controller = new ApiController();
        assertInstanceOf(ContractOffer.class, controller.getContract("X123456789012X12345678901234566").data);
    }
}
