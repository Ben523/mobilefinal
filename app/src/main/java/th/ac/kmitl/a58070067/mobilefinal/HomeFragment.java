package th.ac.kmitl.a58070067.mobilefinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setText();
        ProfileBtn();
    }

    private void setText()
    {
        SharedPreferences sp = getContext().getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        String total_text = "Hello "+name;
        TextView text1 = getView().findViewById(R.id.home_name);
        text1.setText(total_text);
        TextView text2 = getView().findViewById(R.id.home_profile);

    }

    private void ProfileBtn()
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
}
