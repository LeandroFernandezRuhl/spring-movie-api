package com.example.springmovieapi.service;

import com.example.springmovieapi.entities.Role;

public interface RoleService {
    Role findByName(String name);
}
