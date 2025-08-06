// Copyright 2024 The Casdoor Authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.casbin.casdoor;

import org.casbin.casdoor.entity.Transaction;
import org.casbin.casdoor.service.TransactionService;
import org.casbin.casdoor.support.TestDefaultConfig;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private final TransactionService transactionService = new TransactionService(TestDefaultConfig.InitConfig());

    @Test
    public void testTransaction() {
        String name = TestDefaultConfig.getRandomName("Transaction");

        // Add a new object
        Transaction transaction = new Transaction(
        "built-in",
        name,
        "display-name",
        "provider_pay_paypal",
        "category_donation",
        "type_one-time",
        "product_name",
        "Product Display Name",
        "This is a test transaction"
        );


        assertDoesNotThrow(() -> transactionService.addTransaction(transaction));

        // Get all objects, check if our added object is inside the list
        List<Transaction> transactions;
        try {
            transactions = transactionService.getTransactions();
        } catch (Exception e) {
            fail("Failed to get objects: " + e.getMessage());
            return;
        }

        boolean found = transactions.stream().anyMatch(item -> item.name.equals(name));
        assertTrue(found, "Added object not found in list");

        // Get the object
        Transaction retrievedTransaction;
        try {
            retrievedTransaction = transactionService.getTransaction(name);
        } catch (Exception e) {
            fail("Failed to get object: " + e.getMessage());
            return;
        }
        assertEquals(name, retrievedTransaction.name, "Retrieved object does not match added object");

        // Update the object
        String updatedDisplayName = "Updated Transaction";
        retrievedTransaction.displayName = updatedDisplayName;
        assertDoesNotThrow(() -> transactionService.updateTransaction(retrievedTransaction));

        // Validate the update
        Transaction updatedTransaction;
        try {
            updatedTransaction = transactionService.getTransaction(name);
        } catch (Exception e) {
            fail("Failed to get updated object: " + e.getMessage());
            return;
        }
        assertEquals(updatedDisplayName, updatedTransaction.displayName, "Failed to update object, displayName mismatch");

        // Delete the object
        assertDoesNotThrow(() -> transactionService.deleteTransaction(transaction));

        // Validate the deletion
        Transaction deletedTransaction;
        try {
            deletedTransaction = transactionService.getTransaction(name);
        } catch (Exception e) {
            fail("Failed to delete object: " + e.getMessage());
            return;
        }
        assertNull(deletedTransaction, "Failed to delete object, it's still retrievable");
    }
}
