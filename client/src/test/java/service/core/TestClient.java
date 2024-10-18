package service.core;

import org.junit.BeforeClass;
import org.junit.Test;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import service.auldfellas.AFQService;

import service.broker.LocalBrokerService;

public class TestClient {

    private static BrokerService brokerService;
    private static Registry registry;

    @BeforeClass
    public static void setup() throws RemoteException {
        try {
            // Try to get the registry (assuming it's already running)
            registry = LocateRegistry.getRegistry("localhost", 1099);
            // Test if the registry is accessible by calling a dummy method (this won't bind anything)
            registry.list();  // This will throw an exception if the registry isn't running
        } catch (RemoteException e) {
            // If the registry is not running, create it programmatically
            System.out.println("RMI registry not found, starting a new one...");
            registry = LocateRegistry.createRegistry(1099);
        } //Setup RMI registry

        brokerService = new LocalBrokerService(registry); //Create the broker service

    }

/*
    @Test
    public void testCheckQuotationLocally() throws Exception {
        // sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("Akash", "Doe", 'M', 23, 6.1, 70, false, false);

        //Directly create an instance of a quotation service (AFQService)
        AFQService afqService = new AFQService();

        // Add the quotation from AuldFellas to the broker service manually (since we're bypassing the registry)
        ((LocalBrokerService) brokerService).addQuotationService(afqService);

        // Step 4: Call getQuotations on the broker service to retrieve a list of quotations
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);
        System.out.println("Number of quotations retrieved: " + quotations.size());
        System.out.println(quotations); // Print the list for further inspection

        // Step 5: Assert that we received at least one quotation
        assert quotations.size() > 0 : "Expected at least one quotation.";

        // Step 6: Print out the quotations
        System.out.println("Quotations for client: " + clientInfo.name);
        for (Quotation quotation : quotations) {
            System.out.println("---------------------------------");
            System.out.println("Company: " + quotation.company);
            System.out.println("Reference: " + quotation.reference);
            System.out.println("Price: $" + quotation.price);
            System.out.println("---------------------------------");
        }
    }

*/
    @Test
    public void testCheckQuotationWithRMI() throws Exception {
        // Step 1: Create and bind the AuldFellas quotation service to the RMI registry
        AFQService afqService = new AFQService();
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        // Step 2: Create a sample ClientInfo object
        ClientInfo clientInfo = new ClientInfo("Akash", 'M', 23, 6.1, 70, false, false);

        // Step 3: Call getQuotations on the broker service (through RMI) to retrieve a list of quotations
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Assert that we received at least one quotation
        assert quotations.size() > 0 : "Expected at least one quotation.";

        // Step 5: Print out the quotations
        System.out.println("Quotations for client: " + clientInfo.name);
        for (Quotation quotation : quotations) {
            System.out.println("---------------------------------");
            System.out.println("Company: " + quotation.company);
            System.out.println("Reference: " + quotation.reference);
            System.out.println("Price: $" + quotation.price);
            System.out.println("---------------------------------");
        }

        // Cleanup the service after testing (unbind from RMI registry)
        registry.unbind(Constants.AULD_FELLAS_SERVICE);
    }



}





    /*
    @Test
    public void testCheckQuotationInRegistry() throws Exception {

        AFQService afqService = new AFQService();
        registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);

        ClientInfo clientInfo = new ClientInfo("Akash", "Doe", 'M', 23, 6.1, 70, false, false);

        // Step 4: Call getQuotations on the broker service to retrieve a list of quotations
        List<Quotation> quotations = brokerService.getQuotations(clientInfo);

        // Step 5: Loop through the list and print out each quotation
        System.out.println("Quotations for client: " + clientInfo.name);
        for (Quotation quotation : quotations) {
            System.out.println("---------------------------------");
            System.out.println("Company: " + quotation.company);
            System.out.println("Reference: " + quotation.reference);
            System.out.println("Price: $" + quotation.price);
            System.out.println("---------------------------------");
        }

    }

}

/*
    public static void main(String[] args) {
        try {
            // Step 1: Locate the RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);  // Assuming the broker is on localhost

            // Step 2: Lookup the broker service in the registry
            BrokerService brokerService = (BrokerService) registry.lookup("BrokerService");  // Name in the registry

            // Step 3: Create a sample ClientInfo object (for which we will get quotations)
            ClientInfo clientInfo = new ClientInfo("Akash", "Doe", 'M', 23, 6.1, 70, false, false);

            // Step 4: Call getQuotations on the broker service to retrieve a list of quotations
            List<Quotation> quotations = brokerService.getQuotations(clientInfo);

            // Step 5: Loop through the list and print out each quotation
            System.out.println("Quotations for client: " + clientInfo.name);
            for (Quotation quotation : quotations) {
                System.out.println("---------------------------------");
                System.out.println("Company: " + quotation.company);
                System.out.println("Reference: " + quotation.reference);
                System.out.println("Price: $" + quotation.price);
                System.out.println("---------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/