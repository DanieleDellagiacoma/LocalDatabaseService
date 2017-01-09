package localdbservice.rest.model;

import localdbservice.rest.dao.LifeCoachDao;
import localdbservice.rest.model.LifeStatus;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name="Person")
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idPerson")
	private int idPerson;

	@Column(name="lastname")
	private String lastname;	
	
	@Column(name="firstname")
	private String firstname;

	@Column(name="birthdate")
	private String birthdate;
	
	@Column(name="email")
	private String email;
	
	@Column(name="genre")
	private String genre;
	
	@Column(name="idLifeStyle", insertable = false, updatable = false)
	private int idLifeStyle;
	
	@OneToMany(mappedBy="person",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<LifeStatus> lifestatus;
	
	@OneToOne
	@JoinColumn(name = "idLifeStyle", referencedColumnName = "idLifeStyle", insertable = true, updatable = true)
	private LifeStyle lifestyle;
	
	public Person() {
	}	

	public int getIdPerson() {
		return this.idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}
	public String getfirstname() {
		return this.firstname;
	}

	public void setfirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGenre() {
		return this.genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}
	
	public int getIdLifeStyle() {
		return this.idLifeStyle;
	}

	public void setIdLifeStyle(int idLifeStyle) {
		this.idLifeStyle = idLifeStyle;
	}
	
	public List<LifeStatus> getLifeStatus() {
	    return lifestatus;
	}

	public void setLifeStatus(List<LifeStatus> lifestatus) {
	    this.lifestatus= lifestatus;
	}
	
	public LifeStyle getLifeStyle() {
	    return lifestyle;
	}

	public void setLifeStyle(LifeStyle lifestyle) {
	    this.lifestyle= lifestyle;
	}
	
	// Database operations
	public static Person getPersonById(int personId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Person p = em.find(Person.class, personId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<Person> getAll() {
		System.out.println("--> Initializing Entity manager...");
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	    List<Person> list = em.createNamedQuery("Person.findAll", Person.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static Person savePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static Person updatePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removePerson(Person p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
