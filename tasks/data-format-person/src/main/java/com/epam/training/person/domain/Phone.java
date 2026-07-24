package com.epam.training.person.domain;

public record Phone(String area, String region, String local) {

    public Phone(String area, String region, String local) {
        this.area = area;
        this.region = region;
        this.local = local;
    }
}
