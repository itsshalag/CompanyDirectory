import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import edu.princeton.cs.algs4.Bag;

/**
 * This class represents a graph of contacts in a company directory. It uses
 * adjacency list to store direct contact relationships.
 * 
 * Each employee is a node and each direct contact is an edge.
 * 
 * @author Yudith Mendoza & Shala Gutierrez
 */
public class Graph {
	private Map<Integer, Contact> contactsMap; // Map to store contact by their ID
	private Map<Integer, Bag<Contact>> adj; // Adjacency list to store direct contacts
	private int edges; // number of edges or direct contacts in the graph

	/**
	 * Constructs the graph from files containing contact details and their direct
	 * connections.
	 * 
	 * @param contactFilename       Text file with employee details.
	 * @param directContactFilename Text file with the direct contact relationships.
	 */
	public Graph(String contactFilename, String directContactFilename) {
		contactsMap = new HashMap<>(); // Initialize contact map
		adj = new HashMap(); // initialize adjacency list
		edges = 0;

		try {
			BufferedReader br = new BufferedReader(new FileReader(contactFilename));
			br.readLine(); // Skip header

			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length < 6) {
					continue; // Skip if any information is missing
				}

				int id = Integer.parseInt(parts[0]);
				String firstName = parts[1];
				String lastName = parts[2];
				String phoneNumber = parts[3];
				String email = parts[4];
				String position = parts[5];

				Contact contact = new Contact(id, firstName, lastName, phoneNumber, email, position);
				contactsMap.put(id, contact); // Map each employee by their ID
				adj.put(id, new Bag<>()); // Initialize empty adjacency list (Bag) to add direct contacts
			}
			br.close(); // close file

			br = new BufferedReader(new FileReader(directContactFilename));
			while ((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length != 2) {
					continue;
				}

				int sourceId = Integer.parseInt(parts[0]);
				int targetId = Integer.parseInt(parts[1]);

				if (contactsMap.containsKey(sourceId) && contactsMap.containsKey(targetId)) {
					adj.get(sourceId).add(contactsMap.get(targetId)); // Add direct contact relationship
					adj.get(targetId).add(contactsMap.get(sourceId)); // Add reverse relationship for undirected graph
					edges++; // increment the total count of direct connections in the graph.
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the number of vertices in the graph
	 * 
	 * @return the number of employees in the directory
	 */
	public int vertices() {
		return contactsMap.size();
	}

	/**
	 * Returns the number of edges in the graph.
	 * 
	 * @return the number of direct connections
	 */
	public int edges() {
		return edges;
	}

	/**
	 * Returns an iterable collection of direct contacts for a given contact ID. If
	 * the contact ID is invalid or has no direct contacts, and empty iterable is
	 * returned.
	 * 
	 * @param id
	 * @return iterable Returns A list of contacts directly connected to the given
	 *         employee ID
	 */
	public Iterable<Contact> getDirectContacts(int id) {
		return adj.getOrDefault(id, new Bag<>());
	}

	/**
	 * This method prompts user to enter a contact ID and displays the direct
	 * contacts for that contact.
	 * 
	 * @param graph Containing contact info and direct contact relationships
	 */
	public static void testDirectContacts(Graph graph) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the ID of the person to see their direct contacts:");
		int employeeId = scanner.nextInt();

		// Check if the entered ID is valid
		if (employeeId >= 1 && employeeId <= graph.vertices()) {
			Iterable<Contact> directContacts = graph.getDirectContacts(employeeId);
			System.out.println("Direct contacts for employee " + employeeId + ": ");
			for (Contact contact : directContacts) {
				System.out.println(contact);
			}
		} else {
			System.out.println("Invalid empoyee ID. ");
		}
	}
	
	public Map<Integer, Contact> getAllContacts() {
		return contactsMap;
	}

}
