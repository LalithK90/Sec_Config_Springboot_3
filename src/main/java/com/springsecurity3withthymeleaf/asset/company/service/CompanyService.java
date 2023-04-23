package com.springsecurity3withthymeleaf.asset.company.service;


import com.springsecurity3withthymeleaf.asset.company.entity.Company;

import java.util.List;

public interface CompanyService {

    List< Company > findAll();

    Company findById(Long id);

    Company persist(Company Company);

    boolean delete(Long id);

    List< Company > search(Company Company);

//    Integer findByCompanyIdByCompanyName(String CompanyName);
//
//    Company findByCompanyName(String name);


}
