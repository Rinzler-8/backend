package com.vti.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vti.entity.Manufacturer;
import com.vti.repository.IManufacturerRepository;

@Service
public class ManufacturerService implements IManufacturerService {
	@Autowired
	private IManufacturerRepository manufacturerRepository;

	@Override
	public List<Manufacturer> getAllManufacturers() {
		return manufacturerRepository.findAll();
	}
}
