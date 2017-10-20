package com.example.ahror.shopproject.objects;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Ahror on 05.10.2017.
 */

@Entity
public class Product {
    @Id(autoincrement = true) private Long id;
    @Property private int price;
    @Property private int sell;
    @Property private int count;
    @Property private String icon;
    @Property private String name;
    @Generated(hash = 45063820)
    public Product(Long id, int price, int sell, int count, String icon,
            String name) {
        this.id = id;
        this.price = price;
        this.sell = sell;
        this.count = count;
        this.icon = icon;
        this.name = name;
    }
    @Generated(hash = 1890278724)
    public Product() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getPrice() {
        return this.price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public int getSell() {
        return this.sell;
    }
    public void setSell(int sell) {
        this.sell = sell;
    }
    public int getCount() {
        return this.count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
