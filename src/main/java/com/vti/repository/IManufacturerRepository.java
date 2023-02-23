package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.entity.Manufacturer;

public interface IManufacturerRepository extends JpaRepository<Manufacturer, Short> {

}
