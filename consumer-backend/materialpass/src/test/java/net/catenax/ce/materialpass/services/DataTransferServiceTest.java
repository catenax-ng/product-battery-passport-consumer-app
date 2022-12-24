package net.catenax.ce.materialpass.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataTransferServiceTest {

    @Test
    void getContractOfferCatalog() {
        DataTransferService service = new DataTransferService();
         assertTrue(service.getContractOfferCatalog("https://materialpass.int.demo.catena-x.net/provider/api/v1/ids/data").getContractOffers().size() > 0);
    }
}
