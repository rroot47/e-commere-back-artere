package art.fr.repositosy;

import art.fr.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Set<Role> findRoleByRoleName(String roleName);
    @Query("select r from Role r where r.roleName= :roleName")
    Role getRole(@Param("roleName")String roleName);
}
