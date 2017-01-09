package localdbservice.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import localdbservice.rest.dao.LifeCoachDao;

@Entity
@Table(name = "LifeStyle")
@NamedQuery(name = "LifeStyle.findAll", query = "SELECT ls FROM LifeStyle ls")
public class LifeStyle implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idLifeStyle")
	private int idLifeStyle;
	
	@Column(name = "style")
	private String style;
	
	@Column(name = "description")
	private String description;
	
	public LifeStyle(){
	}
		
	public int getIdLifeStyle() {
		return this.idLifeStyle;
	}

	public void setIdLifeStyle(int idLifeStyle) {
		this.idLifeStyle = idLifeStyle;
	}
	
	public String getStyle() {
		return this.style;
	}

	public void setStyle(String style) {
		this.style = style;
	}
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	// Database operations
	public static LifeStyle getLifeStyleById(int lifeStyleId) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		LifeStyle ls = em.find(LifeStyle.class, lifeStyleId);
		LifeCoachDao.instance.closeConnections(em);
		return ls;
	}
	
	public static List<LifeStyle> getAll() {
		System.out.println("--> Initializing Entity manager...");
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		System.out.println("--> Querying the database for all the people...");
	    List<LifeStyle> list = em.createNamedQuery("LifeStyle.findAll", LifeStyle.class).getResultList();
		System.out.println("--> Closing connections of entity manager...");
	    LifeCoachDao.instance.closeConnections(em);
	    return list;
	}
	
	public static LifeStyle saveLifeStyle(LifeStyle ls) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(ls);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return ls;
	}
	
	public static LifeStyle updateLifeStyle(LifeStyle ls) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ls=em.merge(ls);
		tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	    return ls;
	}
	
	public static void removeLifeStyle(LifeStyle ls) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		ls=em.merge(ls);
	    em.remove(ls);
	    tx.commit();
	    LifeCoachDao.instance.closeConnections(em);
	}
}
