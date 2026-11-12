package com.shop.discount_calculator;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Online shopping discount calculator.
 * BigDecimal is used for money to avoid floating-point rounding errors.
 */
public class App {

    private static final BigDecimal HUNDRED = new BigDecimal("100");

    /** Discount amount = totalBill * discountPercent / 100 (rounded to 2 decimals). */
    public static BigDecimal calculateDiscountAmount(BigDecimal totalBill, BigDecimal discountPercent) {
        validate(totalBill, discountPercent);
        return totalBill
                .multiply(discountPercent)
                .divide(HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /** Final amount = totalBill - discountAmount (2 decimals). */
    public static BigDecimal calculateFinalAmount(BigDecimal totalBill, BigDecimal discountPercent) {
        BigDecimal discount = calculateDiscountAmount(totalBill, discountPercent);
        return totalBill.setScale(2, RoundingMode.HALF_UP).subtract(discount);
    }

    private static void validate(BigDecimal totalBill, BigDecimal discountPercent) {
        if (totalBill == null || discountPercent == null) {
            throw new IllegalArgumentException("Total bill and discount percentage must not be null");
        }
        if (totalBill.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Total bill cannot be negative");
        }
        if (discountPercent.compareTo(BigDecimal.ZERO) < 0
                || discountPercent.compareTo(HUNDRED) > 0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
    }

    public static void main(String[] args) {
        try {
            BigDecimal bill;
            BigDecimal percent;

            if (args.length == 2) {
                bill = new BigDecimal(args[0]);
                percent = new BigDecimal(args[1]);
            } else {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter total shopping bill: ");
                bill = new BigDecimal(scanner.nextLine().trim());
                System.out.print("Enter discount percentage: ");
                percent = new BigDecimal(scanner.nextLine().trim());
            }

            BigDecimal discount = calculateDiscountAmount(bill, percent);
            BigDecimal finalAmount = calculateFinalAmount(bill, percent);

            System.out.println("-----------------------------------");
            System.out.println("Total Bill      : " + bill);
            System.out.println("Discount (" + percent + "%) : " + discount);
            System.out.println("Final Amount    : " + finalAmount);
            System.out.println("-----------------------------------");
        } catch (NumberFormatException e) {
            System.err.println("Invalid number entered. Please enter numeric values.");
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (NoSuchElementException e) {
            System.err.println("No input provided. Usage: java -jar discount-calculator.jar <bill> <discountPercent>");
            System.exit(1);
        }
    }
}
