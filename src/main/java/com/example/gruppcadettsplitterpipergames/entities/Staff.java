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

    public Staff(String firstName, String nickName, String lastName) {
        this.firstName = firstName;
        this.nickName = nickName;
        this.lastName = lastName;
    }

    public Staff() {}

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
}
