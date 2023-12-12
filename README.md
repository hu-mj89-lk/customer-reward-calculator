# Customer Reward Calculator

## Problem

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.
A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent
over $50 in each transaction (e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).
Given a record of every transaction during a three-month period, calculate the reward points earned for each customer
per month and total.

## Environment Requirements

* JDK 17
* Gradle 8.5

## Run Instructions

* For IntelliJ Idea IDE, `.run` directory has run configurations which will auto-populate.

* For others, run `gradle bootRun` in project directory.

## Usage Instructions

* `.postman` directory has a postman collection for CRUD operations (except DELETE) for customer & transactions and
  customer reward points.

## API Documentation

* `.documentation` directory contains API documentation.