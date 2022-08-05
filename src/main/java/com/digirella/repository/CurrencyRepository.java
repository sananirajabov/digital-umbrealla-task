package com.digirella.repository;

import com.digirella.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Defines the persistence operations on Currency entity
 */
@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    /**
     * Find currency by code and date from the underlying storage.
     *
     * @param code the currency code (ex. AZN)
     * @param date the currency date
     * @return found currency
     */
    Optional<Currency> findByCodeAndDate(String code, String date);


    /**
     * Find list of currencies by date from the underlying storage.
     *
     * @param date the currency date
     * @return list of currencies
     */
    List<Currency> findAllByDate(String date);

    /**
     * Delete currencies by date from the underlying storage.
     *
     * @param date to be deleted currency date
     */
    void deleteAllByDate(String date);

    /**
     * Find all currencies by currency code (ex. AZN) from the underlying storage.
     *
     * @param code the currency code (ex. AZN)
     * @return list of currencies
     */
    List<Currency> findAllByCode(String code);

}
