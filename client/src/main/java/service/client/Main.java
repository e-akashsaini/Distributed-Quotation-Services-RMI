package service.client;

import java.text.NumberFormat;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Quotation;
//import service.registry.ServiceRegistry;
import service.core.Constants;


import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    //private static BrokerService brokerService;

    public static void main(String[] args) {
        try {
            // Lookup the Broker Service from the RMI registry
            //BrokerService brokerService = ServiceRegistry.lookup(Constants.BROKER_SERVICE, BrokerService.class);



            Registry registry = null;
            if (args.length == 0) {
                registry = LocateRegistry.createRegistry(1099);
            } else {
                registry = LocateRegistry.getRegistry(args[0], 1099);
            }
            BrokerService brokerService = (BrokerService) registry.lookup(Constants.BROKER_SERVICE);

            String[] boundServices = registry.list();
            System.out.println("Services currently bound in the RMI registry:");
            for (String service : boundServices) {
                System.out.println("- " + service);
            }

            // Loop through test data (clients) and get quotations for each
            for (ClientInfo info : clients) {
                displayProfile(info);

                // Retrieve quotations from the broker and display them
                for (Quotation quotation : brokerService.getQuotations(info)) {
                    displayQuotation(quotation);
                }

                // Print a couple of lines between each client
                System.out.println("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Client encountered an error while communicating with the Broker.");
        }
    }

    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.name) +
                        " | Gender: " + String.format("%1$-27s", (info.gender==ClientInfo.MALE?"Male":"Female")) +
                        " | Age: " + String.format("%1$-30s", info.age)+" |");
        System.out.println(
                "| Weight/Height: " + String.format("%1$-20s", info.weight+"kg/"+info.height+"m") +
                        " | Smoker: " + String.format("%1$-27s", info.smoker?"YES":"NO") +
                        " | Medical Problems: " + String.format("%1$-17s", info.medicalIssues?"YES":"NO")+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                "| Company: " + String.format("%1$-26s", quotation.company) +
                        " | Reference: " + String.format("%1$-24s", quotation.reference) +
                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.price))+" |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
            new ClientInfo("Niki Collier", 'F', 49, 1.5494, 80, false, false),
            new ClientInfo("Old Geeza", 'M', 65, 1.6, 100, true, true),
            new ClientInfo("Hannah Montana", 'F', 21, 1.78, 65, false, false),
            new ClientInfo("Rem Collier", 'M', 49, 1.8, 120, false, true),
            new ClientInfo("Jim Quinn", 'M', 55, 1.9, 75, true, false),
            new ClientInfo("Donald Duck", 'M', 35, 0.45, 1.6, false, false)
    };
}




/*
package service.client;

import service.auldfellas.AFQService;
import service.broker.LocalBrokerService;
import service.core.BrokerService;
import service.core.ClientInfo;
import service.core.Constants;
import service.core.Quotation;
import service.dodgygeezers.DGQService;
import service.girlsallowed.GAQService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            // Step 1: Locate the RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);  // Assuming the broker is on localhost

            // Step 2: Lookup the broker service in the registry
            BrokerService brokerService = (BrokerService) registry.lookup("BrokerService");

            // Step 3: Input client details
            System.out.print("Enter client name: ");
            String name = scanner.nextLine();

            System.out.print("Enter gender (M/F): ");
            char gender = scanner.nextLine().charAt(0);

            System.out.print("Enter age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter height (in meters): ");
            double height = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter weight (in kg): ");
            double weight = Double.parseDouble(scanner.nextLine());

            System.out.print("Any smoking history? (true/false): ");
            boolean smoker = Boolean.parseBoolean(scanner.nextLine());

            System.out.print("Any previous claims? (true/false): ");
            boolean previousClaims = Boolean.parseBoolean(scanner.nextLine());

            // Step 4: Create a ClientInfo object
            ClientInfo clientInfo = new ClientInfo("Akash", "Doe", 'M', 23, 6.1, 70, false, false);

            // Step 5: Call getQuotations on the broker service
            List<Quotation> quotations = brokerService.getQuotations(clientInfo);

            // Step 6: Display the quotations
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
            System.out.println("Client encountered an error. Please check the service availability.");
        } finally {
            scanner.close();
        }
    }
}
*/


