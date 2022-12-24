package net.catenax.ce.materialpass.http.controllers.api;

import net.catenax.ce.materialpass.models.ContractOffer;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
@RunWith(SpringRunner.class)

@SpringBootTest
class ApiControllerTest {
    @Autowired ApiController controller;
    @Test
    void getContract() {
        assertInstanceOf(ContractOffer.class, controller.getContract("X123456789012X12345678901234566").data);
    }
}
