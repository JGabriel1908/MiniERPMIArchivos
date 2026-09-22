package com.cunoc.minierp_backend.models.dto;

import com.cunoc.minierp_backend.models.Rol;
import com.cunoc.minierp_backend.models.User;

public class UserResponse {
    private Integer id;
    private String userName;
    private String name;
    private String lastName;
    private Rol rol;
    private Boolean activo;

    public UserResponse(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.lastName = user.getLastName();
        this.rol = user.getRol();
        this.activo = user.getActivo();
    }

    public Integer getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Rol getRol() {
        return rol;
    }

    public Boolean getActivo() {
        return activo;
    }
}
