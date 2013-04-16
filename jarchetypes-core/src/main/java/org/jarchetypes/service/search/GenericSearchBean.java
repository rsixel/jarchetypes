package org.jarchetypes.service.search;

import java.util.List;
import java.util.Map;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Named
public class GenericSearchBean<T> {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> search(Class<T> entityClass, Map<String, Object> parameters) {
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
