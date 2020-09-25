package dat3.jpademoflow2.entities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 *
 * @author thorc
 */
public class Tester {
    
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person p1 = new Person("Jørgen", 1995);
        Person p2 = new Person("Knud", 1985);
        
        Address a1 = new Address("Store Torv 1", 2000, "Frederiksberg");
        Address a2 = new Address("Lille Torv 1", 3200, "Valby");
        
        p1.setAddress(a1);
        p2.setAddress(a2);
        
        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);
        
        p1.addFees(f1);
        p1.addFees(f3);
        p2.addFees(f2);
        
        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast stroke");
        
        p1.addSwimStyle(s1);
        p1.addSwimStyle(s3);
        p2.addSwimStyle(s2);
        
        em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
            p1.removeSwimStyle(s3);
        em.getTransaction().commit();
        
        System.out.println("p1: " + p1.getP_id() + " navn: " + p1.getName());
        System.out.println("p2: " + p2.getP_id() + " navn: " + p2.getName());
        
        System.out.println("Jørgens gade: " + p1.getAddress().getStreet());
        
        System.out.println("Lad os se om to-vejs virker: " + a1.getPerson().getName());
        
        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());
        
        System.out.println("Alle betalinger: ");
        
        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();
       
        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + " : " + f.getAmount() + "kr. Dato: " + f.getPayDate() + " Adr: " + f.getPerson().getAddress().getCity()); 
        }               
    }
}
