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
import android.widget.TextView;
import android.widget.Toast;

public class LoginFragment extends Fragment {
    DatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new DatabaseHelper(this.getContext());
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String user_id = sp.getString("username", "");
        if(!user_id.equals(""))
        {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_view,new HomeFragment())
                    .commit();
        }
        initLoginBtn(savedInstanceState);
        initRegisterBtn(savedInstanceState);
    }

    private void initLoginBtn(final Bundle savedInstanceState)
    {
        Button loginBtn = getView().findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userID = getView().findViewById(R.id.login_user_id);
                EditText passwordText = getView().findViewById(R.id.login_password);
                String username = userID.getText().toString();
                String password = passwordText.getText().toString();

                if(username.isEmpty()||password.isEmpty()){
                    Toast.makeText(getActivity(),
                            "Please field out this form",
                            Toast.LENGTH_SHORT).show();
                    Log.d("USER","USER OR PASSWORD IS EMPTY");

                }else{


                    User currentUser = db.Authenticate(new User(username, null, 0, password));
                    if (currentUser != null) {
                        Toast.makeText(getActivity(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();

                        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("username", username);
                        editor.putString("name",currentUser.getName());
                        editor.putInt("age",currentUser.getAge());
                        editor.putString("password",currentUser.getPassword());
                        editor.commit();

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_view,new HomeFragment())
                                .commit();
                    } else {

                        //User Logged in Failed
                        Toast.makeText(getActivity(), "Invalid user or password", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
    }

    private void initRegisterBtn(final Bundle savedInstanceState)
    {
        TextView registerBtn = getView().findViewById(R.id.login_register_btn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("USER","GO TO REGISTER");
                if(savedInstanceState == null){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.main_view,new RegisterFragment())
                            .commit();
                }

            }
        });
    }
}
