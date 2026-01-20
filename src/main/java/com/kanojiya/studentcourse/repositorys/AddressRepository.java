package com.kanojiya.studentcourse.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanojiya.studentcourse.models.AddressModel;

public interface AddressRepository extends JpaRepository<AddressModel, Long> {}
