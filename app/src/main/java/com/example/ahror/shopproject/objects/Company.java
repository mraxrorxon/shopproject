package com.example.ahror.shopproject.objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Ahror on 05.10.2017.
 */

@Entity
public class Company {
    @Id(autoincrement = true) private Long id;
    @Property private int product_count;
    @Property private int product_price;
    @Property private int product_sell;
    @Property private String company_name;
    @Property private String product_name;
    @Property private String companyIcon;
    @Generated(hash = 108313514)
    public Company(Long id, int product_count, int product_price, int product_sell,
            String company_name, String product_name, String companyIcon) {
        this.id = id;
        this.product_count = product_count;
        this.product_price = product_price;
        this.product_sell = product_sell;
        this.company_name = company_name;
        this.product_name = product_name;
        this.companyIcon = companyIcon;
    }
    @Generated(hash = 1096856789)
    public Company() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getProduct_count() {
        return this.product_count;
    }
    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }
    public int getProduct_price() {
        return this.product_price;
    }
    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }
    public int getProduct_sell() {
        return this.product_sell;
    }
    public void setProduct_sell(int product_sell) {
        this.product_sell = product_sell;
    }
    public String getCompany_name() {
        return this.company_name;
    }
    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
    public String getProduct_name() {
        return this.product_name;
    }
    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    public String getCompanyIcon() {
        return this.companyIcon;
    }
    public void setCompanyIcon(String companyIcon) {
        this.companyIcon = companyIcon;
    }
}
