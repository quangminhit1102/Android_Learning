package com.itgenz.appcontentprovider.model;

public class Country {
    public int _id;
    public String code;
    public String name;
    public String continent;

    public Country() {
    }

    public Country(int _id, String code, String name, String continent) {
        this._id = _id;
        this.code = code;
        this.name = name;
        this.continent = continent;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}
