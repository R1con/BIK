package com.telegram.bot.bik.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
@Setter
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long telegramId;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "course")
    private String course;
}
