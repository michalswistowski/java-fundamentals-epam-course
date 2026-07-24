package com.epam.training.person.domain;

public record Location(String iso3166, String country, String zip, String city) {

    public Location(String iso3166, String country, String zip, String city) {
        this.iso3166 = iso3166;
        this.country = country;
        this.zip = zip;
        this.city = city;
    }
}
