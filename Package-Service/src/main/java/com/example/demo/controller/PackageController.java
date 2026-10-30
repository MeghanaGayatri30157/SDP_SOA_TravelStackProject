package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Package;
import com.example.demo.service.PackageService;

@RestController
@RequestMapping("/packages")
public class PackageController {

    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    // View all packages
    @GetMapping
    public ResponseEntity<List<Package>> getAllPackages() {

        return ResponseEntity.ok(
                packageService.getAllPackages()
        );
    }

    // View package by ID
    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(
            @PathVariable Long id) {

        return packageService.getPackageById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create package
    @PostMapping
    public ResponseEntity<Package> createPackage(
            @RequestBody Package travelPackage,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getSubject();

        Package savedPackage =
                packageService.createPackage(travelPackage, username);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPackage);
    }

    // Update own package
    @PutMapping("/{id}")
    public ResponseEntity<Package> updatePackage(
            @PathVariable Long id,
            @RequestBody Package updatedPackage,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getSubject();

        return packageService
                .updatePackage(id, updatedPackage, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete own package
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePackage(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getSubject();

        boolean deleted =
                packageService.deletePackage(id, username);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Package deleted successfully");
    }
    
    @GetMapping("/my-packages")
    public ResponseEntity<List<Package>> getMyPackages(
            @AuthenticationPrincipal Jwt jwt) {

        String username = jwt.getSubject();

        return ResponseEntity.ok(
                packageService.getPackagesByOwner(username)
        );
    }
}