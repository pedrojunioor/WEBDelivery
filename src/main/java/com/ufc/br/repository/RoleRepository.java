package com.ufc.br.repository;

import com.ufc.br.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, String> {
    Role findByRolePessoa(String rolePessoa);
}
