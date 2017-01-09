package localdbservice.rest.model;

import localdbservice.rest.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Query;

@Entity
@Table(name = "LifeStatus")
@NamedQuery(name = "LifeStatus.findAll", query = "SELECT h FROM LifeStatus h")
public class LifeStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idMeasure")
	private int idMeasure;
	
	@Column(name = "value")
	private String value;
	
	@Column(name = "idMeasureDef", insertable = false, updatable = false)
	private int idMeasureDef;
	
	@Column(name = "idPerson", insertable = false, updatable = false)
	private int idPerson;
	
	@OneToOne
	@JoinColumn(name = "idMeasureDef", referencedColumnName = "idMeasureDef", insertable = true, updatable = true)
	private MeasureDefinition measureDefinition;
	
	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson")
	private Person person;

	public LifeStatus() {
	}

	public int getIdMeasure() {
		return this.idMeasure;
	}

	public void setIdMeasure(int idMeasure) {
		this.idMeasure = idMeasure;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public MeasureDefinition getMeasureDefinition() {
		return measureDefinition;
	}

	public void setMeasureDefinition(MeasureDefinition param) {
		this.measureDefinition = param;
	}

	@XmlTransient
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	// Database operations
	public static LifeStatus getLifeStatusById(int healthprofileId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStatus p = em.find(LifeStatus.class, healthprofileId);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static List<LifeStatus> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
	    List<LifeStatus> list = em.createNamedQuery("HealthProfile.findAll", LifeStatus.class).getResultList();
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static LifeStatus saveLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static LifeStatus updateLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return p;
	}
	
	public static void removeLifeStatus(LifeStatus p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
	    p=em.merge(p);
	    em.remove(p);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
	

	
	public static LifeStatus getValueHealthProfile(Integer idPerson, Integer idMeasureDef){
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		
		Query query=em.createQuery("SELECT h FROM LifeStatus h WHERE h.idMeasureDef=:idMeasureDef AND h.idPerson=:idPerson", LifeStatus.class);
		query.setParameter("idMeasureDef", idMeasureDef);
		query.setParameter("idPerson", idPerson);

		LifeStatus result=(LifeStatus)query.getSingleResult();
		LifeCoachDao.instance.closeConnections(em);

		return result;
	}
}