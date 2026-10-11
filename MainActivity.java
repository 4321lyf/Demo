package com.example.marshmallowupi;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private final int blue = Color.rgb(23, 105, 224);
    private final ArrayList<String> transactions = new ArrayList<>();
    private LinearLayout content;
    private double demoBalance = 2450.00;
    private final NumberFormat money = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        transactions.add("Received from Priya  +₹850.00  •  Demo");
        transactions.add("Paid to Rahul  −₹120.00  •  Demo");
        transactions.add("Mobile recharge  −₹299.00  •  Demo");
        showHome();
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView v = new TextView(this);
        v.setText(value); v.setTextSize(size); v.setTextColor(color);
        if (bold) v.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        v.setPadding(0, 8, 0, 8);
        return v;
    }

    private Button button(String label, Runnable action) {
        Button b = new Button(this);
        b.setText(label);
        b.setAllCaps(false);
        b.setOnClickListener(v -> action.run());
        return b;
    }

    private void base(String title) {
        LinearLayout outer = new LinearLayout(this);
        outer.setOrientation(LinearLayout.VERTICAL);
        outer.setBackgroundColor(Color.rgb(245,247,251));
        TextView bar = text("  " + title, 22, Color.WHITE, true);
        bar.setBackgroundColor(blue);
        bar.setPadding(18, 18, 18, 18);
        outer.addView(bar);
        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(20, 16, 20, 24);
        scroll.addView(content);
        outer.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));
        setContentView(outer);
    }

    private void showHome() {
        base("My UPI • Demo");
        content.addView(text("Your demo balance", 16, Color.DKGRAY, false));
        content.addView(text(money.format(demoBalance), 30, blue, true));
        content.addView(text("Demo mode only — no money will move.", 14, Color.rgb(170,55,30), true));
        content.addView(button("Send money", this::showSend));
        content.addView(button("Request money (demo)", () -> message("Request created for demo purposes only.")));
        content.addView(button("Show UPI QR (sample)", this::showQr));
        content.addView(button("Transaction history", this::showHistory));
        content.addView(button("Real UPI integration status", this::showIntegrationInfo));
        content.addView(text("Sample activity", 20, Color.BLACK, true));
        for (int i=0; i<Math.min(2, transactions.size()); i++) content.addView(text(transactions.get(i), 15, Color.DKGRAY, false));
    }

    private void showSend() {
        base("Send money • Demo");
        content.addView(text("Recipient UPI ID", 16, Color.DKGRAY, true));
        EditText payee = new EditText(this);
        payee.setSingleLine(true); payee.setHint("name@bank");
        content.addView(payee);
        content.addView(text("Amount (INR)", 16, Color.DKGRAY, true));
        EditText amount = new EditText(this);
        amount.setSingleLine(true); amount.setHint("100.00");
        amount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        content.addView(amount);
        content.addView(button("Simulate payment", () -> {
            String id = payee.getText().toString().trim();
            String raw = amount.getText().toString().trim();
            if (!id.matches("[A-Za-z0-9.\\-_]{2,}@[A-Za-z0-9]{2,}") || raw.isEmpty()) {
                message("Enter a sample UPI ID (for example, demo@bank) and amount.");
                return;
            }
            double value;
            try { value = Double.parseDouble(raw); } catch (Exception e) { message("Enter a valid amount."); return; }
            if (value <= 0 || value > 100000) { message("For this demo, amount must be between ₹0.01 and ₹100,000."); return; }
            new AlertDialog.Builder(this).setTitle("Confirm demo payment")
                .setMessage("Simulate ₹" + String.format(Locale.US, "%.2f", value) + " to " + id + "?\n\nThis will NOT send real money.")
                .setNegativeButton("Cancel", (d,w) -> {})
                .setPositiveButton("Simulate", (d,w) -> {
                    transactions.add(0, "Paid to " + id + "  −" + money.format(value) + "  •  SIMULATED");
                    message("Demo result: SUCCESS (simulated). No real payment was made.");
                    showHome();
                }).show();
        }));
        content.addView(button("Back to home", this::showHome));
    }

    private void showQr() {
        base("Sample QR");
        content.addView(text("QR payments are not active in this demo.", 20, Color.BLACK, true));
        content.addView(text("This screen deliberately does not create a payable UPI QR code. Use a provider-issued test QR in an approved sandbox.", 16, Color.DKGRAY, false));
        content.addView(button("Back to home", this::showHome));
    }

    private void showHistory() {
        base("Transactions");
        for (String item : transactions) content.addView(text("•  " + item, 16, Color.DKGRAY, false));
        content.addView(button("Back to home", this::showHome));
    }

    private void showIntegrationInfo() {
        base("Real UPI integration");
        content.addView(text("Not connected", 24, Color.rgb(170,55,30), true));
        content.addView(text("Real payments require an authorized acquiring bank, PSP, or payment provider; onboarding and production approval; provider SDK/API credentials; server-side transaction verification; and reconciliation.", 16, Color.DKGRAY, false));
        content.addView(text("Never collect or store a UPI PIN in this app. Do not treat a client-side success screen or a return from another app as proof of payment. Verify final status through the provider's trusted server/API.", 16, Color.DKGRAY, false));
        content.addView(button("Back to home", this::showHome));
    }

    private void message(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).setPositiveButton("OK", null).show();
    }
}
