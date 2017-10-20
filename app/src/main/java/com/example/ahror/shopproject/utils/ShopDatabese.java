package com.example.ahror.shopproject.utils;

import android.content.Context;

import com.example.ahror.shopproject.objects.Company;
import com.example.ahror.shopproject.objects.DaoMaster;
import com.example.ahror.shopproject.objects.DaoSession;
import com.example.ahror.shopproject.objects.Product;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahror on 05.10.2017.
 */

public class ShopDatabese {
    public static ShopDatabese instance;
    private DaoSession daoSession;

    private ShopDatabese(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "shop-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static ShopDatabese getInstance(Context context) {
        if (instance == null) instance = new ShopDatabese(context);
        return instance;
    }

    public void addProduct(List<Product> products) {
        daoSession.getProductDao().insertOrReplaceInTx(products);
    }

    public void addProduct(Product product) {
        daoSession.getProductDao().insertOrReplace(product);
    }

    public List<Product> getProducts() {
        return daoSession.getProductDao().loadAll();
    }

    public Product getProduct(Long id) {
        return daoSession.getProductDao().load(id);
    }

    public void addCompany(List<Company> companies) {
        daoSession.getCompanyDao().insertOrReplaceInTx(companies);
    }

    public boolean addCompany(Company company) {
        if (company.getId() == null)
            daoSession.getCompanyDao().insertOrReplaceInTx(company);
        else {
            for (Company c : daoSession.getCompanyDao().loadAll()) {
                if (c.getCompany_name().equalsIgnoreCase(company.getCompany_name())) return false;
            }
        }
        return true;
    }

    public Company getCompany(Long id) {
        return daoSession.getCompanyDao().load(id);
    }

    public void removeCompany (Company removedCompany) {
        daoSession.getCompanyDao().delete(removedCompany);
    }

    public List<Company> getCompanies(String searchCompanyName) {
        if (searchCompanyName == null || searchCompanyName.isEmpty())
            return daoSession.getCompanyDao().loadAll();
        List<Company> result = new ArrayList<>();
        for (Company company : daoSession.getCompanyDao().loadAll()) {
            if (company.getCompany_name().toLowerCase().contains(searchCompanyName.toLowerCase()))
                result.add(company);
        }
        return result;
    }

}