package com.digirella.repository;

import com.digirella.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 */

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    /**
     *
     *
     */
    Optional<Currency> findByCodeAndDate(String code, String date);


    /**
     *
     *
     */
    List<Currency> findAllByDate(String date);

    /**
     *
     *
     */
    void deleteAllByDate(String date);

    /**
     *
     *
     */
    List<Currency> findAllByCode(String code);

}
