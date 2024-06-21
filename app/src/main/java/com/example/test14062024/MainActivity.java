package com.example.test14062024;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Boolean RootCheck = false;
    private Button One, Two, Three, Four, Five, Six, Seven, Eight, Nine, Zero;
    private Button Minus, Plus, Division, Multiply, Result, Root, Square, Percent;
    private TextView Formula, EndResult;
    private char Action;
    private double ResultValue = Double.NaN;
    private Boolean checkActionClick = false;
    private double Value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setupView();

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button button = (Button) view;
                Formula.setText(Formula.getText().toString() + button.getText().toString());
                checkActionClick = false;
            }
        };
        One.setOnClickListener(numberClickListener);
        Two.setOnClickListener(numberClickListener);
        Three.setOnClickListener(numberClickListener);
        Four.setOnClickListener(numberClickListener);
        Five.setOnClickListener(numberClickListener);
        Six.setOnClickListener(numberClickListener);
        Seven.setOnClickListener(numberClickListener);
        Eight.setOnClickListener(numberClickListener);
        Nine.setOnClickListener(numberClickListener);
        Zero.setOnClickListener(numberClickListener);

        View.OnClickListener actionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkActionClick) {
                    return;
                } else {
                    Button button = (Button) view;
                    if (Formula.getText().toString().isEmpty() && button.getText().charAt(0) != '√') {
                        return;
                    }
                    else {
                        calculate();
                        Action = button.getText().charAt(0);
                        Formula.setText(String.valueOf(ResultValue) + Action);
                    }
                    EndResult.setText(null);
                    checkActionClick = true;
                }
            }
        };
        Plus.setOnClickListener(actionClickListener);
        Minus.setOnClickListener(actionClickListener);
        Division.setOnClickListener(actionClickListener);
        Multiply.setOnClickListener(actionClickListener);
        Percent.setOnClickListener(actionClickListener);
        Square.setOnClickListener(actionClickListener);
        Root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Action = '√';
                Formula.setText("√");
                EndResult.setText(null);
                RootCheck = true;
            }
        });

        Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
                Action = '=';
                EndResult.setText(String.valueOf(ResultValue));
                Formula.setText(null);
                ResultValue = Double.NaN;
                checkActionClick = false;
                RootCheck = false;
            }
        });
    }

    private void setupView() {
        One = findViewById(R.id.One);
        Two = findViewById(R.id.Two);
        Three = findViewById(R.id.Three);
        Four = findViewById(R.id.Four);
        Five = findViewById(R.id.Five);
        Six = findViewById(R.id.Six);
        Seven = findViewById(R.id.Seven);
        Eight = findViewById(R.id.Eight);
        Nine = findViewById(R.id.Nine);
        Zero = findViewById(R.id.Zero);
        Minus = findViewById(R.id.Minus);
        Plus = findViewById(R.id.Plus);
        Result = findViewById(R.id.Result);
        Division = findViewById(R.id.Division);
        Multiply = findViewById(R.id.Multiply);
        EndResult = findViewById(R.id.EndResult);
        Formula = findViewById(R.id.Formula);
        Root = findViewById(R.id.Root);
        Percent = findViewById(R.id.Percent);
        Square = findViewById(R.id.Square);
    }

    private void calculate() {
        String textFormula = Formula.getText().toString();
        if (RootCheck) {
            String numberValue = textFormula.substring(1);
            if (!numberValue.isEmpty()) {
                Value = Double.parseDouble(numberValue);
                ResultValue = Math.sqrt(Value);
                RootCheck=false;
            }
            else{
                Formula.setText("Нет числа для решения корня!");
            }
        } else if (!Double.isNaN(ResultValue)) {
            int index = textFormula.indexOf(Action);
            if (index != -1) {
                String numberValue = textFormula.substring(index + 1);
                if (!numberValue.isEmpty()) {
                    Value = Double.parseDouble(numberValue);
                    switch (Action) {
                        case '/':
                            if (Value == 0) {
                                ResultValue = 0.0;
                            } else {
                                ResultValue /= Value;
                            }
                            break;
                        case '*':
                            ResultValue *= Value;
                            break;
                        case '+':
                            ResultValue += Value;
                            break;
                        case '-':
                            ResultValue -= Value;
                            break;
                        case '=':
                            ResultValue = Value;
                            break;
                        case '%':
                            ResultValue = (ResultValue/100)*Value;
                            break;
                        case '^':
                            ResultValue = Math.pow(ResultValue, Value);
                            break;
                    }
                }
            }
        } else {
            ResultValue = Double.parseDouble(Formula.getText().toString());
        }
        EndResult.setText(String.valueOf(ResultValue));
        Formula.setText("");
    }
}