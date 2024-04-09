/**
 * This class represents a contact in the company directory.
 * 
 * @author Yudith Mendoza & Shala Gutierrez
 * 
 */
class Contact {
	private int id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String email;
	private String position;
	
	/**
	 * Constructs a new Contact.
	 * 
	 * @param id			the unique id for the contact
	 * @param firstName		the first name of the contact
	 * @param lastName		the last name of the contact
	 * @param phoneNumber	the phone number of the contact
	 * @param email			the email address of the contact
	 * @param position		the position/title of the contact
	 */
	public Contact(int id, String firstName, String lastName, String phoneNumber, String email, String position) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.position = position;
	}


	/**
	 * Gets the id of the contact.
	 * 
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * Gets the first name of the contact.
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}


	/**
	 * Gets the last name of the contact.
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Gets the phone number of the contact.
	 * 
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}


	/**
	 * Gets the email of the contact.
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}


	/**
	 * Gets the position of the contact.
	 * 
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * Returns a string representation of the contact.
	 */
	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", position=" + position + "]";
	}

}