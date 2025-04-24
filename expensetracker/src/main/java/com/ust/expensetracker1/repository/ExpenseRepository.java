package com.ust.expensetracker1.repository;

import com.ust.expensetracker1.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Expense> findByDateBetweenAndType(LocalDate startDate, LocalDate endDate, Expense.ExpenseType type);
    List<Expense> findByDateBetweenAndCategory(LocalDate startDate, LocalDate endDate, String category);
    List<Expense> findByDateBetweenAndDescriptionContaining(LocalDate startDate, LocalDate endDate, String description);
    List<Expense> findByDateBetweenAndAmountBetween(LocalDate startDate, LocalDate endDate, BigDecimal minAmount, BigDecimal maxAmount);
}