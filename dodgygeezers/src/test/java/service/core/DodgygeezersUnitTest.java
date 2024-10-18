package service.core;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import service.dodgygeezers.DGQService;
import org.junit.*;
import static org.junit.Assert.assertNotNull;

public class DodgygeezersUnitTest{
    private static Registry registry;
    @BeforeClass
    public static void setup() {
        QuotationService dgqService = new DGQService();
        try {
            registry = LocateRegistry.createRegistry(1099);
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(dgqService,0);
            registry.bind(Constants.DODGY_GEEZERS_SERVICE, quotationService);
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
    @Test
    public void connectionTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_GEEZERS_SERVICE);
        assertNotNull(service);
    }

    @Test
    public void generateQuotationTest() throws Exception {
        QuotationService service = (QuotationService)
                registry.lookup(Constants.DODGY_GEEZERS_SERVICE);
        ClientInfo clientInfo = new ClientInfo("Akash", 'M', 23, 6.1, 70, false, false);
        Quotation q = service.generateQuotation(clientInfo);
        assertNotNull(q);

    }
}
