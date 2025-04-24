package com.ust.expensetracker1.controller;

import com.ust.expensetracker1.model.Expense;
import com.ust.expensetracker1.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/add")
    public String showAddExpenseForm(Model model) {
        model.addAttribute("expense", new Expense());
        return "add-expense";
    }

    @PostMapping("/add")
    public String addExpense(@ModelAttribute Expense expense) {
        expense.setDate(LocalDate.now());
        expenseService.addExpense(expense);
        return "redirect:/expenses/list";
    }

    @GetMapping("/list")
    public String listExpenses(Model model) {
        model.addAttribute("expenses", expenseService.getAllExpenses());
        return "list-expenses";
    }

    @GetMapping("/edit/{id}")
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        Expense expense = expenseService.getExpenseById(id);
        model.addAttribute("expense", expense);
        return "edit-expense";
    }

    @PostMapping("/edit/{id}")
    public String updateExpense(@PathVariable Long id, @ModelAttribute Expense expense) {
        expense.setId(id);
        expenseService.updateExpense(expense);
        return "redirect:/expenses/list";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return "redirect:/expenses/list";
    }

    @GetMapping("/reports")
    public String showReports(Model model) {
        model.addAttribute("dailyExpenses", expenseService.getDailyExpenses());
        model.addAttribute("monthlyExpenses", expenseService.getMonthlyExpenses());
        model.addAttribute("yearlyExpenses", expenseService.getYearlyExpenses());
        model.addAttribute("quarterlyExpenses", expenseService.getQuarterlyExpenses());
        model.addAttribute("monthlyAverage", expenseService.calculateMonthlyAverage());
        model.addAttribute("monthComparison", expenseService.compareMonths(LocalDate.now().getMonthValue(), LocalDate.now().minusMonths(1).getMonthValue()));
        return "reports";
    }
}