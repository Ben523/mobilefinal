package th.ac.kmitl.a58070067.mobilefinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class ProfileSetupFragment extends Fragment {
    private DatabaseHelper db;
    private String username;
    private User user;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_setup,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getActivity());
        setText();
        saveProfile();
    }

    private void setText()
    {
        EditText user_idTxt = getView().findViewById(R.id.profile_user_id);
        EditText nameTxt = getView().findViewById(R.id.profile_name);
        EditText ageTxt = getView().findViewById(R.id.profile_age);
        EditText passwordTxt = getView().findViewById(R.id.profile_password);
        EditText quoteText = getView().findViewById(R.id.profile_quote);
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        username = sp.getString("username", "");
        String name = sp.getString("name", "");
        int  age = sp.getInt("age", 10);
        String password = sp.getString("password","");
        user_idTxt.setText(username);
        nameTxt.setText(name);
        ageTxt.setText(Integer.toString(age));
        passwordTxt.setText(password);
        quoteText.setText(readFromFile(getContext()));
        user = new User(username,name,age,password);

    }

    private void saveProfile()
    {
        Button save = getView().findViewById(R.id.profile_save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.profile_user_id);
                EditText nameText = getView().findViewById(R.id.profile_name);
                EditText ageText = getView().findViewById(R.id.profile_age);
                EditText passText = getView().findViewById(R.id.profile_password);
                EditText quoteText = getView().findViewById(R.id.profile_quote);

                username = userID.getText().toString();
                String name = nameText.getText().toString();
                int age = Integer.parseInt(ageText.getText().toString());
                String password = passText.getText().toString();
                String quote = quoteText.getText().toString();

                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || ageText.getText().toString().isEmpty()) {
                    Log.d("USER", "SOME FIELD  IS EMPTY");
                    Toast.makeText(getActivity(), "SOME FIELD  IS EMPTY", Toast.LENGTH_SHORT).show();

                } else {
                    if (validate(username, name,password,age)) {
                        db.updateUser(user,user.getUser_id());
                        writeToFile(quote,getContext());
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view,new HomeFragment())
                                .commit();
                    }
                }
            }
        });

    }

    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(user.getUser_id()+".txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public boolean validate(String username,String name,String password,int age) {
        boolean valid = false;



        //Handling validation for UserName field
        if (username.isEmpty()) {
            valid = false;
            Toast.makeText(getActivity(), "Please enter valid username", Toast.LENGTH_SHORT).show();
        } else {
            if (username.length() > 5 && username.length() < 13) {
                valid = true;

            } else {
                valid = false;
                Toast.makeText(getActivity(), "username is too short", Toast.LENGTH_SHORT).show();
            }
        }

        if(!name.contains(" "))
        {
            Toast.makeText(getActivity(), "Name must have 1 space between firstname and lastname", Toast.LENGTH_SHORT).show();
            return false;
        }
        else
        {
            valid = true;
        }

        if (age == 0) {

            Toast.makeText(getActivity(), "Please enter valid age", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (age > 9 && age < 81) {
                valid = true;

            } else {

                Toast.makeText(getActivity(), "Please enter age between 10 and 80", Toast.LENGTH_SHORT).show();
                return false;
            }
        }



        //Handling validation for Password field
        if (password.isEmpty()) {

            Toast.makeText(getActivity(), "Please enter valid password", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (password.length() > 6) {
                valid = true;

            } else {

                Toast.makeText(getActivity(), "password is too short", Toast.LENGTH_SHORT).show();
                return false;
            }
        }


        return valid;
    }

    private String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(username+".txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("user", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("user", "Can not read file: " + e.toString());
        }

        return ret;
    }
}
