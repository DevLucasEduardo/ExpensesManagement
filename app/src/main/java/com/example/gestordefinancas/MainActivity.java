package com.example.gestordefinancas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextExpense;
    private EditText editTextValue;
    private EditText editTextDate;
    private Spinner spinnerPriority;
    private Button buttonAddExpense;
    private LinearLayout linearLayoutTasks;

    private List<String> expenses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextExpense = findViewById(R.id.editTextExpense);
        editTextValue = findViewById(R.id.editTextValue);
        editTextDate = findViewById(R.id.editTextDate);

        spinnerPriority = findViewById(R.id.spinnerPriority);
        buttonAddExpense = findViewById(R.id.buttonAddExpense);
        linearLayoutTasks = findViewById(R.id.linearLayoutTasks);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priorities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapter);

        spinnerPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String priority = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Priority selected: " + priority, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buttonAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expense = editTextExpense.getText().toString().trim();
                String value = editTextValue.getText().toString().trim();
                String date = editTextDate.getText().toString().trim();
                String priority = spinnerPriority.getSelectedItem().toString().trim();
                if (!expense.isEmpty() || value.isEmpty() || date.isEmpty()) {
                    expenses.add(expense);
                    addExpense(expense, value, date, priority);
                    editTextExpense.setText("");
                    editTextValue.setText("");
                    editTextDate.setText("");
                    showExpenseMessage(expense);
                } else {
                    Toast.makeText(getApplicationContext(), "Há campos vazios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addExpense(String expense, String value, String date, String priority) {
        TextView expenseView = new TextView(this);
        expenseView.setText(String.format("Despesa: %s | Valor: %s | Data: %s | Prioridade: %s", expense, value, date, priority));

        linearLayoutTasks.addView(expenseView);
    }

    private void showExpenseMessage(String task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Despesa adicionada: " + task)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }
}