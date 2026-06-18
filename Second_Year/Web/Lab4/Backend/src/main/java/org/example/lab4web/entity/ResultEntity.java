package org.example.lab4web.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;


@Entity
@Table(name = "results")
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x", nullable = false)
    private BigDecimal x;

    @Column(name = "y", nullable = false)
    private BigDecimal y;

    @Column(name = "r", nullable = false)
    private BigDecimal r;

    @Column(name = "hit", nullable = false)
    private boolean hit;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public ResultEntity() {
    }

    public ResultEntity(BigDecimal x, BigDecimal y, BigDecimal r, boolean hit) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getX() {
        return x;
    }


    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
}