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
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStreamWriter;

public class ProfileSetupFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_setup,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
    }

    private void setText()
    {
        EditText user_idTxt = getView().findViewById(R.id.profile_user_id);
        EditText nameTxt = getView().findViewById(R.id.profile_name);
        EditText ageTxt = getView().findViewById(R.id.profile_age);
        EditText passwordTxt = getView().findViewById(R.id.profile_password);
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String user_id = sp.getString("username", "");
        String name = sp.getString("name", "");
        int  age = sp.getInt("age", 10);
        String password = sp.getString("password","");
        user_idTxt.setText(user_id);
        nameTxt.setText(name);
        ageTxt.setText(Integer.toString(age));
        passwordTxt.setText(password);
    }

    private void saveProfile()
    {

    }
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
