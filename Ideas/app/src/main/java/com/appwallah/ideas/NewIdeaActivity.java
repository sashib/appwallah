package com.appwallah.ideas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewIdeaActivity extends AppCompatActivity {

    NewIdeaController mNewIdeaController;

    Button mSaveButton;
    EditText mIdeaEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_idea);

        initializeController();
        initializeView();

    }


    public void initializeController() {
        this.mNewIdeaController = new NewIdeaController(this);
    }

    public void initializeView() {
        mIdeaEdit = (EditText) findViewById(R.id.idea_edit);
        mSaveButton = (Button) findViewById(R.id.save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveIdea();
            }
        });
    }

    public void saveIdea() {
        mNewIdeaController.saveIdea(mIdeaEdit.getText().toString());
    }

}
