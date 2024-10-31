
package com.example.loancalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoanCalculatorApp()
        }
    }
}

@Composable
fun LoanCalculatorApp() {
    var principal by remember { mutableStateOf("") }
    var interestRate by remember { mutableStateOf("") }
    var loanTerm by remember { mutableStateOf("") }
    var monthlyPayment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = principal,
            onValueChange = { principal = it },
            label = { Text("Principal Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = interestRate,
            onValueChange = { interestRate = it },
            label = { Text("Annual Interest Rate (%)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = loanTerm,
            onValueChange = { loanTerm = it },
            label = { Text("Loan Term (Years)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                monthlyPayment = calculateMonthlyPayment(principal, interestRate, loanTerm)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Monthly Payment")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Monthly Payment: $monthlyPayment", style = MaterialTheme.typography.h6)
    }
}

fun calculateMonthlyPayment(principal: String, interestRate: String, loanTerm: String): String {
    val p = principal.toDoubleOrNull() ?: return "Invalid input"
    val r = (interestRate.toDoubleOrNull() ?: return "Invalid input") / 100 / 12
    val n = (loanTerm.toDoubleOrNull() ?: return "Invalid input") * 12
    val monthlyPayment = if (r == 0.0) {
        p / n
    } else {
        p * r * (1 + r).pow(n) / ((1 + r).pow(n) - 1)
    }
    return "%.2f".format(monthlyPayment)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoanCalculatorApp()
}
    