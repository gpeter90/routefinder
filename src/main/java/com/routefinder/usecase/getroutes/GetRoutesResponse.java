package com.routefinder.usecase.getroutes;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@Getter
@ToString
public class GetRoutesResponse {

    private List<RouteDetailDto> routes;
}
