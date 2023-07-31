package com.adeo.testjavabigqueryproject.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@AllArgsConstructor
@Data
public class Model {
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private Timestamp createdDate;
}
