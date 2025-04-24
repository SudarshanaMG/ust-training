package com.ust.expensetracker1.service;

import com.ust.expensetracker1.model.Expense;
import com.ust.expensetracker1.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElse(null);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public List<Expense> getDailyExpenses() {
        LocalDate now = LocalDate.now();
        return expenseRepository.findByDateBetween(now, now);
    }

    public List<Expense> getMonthlyExpenses() {
        LocalDate startOfMonth = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfMonth = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return expenseRepository.findByDateBetween(startOfMonth, endOfMonth);
    }

    public List<Expense> getYearlyExpenses() {
        LocalDate startOfYear = LocalDate.now().with(TemporalAdjusters.firstDayOfYear());
        LocalDate endOfYear = LocalDate.now().with(TemporalAdjusters.lastDayOfYear());
        return expenseRepository.findByDateBetween(startOfYear, endOfYear);
    }

    public List<Expense> getQuarterlyExpenses() {
        LocalDate now = LocalDate.now();
        LocalDate startOfQuarter = now.with(now.getMonth().firstMonthOfQuarter()).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endOfQuarter = startOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
        return expenseRepository.findByDateBetween(startOfQuarter, endOfQuarter);
    }

    public BigDecimal calculateMonthlyAverage() {
        List<Expense> monthlyExpenses = getMonthlyExpenses();
        if (monthlyExpenses.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = monthlyExpenses.stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.divide(BigDecimal.valueOf(monthlyExpenses.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal compareMonths(int month1, int month2) {
        LocalDate startMonth1 = LocalDate.now().withMonth(month1).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endMonth1 = LocalDate.now().withMonth(month1).with(TemporalAdjusters.lastDayOfMonth());
        LocalDate startMonth2 = LocalDate.now().withMonth(month2).with(TemporalAdjusters.firstDayOfMonth());
        LocalDate endMonth2 = LocalDate.now().withMonth(month2).with(TemporalAdjusters.lastDayOfMonth());

        BigDecimal totalMonth1 = expenseRepository.findByDateBetween(startMonth1, endMonth1).stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalMonth2 = expenseRepository.findByDateBetween(startMonth2, endMonth2).stream().map(Expense::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalMonth1.subtract(totalMonth2);
    }
}