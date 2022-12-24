package net.catenax.ce.materialpass.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
class DataTransferServiceTest {
    DataTransferService dataService;
    @BeforeEach
    void setUp() throws Exception {
        this.dataService = new DataTransferService();
    }
    @Test
    void getContractOfferCatalog() {
        assertTrue(dataService.getContractOfferCatalog("https://materialpass.int.demo.catena-x.net/provider/api/v1/ids/data").getContractOffers().size() > 0);
    }
}
