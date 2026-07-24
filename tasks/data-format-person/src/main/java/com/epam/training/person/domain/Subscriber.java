package com.epam.training.person.domain;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

import java.util.Objects;

@XmlType(propOrder = {"phone", "clientName"})
public class Subscriber {

    private String phone;
    private String clientName;

    public Subscriber() {
    }

    public Subscriber(String phone, String clientName) {
        this.phone = phone;
        this.clientName = clientName;
    }

    @XmlElement(name = "phone-number")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlElement(name = "client-name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return Objects.equals(phone, that.phone) && Objects.equals(clientName, that.clientName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone, clientName);
    }
}
