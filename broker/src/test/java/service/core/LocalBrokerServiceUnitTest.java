package service.core;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
//import static org.junit.Assert.*;
import java.util.List;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import org.junit.*;
//import static org.junit.*;


public class LocalBrokerServiceUnitTest {
    private static Registry registry;
    private static BrokerService brokerService;

    @BeforeClass
    public static void setup() throws RemoteException {
        registry = LocateRegistry.createRegistry(1099);
        brokerService = new LocalBrokerService(registry);
/*
        AFQService afqService = new AFQService();
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        // Register the DodgyGeezers service
        DGQService dgqService = new DGQService();
        registry.rebind(Constants.DODGY_GEEZERS_SERVICE, dgqService);

        // Register the GirlsAllowed service
        GAQService gaqService = new GAQService();
        registry.rebind(Constants.GIRLS_ALLOWED_SERVICE, gaqService);
*/
    }


    @Test
    public void testNoServicesRegistered() throws Exception {
        // Create a sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("John", "Doe", 'M', 35, 3, 600);

        // Call getQuotations on the broker service
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Directly assert that the size of quotations is zero, since no services are registered
        assert quotations.size() == 0 : "Expected 0 quotations when no services are registered.";
    }

    @Test
    public void testCheckServicesInRegistry() throws Exception {
        // Register a service to simulate available services
        AFQService afqService = new AFQService();
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        // Create a sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("Akash", 'M', 23, 6.1, 70, false, false);

        // Call getQuotations on the broker service
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Directly assert that the size of quotations is greater than 0
        assert quotations.size() > 0 : "Expected at least one quotation when services are registered.";

        // Optionally print the companies
        for (Quotation quotation : quotations) {
            System.out.println("Quotation from: " + quotation.company);
        }
    }














    /*
    @Test
    public void testGetQuotations_NoServicesRegistered() throws Exception {

        ClientInfo clientInfo = new ClientInfo("John", "Doe", 'M', 35, 3, 600);

        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Assert that the list of quotations is empty since no services are registered
        //assertTrue("Expected an empty list of quotations", quotations.isEmpty());
        //assertEquals("Expected the list size to be 0", 0, quotations.size());
        //assertNotNull(quotations);

        //assert quotations.size() > 0;

        assert quotations.size() > 0; // This asserts that the size is greater than 0

        // Optionally, you can also check that the company name is correct if needed
        Quotation quotation = quotations.get(0);
        //assertEquals("Expected the quotation from Auldfellas", Constants.AULD_FELLAS_SERVICE, quotation.company);
    }


    @Test
    public void generateQuotationTest() throws Exception {
        AFQService afqService = new AFQService(); // Create the service instance
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        QuotationService service = (QuotationService)
                registry.lookup(Constants.AULD_FELLAS_SERVICE);
        ClientInfo clientInfo = new ClientInfo("Akash", 'M', 23, 6.1, 70, false, false);
        Quotation q = service.generateQuotation(clientInfo);
        List<Quotation> quotations = List.of(q);
        assert quotations.size() > 0;
    }
*/







    /*
    @Test
    public void testGetQuotations_WithServiceRegistered() throws Exception {

        AFQService afqService = new AFQService(); // Create the service instance
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        // Create a sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("Jane", "Doe", ClientInfo.FEMALE, 28, 4, 800);

        // Call getQuotations on the broker service
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Assert that we got a quotation from Auldfellas
        assertNotNull("Expected a non-null list of quotations", quotations);
        assertEquals("Expected one quotation in the list", 1, quotations.size());

        // Clean up: Unbind the service after the test to avoid conflicts
        registry.unbind(Constants.AULD_FELLAS_SERVICE);
    }

    public void generateQuotationTest() throws Exception {
        // Lookup the service from the RMI registry
        QuotationService service = (QuotationService)
                registry.lookup(Constants.AULD_FELLAS_SERVICE);

        ClientInfo clientInfo = new ClientInfo("Akash", 'M', 23, 6.1, 70, false, false);

        Quotation q = service.generateQuotation(clientInfo);

        // Wrap the quotation in a list (as the broker would return a list)
        List<Quotation> quotations = List.of(q);

        // Assert that the list has at least one quotation
        assert quotations.size() > 0;
    }



*/

}


