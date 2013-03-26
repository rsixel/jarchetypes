package org.jarchetypes.test.service;

import org.jarchetypes.test.model.Person;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

// The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class MemberRegistration {

   @Inject
   private Logger log;

   @Inject
   private EntityManager em;

   @Inject
   private Event<Person> memberEventSrc;

   public void register(Person member) throws Exception {
      log.info("Registering " + member.getName());
      em.persist(member);
      memberEventSrc.fire(member);
   }
}
