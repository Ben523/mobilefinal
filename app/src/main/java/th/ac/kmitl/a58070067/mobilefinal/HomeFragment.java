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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {
    private String username;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
        profileBtn();
        signOutBtn();
    }

    private void setText()
    {
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        username = sp.getString("username","");
        String total_text = "Hello "+name;
        TextView text1 = getView().findViewById(R.id.home_name);
        text1.setText(total_text);
        TextView text2 = getView().findViewById(R.id.home_profile);
        text2.setText(readFromFile(getContext()));

    }

    private void profileBtn()
    {
        Button back = getView().findViewById(R.id.home_profile_setup);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new ProfileSetupFragment())
                        .commit();
            }
        });
    }

    private void signOutBtn()
    {
        Button signout = getView().findViewById(R.id.home_sign_out);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE).edit().clear().commit();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_view,new LoginFragment())
                        .commit();
            }
        });


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
