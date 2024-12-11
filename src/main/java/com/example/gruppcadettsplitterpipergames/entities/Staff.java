package com.example.gruppcadettsplitterpipergames.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "staff")

public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private int staffId;

    @Column(name = "staff_first_name", length = 20, nullable = false)
    private String firstName;

    @Column(name = "staff_last_name", length = 20, nullable = false)
    private String lastName;

    @Column(name = "staff_nick_name", length = 20, nullable = false)
    private String nickName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "staff_email", length = 50, nullable = false)
    private String email;

    //Constructors

    public Staff() {
    }

    public Staff(String firstName, String lastName, String nickName, Address address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nickName = nickName;
        this.address = address;
        this.email = email;
    }

//Getters & Setters


    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
