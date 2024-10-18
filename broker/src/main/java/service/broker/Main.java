package service.broker;

import service.core.BrokerService;
import service.core.Constants;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    public static void main(String[] args) {
        try {
            // Connect to the RMI registry on localhost and port 1099
            Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }

            // List all the services currently bound in the registry
            String[] boundServices = registry.list();
            System.out.println("Services currently bound in the RMI registry:");
            for (String service : boundServices) {
                System.out.println("- " + service);
            }

            // Bind the Broker service if it's not already bound
            try {
                registry.lookup(Constants.BROKER_SERVICE);
                System.out.println(Constants.BROKER_SERVICE + " is already bound in the RMI registry.");
            } catch (Exception e) {
                BrokerService brokerService = new LocalBrokerService(registry);
                BrokerService remoteBrokerService = (BrokerService) UnicastRemoteObject.exportObject(brokerService, 0);
                registry.bind(Constants.BROKER_SERVICE, remoteBrokerService);
                System.out.println(Constants.BROKER_SERVICE + " bound successfully in the RMI registry.");
            }




            System.out.println("STOPPING SERVER SHUTDOWN");
            while (true) {Thread.sleep(1000); }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


           /*
        try {
            // Create or get the RMI registry on port 1099
            Registry registry = LocateRegistry.getRegistry(1099);

            /*
            // Unbind services if they are already bound
            unbindServiceIfExists(registry, Constants.AULD_FELLAS_SERVICE);
            unbindServiceIfExists(registry, Constants.DODGY_GEEZERS_SERVICE);
            unbindServiceIfExists(registry, Constants.GIRLS_ALLOWED_SERVICE);
            unbindServiceIfExists(registry, Constants.BROKER_SERVICE);



            // Rebind services directly (to avoid AlreadyBoundException)

            // AuldFellas service
            QuotationService afqService = new AFQService();
            registry.rebind(Constants.AULD_FELLAS_SERVICE, afqService);
            System.out.println("Rebound " + Constants.AULD_FELLAS_SERVICE);

            // DodgyGeezers service
            QuotationService dgqService = new DGQService();
            registry.rebind(Constants.DODGY_GEEZERS_SERVICE, dgqService);
            System.out.println("Rebound " + Constants.DODGY_GEEZERS_SERVICE);

            // GirlsAllowed service
            QuotationService gaqService = new GAQService();
            registry.rebind(Constants.GIRLS_ALLOWED_SERVICE, gaqService);
            System.out.println("Rebound " + Constants.GIRLS_ALLOWED_SERVICE);

            // Broker service
            BrokerService brokerService = new LocalBrokerService();
            registry.rebind(Constants.BROKER_SERVICE, brokerService);
            System.out.println("Rebound " + Constants.BROKER_SERVICE);

            System.out.println("All services rebound and Broker ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to unbind a service if it's already bound
    private static void unbindServiceIfExists(Registry registry, String serviceName) {
        try {
            registry.unbind(serviceName);
            System.out.println("Unbound " + serviceName);
        } catch (NotBoundException e) {
            System.out.println(serviceName + " was not bound.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        */

