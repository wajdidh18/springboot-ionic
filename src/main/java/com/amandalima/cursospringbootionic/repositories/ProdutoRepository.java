package com.amandalima.cursospringbootionic.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.amandalima.cursospringbootionic.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
