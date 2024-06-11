package com.PayFlex.PayFlex.API.Repository;

import com.PayFlex.PayFlex.API.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByAuthority(String authority);
}
