package com.spiderdiary.DAO;

import com.spiderdiary.Entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RoleRepository {

    @Autowired
    private EntityManager entityManager;

    public RoleRepository(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    public Role findRoleByName(String theRoleName) {

        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try {
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }

        return theRole;
    }

    @Transactional
    public void deleteRoleById(Long roleId) {
        Role role = entityManager.find(Role.class, roleId);
        if (role == null) {
            throw new RuntimeException("No record found for given role id: " + roleId);
        }
        role.getUsers().clear();

        entityManager.remove(role);
    }
}
