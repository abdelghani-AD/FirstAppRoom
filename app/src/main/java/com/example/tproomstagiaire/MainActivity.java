package com.example.tproomstagiaire;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.tproomstagiaire.BD.BaseDonees;
import com.example.tproomstagiaire.Entity.Stagiaire;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BaseDonees database;
    private EditText nomEditText, emailEditText, ageEditText;
        private Button insert, update, delete;
    private ListView listView;
    private ArrayAdapter<Stagiaire> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize views
        nomEditText = findViewById(R.id.nameEdit);
        emailEditText = findViewById(R.id.emailEdit);
        ageEditText = findViewById(R.id.ageEdit);
        insert = findViewById(R.id.inset);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        listView = findViewById(R.id.listView);

        // Initialize the Room Database instance
        database = BaseDonees.getInstance(this);

        // Initialize the adapter for the ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        // Load all Stagiaires initially
        new GetAllStagiaires().execute();
        // Set onClickListeners for buttons
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new InsertStagiaire().execute(createStagiaire());
                nomEditText.setText("");
                emailEditText.setText("");
                ageEditText.setText("");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateStagiaire().execute(createStagiaire());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteStagiaire().execute(createStagiaire());
            }
        });

    }

    private class GetAllStagiaires extends AsyncTask<Void,Void, List<Stagiaire>>{
        @Override
        protected List<Stagiaire> doInBackground(Void... voids) {
            return database.stagiaireDao().getAllStagiaires();
        }
        @Override
        protected void onPostExecute(List<Stagiaire> stagiaires) {
            super.onPostExecute(stagiaires);
            // Update the adapter with the new list of Stagiaires
            adapter.clear();
            adapter.addAll(stagiaires);
            adapter.notifyDataSetChanged();
        }
    }

    private Stagiaire createStagiaire(){
        Stagiaire stagiaire = new Stagiaire();
        stagiaire.setNom(nomEditText.getText().toString());
        stagiaire.setEmail(emailEditText.getText().toString());
        try {
            stagiaire.setAge(Integer.parseInt(ageEditText.getText().toString()));
        }catch (NumberFormatException e){
            e.getMessage();
        }
        return stagiaire;
    }

    private class InsertStagiaire extends AsyncTask<Stagiaire,Void,Void>{
        @Override
        protected Void doInBackground(Stagiaire... stagiaires) {
            database.stagiaireDao().insert(stagiaires[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            // Reload the list after insertion
            new GetAllStagiaires().execute();
        }
    }

    private class UpdateStagiaire extends AsyncTask<Stagiaire,Void,Void>{
        @Override
        protected Void doInBackground(Stagiaire... stagiaires) {
            database.stagiaireDao().update(stagiaires[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            // Reload the list after update
            new GetAllStagiaires().execute();
        }
    }

    private class DeleteStagiaire extends AsyncTask<Stagiaire ,Void,Void>{

        @Override
        protected Void doInBackground(Stagiaire... stagiaires) {
            database.stagiaireDao().delete(stagiaires[0]);
            return null;
        }
        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            new GetAllStagiaires().execute();
        }
    }

}