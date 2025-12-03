package web.Regional_Api.service;

import java.util.List;
import java.util.Optional;

import web.Regional_Api.entity.SuperAdmin;

public interface ISuperAdminService {

    List<SuperAdmin> getAllSuperAdmins();

    Optional<SuperAdmin> getSuperAdminById(Integer id);

    SuperAdmin createSuperAdmin(SuperAdmin superAdmin);

    Optional<SuperAdmin> updateSuperAdmin(Integer id, SuperAdmin superAdmin);

    void deleteSuperAdmin(Integer id);

    Optional<SuperAdmin> getSuperAdminByEmail(String email);

    Optional<SuperAdmin> getSuperAdminByToken(String token);
}
