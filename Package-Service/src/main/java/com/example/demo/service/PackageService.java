package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Package;
import com.example.demo.repository.PackageRepository;

@Service
public class PackageService {

    private final PackageRepository repository;

    public PackageService(PackageRepository repository) {
        this.repository = repository;
    }

    // View all packages
    public List<Package> getAllPackages() {
        return repository.findAll();
    }

    // View package by ID
    public Optional<Package> getPackageById(Long id) {
        return repository.findById(id);
    }

    // Create package and assign owner
    public Package createPackage(
            Package travelPackage,
            String username) {

        travelPackage.setPackageId(null);
        travelPackage.setOwnerUsername(username);

        return repository.save(travelPackage);
    }

    // Update only the company's own package
    public Optional<Package> updatePackage(
            Long id,
            Package updatedPackage,
            String username) {

        return repository
                .findByPackageIdAndOwnerUsername(id, username)
                .map(existingPackage -> {

                    existingPackage.setDestination(
                            updatedPackage.getDestination()
                    );

                    existingPackage.setPrice(
                            updatedPackage.getPrice()
                    );

                    existingPackage.setAvailability(
                            updatedPackage.getAvailability()
                    );

                    return repository.save(existingPackage);
                });
    }

    // Delete only the company's own package
    public boolean deletePackage(Long id, String username) {

        Optional<Package> travelPackage =
                repository.findByPackageIdAndOwnerUsername(id, username);

        if (travelPackage.isEmpty()) {
            return false;
        }

        repository.delete(travelPackage.get());

        return true;
    }
}