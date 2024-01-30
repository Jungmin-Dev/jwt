package com.example.redis_jwt.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "USER")
public class User {
  @Id
  @Column(name = "UID")
  private long uid;

  @Column(name = "USER_ID")
  private String userId;

  @Column(name = "USER_PWD")
  private String userPwd;
}
