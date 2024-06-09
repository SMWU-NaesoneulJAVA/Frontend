package com.example.nidonnaedon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.nisonnaeson.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AccountViewActivity extends AppCompatActivity {

    private ArrayList<String> accountList;
    private ArrayAdapter<String> adapter;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "account_data";
    private static final String KEY_ACCOUNTS = "accounts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarTitle = findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        if (itemName != null) {
            toolbarTitle.setText(itemName);
        }

        accountList = new ArrayList<>();
        ListView listViewAccounts = findViewById(R.id.listViewAccounts);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, accountList);
        listViewAccounts.setAdapter(adapter);

        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        loadAccounts();

        // 데이터 전달받기
        if (intent != null) {
            String amount = intent.getStringExtra("amount");
            String date = intent.getStringExtra("date");
            String usageDetails = intent.getStringExtra("usageDetails");
            String category = intent.getStringExtra("category");
            String currency = intent.getStringExtra("currency");

            if (amount != null && date != null && usageDetails != null && category != null && currency != null) {
                String displayText = usageDetails + " " + category + " " + date + " " + amount + " " + currency;
                accountList.add(displayText);
                adapter.notifyDataSetChanged();
                saveAccounts();
            }
        }

        listViewAccounts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mainPageIntent = new Intent(AccountViewActivity.this, MainActivity_page7.class);
                mainPageIntent.putExtra("itemName", accountList.get(position));
                startActivity(mainPageIntent);
            }
        });

        FloatingActionButton buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(AccountViewActivity.this, InputViewActivity.class);
                startActivity(addIntent);
            }
        });

        FloatingActionButton buttonSearch = findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(AccountViewActivity.this, ReportActivity.class);
                startActivity(searchIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_accountview, menu);

        // 메뉴 아이템 텍스트 스타일 변경
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            SpannableString s = new SpannableString(menuItem.getTitle());
            s.setSpan(new ForegroundColorSpan(Color.BLACK), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            s.setSpan(new TypefaceSpan("sans-serif"), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 기본 폰트 사용
            menuItem.setTitle(s);
        }

        return true;
    }

    private void loadAccounts() {
        Set<String> accountSet = sharedPreferences.getStringSet(KEY_ACCOUNTS, new HashSet<>());
        accountList.clear();
        accountList.addAll(accountSet);
        adapter.notifyDataSetChanged();
    }

    private void saveAccounts() {
        Set<String> accountSet = new HashSet<>(accountList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_ACCOUNTS, accountSet);
        editor.apply();
    }
}
