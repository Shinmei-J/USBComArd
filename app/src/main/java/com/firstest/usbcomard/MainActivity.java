package com.firstest.usbcomard;

import android.hardware.usb.UsbDevice;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;import me.aflak.arduino.Arduino;
import me.aflak.arduino.ArduinoListener;
public class MainActivity extends AppCompatActivity implements ArduinoListener {    private Arduino arduino; // On initialise l'arduino
    private TextView displayTextView; // On initialise l'affichage de l'entrée du texte
    private EditText editText; // On initialise l'entrée du texte
    private Button sendBtn;@Override // On initialise le bouton
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // On défini quel Layout on va utiliser
        displayTextView = findViewById(R.id.diplayTextView); // On récupère l'id défini dans le Layout pour l'affichage de l'entrée du texte
        editText = findViewById(R.id.editText); // On récupère l'id défini dans le Layout pour l'entrée du texte
        sendBtn = findViewById(R.id.sendBtn);  // On récupère l'id défini dans le Layout pour l'entrée du bouton
        displayTextView.setMovementMethod(new    ScrollingMovementMethod());
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editTextString =   editText.getText().toString(); // On initialise le resultat de l'entrée du texte
                arduino.send(editTextString.getBytes()); // On envoie le texte en entrée à l'arduino
                editText.getText().clear(); // On clear l'array où il y avait le texte
            }
        });        arduino = new Arduino(this);
    }@Override
    protected void onStart() {  // Fonction pour démarer la connection
        super.onStart();
        arduino.setArduinoListener(this);
    }@Override
    protected void onDestroy() { // Fonction pour détruire la connection
        super.onDestroy();
        arduino.unsetArduinoListener();
        arduino.close();
    }@Override
    public void onArduinoAttached(UsbDevice device) { // Fonction indiquant que l'arduino est bien connectée
        display("arduino attached...");
        arduino.open(device);
    }@Override
    public void onArduinoDetached() { // Fonction indiquant que l'arduino est bien déconnectée
        display("arduino detached.");
    }@Override
    public void onArduinoMessage(byte[] bytes) {
        display(new String(bytes));
    } // Fonction affichant le message qui a été renvoyée par l'arduino en bytes
    @Override
    public void onArduinoOpened() { // Fonction indiquant que la connection à l'arduino est bien ouverte
        String str = "arduino opened...";
        arduino.send(str.getBytes());
    }@Override
    public void onUsbPermissionDenied() {   // Fonction indiquant que l'appareil à refusé l'arduino
        display("Permission denied. Attempting again in 3 sec...");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arduino.reopen();
            }
        }, 3000);
    }private void display(final String message){  // Fonction affichant le message
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayTextView.append(message);
            }
        });
    }
}