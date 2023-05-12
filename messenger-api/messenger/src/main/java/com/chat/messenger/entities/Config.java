package com.chat.messenger.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "config")
public class Config {

    @Id
    @Column(name = "enable_client")
    private Boolean enableClient;
}