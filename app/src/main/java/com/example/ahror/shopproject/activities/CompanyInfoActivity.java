package com.example.ahror.shopproject.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ahror.shopproject.objects.Company;
import com.example.ahror.shopproject.utils.ShopDatabese;

/**
 * Created by Ahror on 16.10.2017.
 */

public class CompanyInfoActivity extends AppCompatActivity {

    public static void start(Context context, Long companyId) {
        Intent intent = new Intent(context, CompanyInfoActivity.class);
        intent.putExtra("companyId", companyId);
        context.startActivity(intent);
    }

    private Long companyId;
    private Company company;
    private ShopDatabese shopDatabese;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopDatabese = ShopDatabese.getInstance(this);
        if (getIntent() != null)
            companyId = getIntent().getLongExtra("companyId", -1);
        if (companyId != null && getIntent().hasExtra("companyId")) {
            company = shopDatabese.getCompany(companyId);
        }

    }

}
