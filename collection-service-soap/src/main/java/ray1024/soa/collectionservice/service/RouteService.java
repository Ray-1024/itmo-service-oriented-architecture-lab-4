package ray1024.soa.collectionservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ray1024.soa.collectionservice.exception.InvalidInputException;
import ray1024.soa.collectionservice.exception.InvalidQueryParamException;
import ray1024.soa.collectionservice.exception.OtherErrorException;
import ray1024.soa.collectionservice.model.dto.ErrorDto;
import ray1024.soa.collectionservice.model.dto.GroupInfoDto;
import ray1024.soa.collectionservice.model.dto.RouteDto;
import ray1024.soa.collectionservice.repository.RouteRepository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RouteService {
    private final RouteRepository routeRepository;

    private void invalidInput(String paramName, String paramMessage) throws InvalidInputException {
        throw new InvalidInputException(paramName, paramMessage, "Wrong route param");
    }

    private void validateRoute(RouteDto routeDto, boolean isCreate) {
        if (routeDto == null) throw OtherErrorException.builder()
                .error(ErrorDto.builder().message("Route can't be null").time(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date())).build())
                .build();
        if (routeDto.getName() == null || routeDto.getName().isEmpty())
            invalidInput("name", "Route.name can't be empty or null");
        if (routeDto.getCoordinates() == null)
            invalidInput("coordinates", "Coordinates can't be null");
        if (routeDto.getCoordinates().getX() > 510)
            invalidInput("coordinates.x", "Coordinates.x can't be greater than 510");
        if (routeDto.getCoordinates().getY() == null)
            invalidInput("coordinates.y", "Coordinates.y can't be null");
        if (!isCreate) {
            if (routeDto.getId() == null || routeDto.getId() <= 0)
                invalidInput("creationDate", "Route.creationDate can't be null or less than 1");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            try {
                Date date = format.parse(routeDto.getCreationDate());
                routeDto.setCreationDate(format.format(date));
            } catch (Exception e) {
                invalidInput("creationDate", "Route.creationDate can't be null and should in format yyyy-MM-dd'T'HH:mm:ss'Z'");
            }
        }
        if (routeDto.getFrom() != null) {
            if (routeDto.getFrom().getName() == null)
                invalidInput("from.name", "From.name can't be null");
            if (routeDto.getFrom().getName().length() > 452)
                invalidInput("from.name", "From.name too long");
        }
        if (routeDto.getTo() == null)
            invalidInput("to", "Route.to can't be null");
        if (routeDto.getTo().getName() == null)
            invalidInput("to.name", "To.name can't be null");
        if (routeDto.getTo().getName().length() > 452)
            invalidInput("to.name", "To.name too long");
        if (routeDto.getDistance() <= 1)
            invalidInput("distance", "Route.distance can't be less than 1 or equal to 1");
    }

    private boolean isRouteField(String fieldName) {
        return Set.of(
                "id", "name", "coordinates.x", "coordinates.y", "creationDate",
                "from.x", "from.y", "from.z", "from.name",
                "to.x", "to.y", "to.z", "to.name",
                "distance"
        ).contains(fieldName);
    }

    private Class<?> getFieldClass(String fieldName) {
        return switch (fieldName) {
            case "id", "coordinates.x", "coordinates.y" -> Long.class;
            case "name", "from.name", "to.name" -> String.class;
            case "creationDate" -> Date.class;
            case "from.x", "from.y", "to.x", "to.y" -> Integer.class;
            case "from.z", "to.z", "distance" -> Double.class;
            default ->
                    throw new OtherErrorException(new ErrorDto("Wrong field name", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date())));
        };
    }

    private List<Map.Entry<String, Boolean>> parseSorting(String sorting) {
        if (Objects.isNull(sorting) || sorting.isEmpty()) return List.of();
        String[] sortingFields = sorting.split(";");

        if (!Arrays.stream(sortingFields).allMatch(sort ->
                isRouteField(sort) ||
                        (sort.charAt(0) == '<' && isRouteField(sort.substring(1)))
        ) || Arrays.stream(sortingFields).filter(str -> !str.isEmpty()).map(str -> str.charAt(0) == '<' ? str.substring(1) : str).distinct().count() != sortingFields.length) {
            throw new InvalidQueryParamException("sort",
                    "sort must be '' or list in format '{fieldName}' or '<{fieldName}'",
                    "Wrong query parameter"
            );
        }
        return Arrays.stream(sortingFields).map(str -> {
                    boolean asc = str.charAt(0) != '<';
                    return Map.entry(asc ? str : str.substring(1), asc);
                }
        ).toList();
    }

    private List<Map.Entry<String, Map.Entry<String, String>>> parseFiltering(String filtering) {
        if (filtering == null || filtering.isEmpty()) return List.of();
        String[] split = filtering.split(";");

        if (!Arrays.stream(split).allMatch(str -> {
                    String[] parts = str.split("=|!=|<|<=|>|>=");
                    if (parts.length != 2) return false;
                    if (!isRouteField(parts[0])) return false;
                    Class<?> clazz = getFieldClass(parts[0]);
                    try {
                        if (clazz == Integer.class) {
                            int val = Integer.parseInt(parts[1]);
                            return true;
                        } else if (clazz == Long.class) {
                            long val = Long.parseLong(parts[1]);
                            return true;
                        } else if (clazz == Double.class) {
                            double val = Double.parseDouble(parts[1]);
                            return true;
                        } else if (clazz == String.class) {
                            String val = parts[1];
                            return true;
                        } else if (clazz == Date.class) {
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            Date date = format.parse(parts[1]);
                            return true;
                        }
                    } catch (Exception ignored) {
                    }
                    return false;
                }
        )) {
            throw new InvalidQueryParamException("filter",
                    "filter must be {fieldName} =/!=/</>/<=/>= {value} in semicolon list",
                    "Wrong query parameter");
        }
        return Arrays.stream(split).map(str -> {
                    String[] parts = str.split("=|!=|<|<=|>|>=");
                    String value = parts[1];
                    return Map.entry(
                            parts[0],
                            Map.entry(str.substring(parts[0].length(), str.length() - parts[1].length()), value));
                }
        ).toList();
    }

    public List<RouteDto> getAllRoutes(Integer page, Integer size, String sort, String filter) {
        if (page == null || page < 1) invalidInput("page", "Page number must be greater than 0");
        if (size == null || size < 1 || size > 100) invalidInput("size", "Page size must be from range [1:100]");
        List<Map.Entry<String, Boolean>> sorting = parseSorting(sort);
        List<Map.Entry<String, Map.Entry<String, String>>> filtering = parseFiltering(filter);
        return routeRepository.getAll(
                page, size, sorting, filtering
        ).stream().map(RouteDto::fromEntity).collect(Collectors.toList());
    }

    public RouteDto getRoute(Long routeId) {
        if (routeId == null)
            throw new InvalidQueryParamException("routeId", "routeId is incorrect", "Incorrect route id");
        return RouteDto.fromEntity(routeRepository.getById(routeId));
    }

    public RouteDto createRoute(RouteDto route) {
        validateRoute(route, true);
        return RouteDto.fromEntity(routeRepository.create(route));
    }

    public RouteDto updateRoute(RouteDto route) {
        validateRoute(route, false);
        return RouteDto.fromEntity(routeRepository.update(route));
    }

    public void deleteRoute(Long routeId) {
        if (routeId == null)
            throw new InvalidQueryParamException("routeId", "routeId is incorrect", "Incorrect route id");
        routeRepository.deleteById(routeId);
    }

    public List<GroupInfoDto> getGroupsInfo() {
        return routeRepository.getGroupsInfo();
    }

    public long getEqualDistanceRoutesCount(Float distance) {
        if (distance == null)
            throw new InvalidQueryParamException("route.distance", "route.distance is incorrect", "Incorrect route distance");
        return routeRepository.getEqualDistanceRoutesCount((int) distance.floatValue());
    }
}
