package service.broker;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.List;

import service.core.*;

public class LocalBrokerService implements BrokerService {

	// List to hold manually added QuotationService instances (for local use)
	private List<QuotationService> quotationServices = new LinkedList<>();

	private final Registry registry;

	public LocalBrokerService(Registry registry) {
		this.registry = registry;
	}
	/**
	 * Method to manually add a QuotationService instance (e.g., AFQService, DGQService).
	 * This is useful when not using RMI but still want to test broker functionality.
	 */
/*
	public void addQuotationService(QuotationService service) {
		quotationServices.add(service);
	}
 */

	@Override
	public List<Quotation> getQuotations(ClientInfo info) throws RemoteException {
		List<Quotation> quotations = new LinkedList<>();

		try {
			// Attempt to use the RMI registry to lookup services
			Registry registry = LocateRegistry.getRegistry("localhost", 1099);

			// Lookup AuldFellas service
			try {
				QuotationService afqService = (QuotationService) registry.lookup(Constants.AULD_FELLAS_SERVICE);
				quotations.add(afqService.generateQuotation(info));
			} catch (NotBoundException e) {
				System.out.println(Constants.AULD_FELLAS_SERVICE + " Not Bound in RMI registry.");
			}

			// Lookup Dodgy Geezers service
			try {
				QuotationService dgqService = (QuotationService) registry.lookup(Constants.DODGY_GEEZERS_SERVICE);
				quotations.add(dgqService.generateQuotation(info));
			} catch (NotBoundException e) {
				System.out.println(Constants.DODGY_GEEZERS_SERVICE + " Not Bound in RMI registry.");
			}

			// Lookup Girls Allowed service
			try {
				QuotationService gaqService = (QuotationService) registry.lookup(Constants.GIRLS_ALLOWED_SERVICE);
				quotations.add(gaqService.generateQuotation(info));
			} catch (NotBoundException e) {
				System.out.println(Constants.GIRLS_ALLOWED_SERVICE + " Not Bound in RMI registry.");
			}

		} catch (RemoteException e) {
			// If RMI registry fails, fall back to local QuotationService instances (if available)
			System.out.println("RMI registry not available. Falling back to local quotation services.");
			for (QuotationService service : quotationServices) {
				quotations.add(service.generateQuotation(info));
			}
		}

		return quotations;
	}

	public void registerService(String name, Remote service) throws RemoteException {
		try {
			registry.bind(name, service);
			System.out.println("Inside Broker - Service " + name + " registered successfully!");
		} catch (Exception e) {
			throw new RemoteException("Failed to register service: " + e.getMessage(), e);
		}
	}
}























