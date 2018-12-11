package th.ac.kmitl.a58070067.mobilefinal;

import android.os.Bundle;
import android.os.Handler;
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

public class RegisterFragment extends Fragment {
    DatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getContext());
        initRegisterBtn(savedInstanceState);
    }
    private void initRegisterBtn(final Bundle savedInstanceState) {
        Button registerBtn = getView().findViewById(R.id.register_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.register_user_id);
                EditText nameText = getView().findViewById(R.id.register_name);
                EditText ageText = getView().findViewById(R.id.register_age);
                EditText passText = getView().findViewById(R.id.register_password);

                String username = userID.getText().toString();
                String name = nameText.getText().toString();
                int age = Integer.parseInt(ageText.getText().toString());
                String password = passText.getText().toString();

                if (username.isEmpty() || password.isEmpty() || name.isEmpty() || ageText.getText().toString().isEmpty()) {
                    Log.d("USER", "SOME FIELD  IS EMPTY");
                    Toast.makeText(getActivity(), "SOME FIELD  IS EMPTY", Toast.LENGTH_SHORT).show();

                } else {
                    Log.d("USER", "GO TO BMI");
                    if (validate(username, name,password,age)) {


                        //Check in the database is there any user associated with  this email
                        if (!db.isEmailExists(username)) {

                            //Email does not exist now add new user to database
                            db.addUser(new User(username, name, age, password));
                            Toast.makeText(getActivity(), "Account created successful", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.main_view,new LoginFragment())
                                    .commit();


                        } else {

                            //Email exists with email input provided so show error user already exist
                            Toast.makeText(getActivity(), "User aleready exit", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
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
                valid = false;
                Toast.makeText(getActivity(), "Please enter age between 10 and 80", Toast.LENGTH_SHORT).show();
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
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
