package com.springsecurity3withthymeleaf.asset.company.service.impl;


import com.springsecurity3withthymeleaf.asset.company.dao.CompanyDao;
import com.springsecurity3withthymeleaf.asset.company.entity.Company;
import com.springsecurity3withthymeleaf.asset.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"company"})
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyDao companyDao;

    @Cacheable
    public List< Company > findAll() {
        return companyDao.findAll();
    }

    @Cacheable
    public Company findById(Long id) {
        return companyDao.getReferenceById(id);
    }

    @Caching(evict = {@CacheEvict(value = "company", allEntries = true)},
            put = {@CachePut(value = "company", key = "#company.id")})
    @Transactional
    public Company persist(Company company) {

        return companyDao.save(company);
    }

    @Cacheable
    public boolean delete(Long id) {
    Company company = companyDao.getReferenceById(id);
    companyDao.save(company);
        return false;
    }

    @Cacheable
    public List< Company > search(Company company) {
        ExampleMatcher matcher =
                ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< Company > companyExample = Example.of(company, matcher);
        return companyDao.findAll(companyExample);
    }

//    @Cacheable
//    public Integer findByCompanyIdByCompanyName(String companyName) {
//        return companyDao.findByCompanyname(companyName);
//    }

//    @Cacheable
//    @Transactional(readOnly = true)
//    public Company findByCompanyName(String name) {
//        return companyDao.findByCompanyname(name);
//    }

//    @Cacheable
//    public Company findCompanyByEmployee(CompanyDetails companyDetails) {
//        return companyDao.findByCompanyDetails(companyDetails);
//    }
//
//    @Cacheable
//    public boolean findByEmployee(CompanyDetails companyDetails) {
//        return companyDao.findByCompanyDetails(companyDetails) == null;
//    }


}