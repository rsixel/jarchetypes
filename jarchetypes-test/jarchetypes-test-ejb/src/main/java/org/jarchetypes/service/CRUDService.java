package org.jarchetypes.service;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
@Named
public class CRUDService {

	@Inject
	private Logger log;

	@PersistenceContext
	private EntityManager em;

	public void save(Object bean) throws Exception {
		log.info("Saving " + bean);

		if (bean.getClass().getMethod("getId").invoke(bean) == null) {
			em.persist(bean);
		} else {
			em.merge(bean);
		}
	}
	
	public <T> T find(Class<T> c,Object id) throws Exception {
		return em.find(c, id);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> search(Class<T> entityClass, Map<String, Object> parameters) {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery query = builder.createQuery();

		Root<?> root = query.from(entityClass);
		query.select(root);

		Predicate predicate = null;

		for (String field : parameters.keySet()) {
			Predicate p = builder.equal(root.get(field), parameters.get(field));

			predicate = predicate == null ? p :

			builder.and(predicate, p);
		}

		query.where(predicate);

		return em.createQuery(query).getResultList();
	}
}
