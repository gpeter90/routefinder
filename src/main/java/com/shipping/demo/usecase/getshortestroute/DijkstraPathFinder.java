package com.shipping.demo.usecase.getshortestroute;

import com.shipping.demo.domain.routes.RouteDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

@Component
public class DijkstraPathFinder {

    public DijkstraResult findLeastCostPath(List<RouteDto> routes, Long departureCityId, Long arrivalCityId) {
        Map<Long, List<Edge>> adjacencyList = buildAdjacencyList(routes);

        Map<Long, Integer> travelTimes = new HashMap<>();
        Map<Long, Integer> distances = new HashMap<>();
        Map<Long, Long> previousCity = new HashMap<>();
        Set<Long> visited = new HashSet<>();

        travelTimes.put(departureCityId, 0);
        distances.put(departureCityId, 0);

        PriorityQueue<NodeCost> queue = new PriorityQueue<>(Comparator.comparingInt(NodeCost::travelTimeMin));
        queue.add(new NodeCost(departureCityId, 0));

        while (!queue.isEmpty()) {
            NodeCost current = queue.poll();
            Long currentCityId = current.cityId();

            if (!visited.contains(currentCityId)) {
                visited.add(currentCityId);

                if (currentCityId.equals(arrivalCityId)) {
                    return buildResult(previousCity, distances, travelTimes, departureCityId, arrivalCityId);
                }

                List<Edge> neighbors = adjacencyList.getOrDefault(currentCityId, Collections.emptyList());
                for (Edge edge : neighbors) {
                    if (!visited.contains(edge.targetCityId())) {
                        int newTravelTime = travelTimes.get(currentCityId) + edge.travelTimeMin();
                        int currentBestTravelTime =
                                travelTimes.getOrDefault(edge.targetCityId(), Integer.MAX_VALUE);

                        if (newTravelTime < currentBestTravelTime) {
                            travelTimes.put(edge.targetCityId(), newTravelTime);
                            distances.put(edge.targetCityId(), distances.get(currentCityId) + edge.distanceKm());
                            previousCity.put(edge.targetCityId(), currentCityId);
                            queue.add(new NodeCost(edge.targetCityId(), newTravelTime));
                        }
                    }
                }
            }
        }

        return null;
    }

    private Map<Long, List<Edge>> buildAdjacencyList(List<RouteDto> routes) {
        Map<Long, List<Edge>> adjacencyList = new HashMap<>();

        for (RouteDto route : routes) {
            adjacencyList.computeIfAbsent(route.getDepartureCityId(), cityId -> new ArrayList<>())
                    .add(new Edge(route.getArrivalCityId(), route.getDistanceKm(), route.getTravelTimeMin()));

            adjacencyList.computeIfAbsent(route.getArrivalCityId(), cityId -> new ArrayList<>())
                    .add(new Edge(route.getDepartureCityId(), route.getDistanceKm(), route.getTravelTimeMin()));
        }

        return adjacencyList;
    }

    private DijkstraResult buildResult(
            Map<Long, Long> previousCity,
            Map<Long, Integer> distances,
            Map<Long, Integer> travelTimes,
            Long departureCityId,
            Long arrivalCityId
    ) {
        List<Long> path = new ArrayList<>();
        Long current = arrivalCityId;

        while (current != null) {
            path.add(current);
            if (current.equals(departureCityId)) {
                break;
            }
            current = previousCity.get(current);
        }

        Collections.reverse(path);

        return DijkstraResult.builder()
                .pathCityIds(path)
                .totalDistanceKm(distances.get(arrivalCityId))
                .totalTravelTimeMin(travelTimes.get(arrivalCityId))
                .build();
    }

    private record NodeCost(Long cityId, int travelTimeMin) {
    }

    private record Edge(Long targetCityId, int distanceKm, int travelTimeMin) {
    }
}
