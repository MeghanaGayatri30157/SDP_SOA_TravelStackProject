package com.travel.booking.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class PackageClient {

    private final RestClient packageRestClient;

    public PackageClient(RestClient packageRestClient) {
        this.packageRestClient = packageRestClient;
    }

    public List<Long> getPackageIdsOwnedBy(String token) {

        List<Map<String, Object>> packages =
                packageRestClient.get()
                .uri("/packages/my-packages")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .body(new ParameterizedTypeReference<
                        List<Map<String, Object>>>() {});

        if (packages == null) {
            return List.of();
        }

        return packages.stream()
                .map(pkg -> ((Number) pkg.get("packageId")).longValue())
                .collect(Collectors.toList());
    }
}