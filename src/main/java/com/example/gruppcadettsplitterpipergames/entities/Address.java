package com.example.gruppcadettsplitterpipergames.entities;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "addresses")
public class Address {          //Lynsey Fox
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @Column(name = "address", length = 50, nullable = false)
    private String address;

    @Column(name = "district", length = 50, nullable = true)
    private String district;

    @Column(name = "city", length = 30, nullable = false)
    private String city;

    @Column(name = "postcode", length = 10, nullable = false)
    private String postcode;

    @Column (name = "country", length = 20, nullable = false)
    private String country;

    @OneToMany (mappedBy= "address", orphanRemoval = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Player> players = new ArrayList<>();

    @OneToMany (mappedBy = "address", orphanRemoval = false, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Staff> staff = new ArrayList<>();

    public Address(String address, String district, String city, String postcode, String country) {
        this.address = address;
        this.district = district;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
    }
    public Address() {}

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public void setStaff(List<Staff> staff) {
        this.staff = staff;
    }
}
