package service.auldfellas;

import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import service.auldfellas.AFQService;
import service.core.BrokerService;
import service.core.QuotationService;
import service.core.Constants;
public class Main {
    public static void main(String[] args) {
        QuotationService afqService = new AFQService();
        System.out.println(args[0]);
        try {
            // Connect to the RMI Registry - creating the registry will be the
            // responsibility of the broker.
            //Registry registry = LocateRegistry.createRegistry(1099);
            Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }

            // Create the Remote Object
            QuotationService quotationService = (QuotationService)
                    UnicastRemoteObject.exportObject(afqService,0);
            // Register the object with the RMI Registry
            //registry.bind(Constants.AULD_FELLAS_SERVICE, quotationService);


            BrokerService proxyRegistryService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);

            proxyRegistryService.registerService(Constants.AULD_FELLAS_SERVICE, quotationService);

            System.out.println("STOPPING SERVER SHUTDOWN");

            while (true) {Thread.sleep(1000); }
        } catch (Exception e) {
            System.out.println("Trouble: " + e);
        }
    }
}


