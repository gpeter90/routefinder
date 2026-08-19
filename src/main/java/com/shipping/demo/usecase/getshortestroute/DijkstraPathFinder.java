package com.shipping.demo.usecase.getshortestroute;

import com.shipping.demo.domain.route.RouteDto;
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

    public DijkstraResult findShortestPath(List<RouteDto> routes, Long departureCityId, Long arrivalCityId) {
        Map<Long, List<Edge>> adjacencyList = buildAdjacencyList(routes);

        Map<Long, Integer> distances = new HashMap<>();
        Map<Long, Integer> costs = new HashMap<>();
        Map<Long, Long> previousCity = new HashMap<>();
        Set<Long> visited = new HashSet<>();

        distances.put(departureCityId, 0);
        costs.put(departureCityId, 0);

        PriorityQueue<NodeDistance> queue = new PriorityQueue<>(Comparator.comparingInt(NodeDistance::distance));
        queue.add(new NodeDistance(departureCityId, 0));

        while (!queue.isEmpty()) {
            NodeDistance current = queue.poll();
            Long currentCityId = current.cityId();

            if (visited.contains(currentCityId)) {
                continue;
            }
            visited.add(currentCityId);

            if (currentCityId.equals(arrivalCityId)) {
                return buildResult(previousCity, distances, costs, departureCityId, arrivalCityId);
            }

            List<Edge> neighbors = adjacencyList.getOrDefault(currentCityId, Collections.emptyList());
            for (Edge edge : neighbors) {
                if (visited.contains(edge.targetCityId())) {
                    continue;
                }

                int newDistance = distances.get(currentCityId) + edge.distanceKm();
                int currentBestDistance = distances.getOrDefault(edge.targetCityId(), Integer.MAX_VALUE);

                if (newDistance < currentBestDistance) {
                    distances.put(edge.targetCityId(), newDistance);
                    costs.put(edge.targetCityId(), costs.get(currentCityId) + edge.costEur());
                    previousCity.put(edge.targetCityId(), currentCityId);
                    queue.add(new NodeDistance(edge.targetCityId(), newDistance));
                }
            }
        }

        return null;
    }

    private Map<Long, List<Edge>> buildAdjacencyList(List<RouteDto> routes) {
        Map<Long, List<Edge>> adjacencyList = new HashMap<>();

        for (RouteDto route : routes) {
            adjacencyList.computeIfAbsent(route.getDepartureCityId(), cityId -> new ArrayList<>())
                    .add(new Edge(route.getArrivalCityId(), route.getDistanceKm(), route.getCostEur()));

            adjacencyList.computeIfAbsent(route.getArrivalCityId(), cityId -> new ArrayList<>())
                    .add(new Edge(route.getDepartureCityId(), route.getDistanceKm(), route.getCostEur()));
        }

        return adjacencyList;
    }

    private DijkstraResult buildResult(
            Map<Long, Long> previousCity,
            Map<Long, Integer> distances,
            Map<Long, Integer> costs,
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
                .totalCostEur(costs.get(arrivalCityId))
                .build();
    }

    private record NodeDistance(Long cityId, int distance) {
    }

    private record Edge(Long targetCityId, int distanceKm, int costEur) {
    }
}
