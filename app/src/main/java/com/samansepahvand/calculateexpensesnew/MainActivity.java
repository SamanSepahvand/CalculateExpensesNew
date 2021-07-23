package com.samansepahvand.calculateexpensesnew;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.samansepahvand.calculateexpensesnew.business.metamodel.OperationResult;
import com.samansepahvand.calculateexpensesnew.business.repository.InfoRepository;
import com.samansepahvand.calculateexpensesnew.db.Info;
import com.samansepahvand.calculateexpensesnew.helper.interfaces.ActionInfo;
import com.samansepahvand.calculateexpensesnew.ui.fragment.ListExpensesFragmentDirections;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements ActionInfo {


    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();


    }

    private void initView() {



    }

    @Override
    public void actionInfo(Info info) {
        if (info != null) {
            navController= Navigation.findNavController(this, R.id.fragment);
            ListExpensesFragmentDirections.ActionListExpensesFragmentToAddExpensesFragment action=
                    ListExpensesFragmentDirections.actionListExpensesFragmentToAddExpensesFragment();
            action.setInfo(info);
            navController.navigate(action);

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void actionDelete(Info info) {



    }
}