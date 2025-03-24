package ray1024.soa.collectionservice.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ray1024.soa.collectionservice.exception.InternalServerException;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.model.dto.GroupInfoDto;
import ray1024.soa.collectionservice.model.dto.RouteDto;
import ray1024.soa.collectionservice.model.entity.RouteEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RouteRepository {
    private final EntityManager entityManager;

    @Transactional
    public List<RouteEntity> getAll(int page, int size, List<Map.Entry<String, Boolean>> sorting, List<Map.Entry<String, Map.Entry<String, String>>> filtering) {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<RouteEntity> criteriaQuery = criteriaBuilder.createQuery(RouteEntity.class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);
            criteriaQuery.select(root);

            if (!sorting.isEmpty()) {
                criteriaQuery.orderBy(sorting.stream().map(srt -> {
                            String[] fields = srt.getKey().split("\\.");
                            return srt.getValue() ?
                                    criteriaBuilder.asc(fields.length == 1 ? root.get(srt.getKey()) : root.get(fields[0]).get(fields[1])) :
                                    criteriaBuilder.desc(fields.length == 1 ? root.get(srt.getKey()) : root.get(fields[0]).get(fields[1]));
                        }
                ).toList());
            }
            if (!filtering.isEmpty()) {
                criteriaQuery.where(filtering.stream().map(fltr -> {
                            String[] fields = fltr.getKey().split("\\.");
                            String filterBy = fltr.getKey();
                            String filterValue = fltr.getValue().getValue();
                            Path<String> elem = fields.length == 1 ? root.get(filterBy) : root.get(fields[0]).get(fields[1]);
                            return switch (fltr.getValue().getKey()) {
                                case "=" -> criteriaBuilder.equal(elem, filterValue);
                                case "!=" -> criteriaBuilder.notEqual(elem, filterValue);
                                case "<=" -> criteriaBuilder.lessThanOrEqualTo(elem, filterValue);
                                case ">=" -> criteriaBuilder.greaterThanOrEqualTo(elem, filterValue);
                                case "<" -> criteriaBuilder.lessThan(elem, filterValue);
                                case ">" -> criteriaBuilder.greaterThan(elem, filterValue);
                                default -> null;
                            };
                        }).toArray(Predicate[]::new)
                );
            }

            TypedQuery<RouteEntity> query = entityManager.createQuery(criteriaQuery);

            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            return query.getResultList();
        } catch (Exception e) {
            throw new InternalServerException("Can't find routes with this paging, sorting and filtering parameters");
        }
    }

    @Transactional
    public RouteEntity getById(Long id) {
        try {
            RouteEntity entity = entityManager.find(RouteEntity.class, id);
            if (entity == null) {
                throw new InvalidQueryParamException("id", "Route with this id doesn't exists",
                        "Route with id=" + id + "doesn't exists");
            }
            return entity;
        } catch (InvalidQueryParamException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("Some problem with searching route by id");
        }
    }

    @Transactional
    public RouteEntity create(RouteDto route) {
        try {
            route.setId(null);
            route.setCreationDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
            RouteEntity entity = RouteEntity.fromDto(route);
            entity.getCoordinates().setId(null);
            entity.getTo().setId(null);
            if (entity.getFrom() != null) {
                entity.getFrom().setId(null);
                entityManager.persist(entity.getFrom());
            }
            entityManager.persist(entity.getCoordinates());
            entityManager.persist(entity.getTo());
            entityManager.persist(entity);
            return getById(entity.getId());
        } catch (Exception e) {
            throw new InternalServerException("Some problem with creating route");
        }
    }

    @Transactional
    public RouteEntity update(RouteDto route) {
        try {
            RouteEntity entity = RouteEntity.fromDto(route);
            getById(route.getId());
            entityManager.merge(entity);
            return entity;
        } catch (Exception e) {
            throw new InternalServerException("Some problem with updating route by id");
        }
    }

    @Transactional
    public void deleteById(long routeId) {
        try {
            entityManager.remove(getById(routeId));
        } catch (Exception e) {
            throw new InternalServerException("Some problem with deleting route by id");
        }
    }

    @Transactional
    public List<GroupInfoDto> getGroupsInfo() {
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);

            criteriaQuery.multiselect(
                    root.get("name"),
                    criteriaBuilder.count(root.get("name"))
            ).groupBy(root.get("name"));

            return entityManager.createQuery(criteriaQuery).getResultList().stream().map(objects ->
                    GroupInfoDto.builder()
                            .name((String) objects[0])
                            .count((int) (((Long) objects[1]).longValue()))
                            .build()
            ).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerException("Some problem with searching info about groups");
        }
    }

    @Transactional
    public long getEqualDistanceRoutesCount(int distance) {
        if (distance <= 1) throw new InvalidQueryParamException("distance",
                "Distance can't be less or equal to 1", "Invalid query param distance");
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<RouteEntity> root = criteriaQuery.from(RouteEntity.class);

            criteriaQuery.select(criteriaBuilder.count(root.get("distance")))
                    .where(criteriaBuilder.equal(root.get("distance"), distance));

            return entityManager.createQuery(criteriaQuery).getResultList().get(0);
        } catch (InvalidQueryParamException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("Some problem with searching routes count by distance");
        }
    }
}
