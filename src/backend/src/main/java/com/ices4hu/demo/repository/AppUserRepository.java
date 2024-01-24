package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAppUsersByRole(Role role);

    @Query("SELECT u FROM app_user u WHERE u.secondEmail =:secondEmail AND (:secondEmail IS NOT NULL AND u.secondEmail IS NOT NULL)")
    Optional<AppUser> findBySecondEmailOrNull(@Param("secondEmail") String secondEmail);

    Optional<AppUser> findByDetail(String detail);
    @Modifying
    @Query("update app_user set " +
            "username =:username," +
            "password =:password," +
            "email =:email," +
            "secondEmail =:secondEmail," +
            "role =:role," +
            "detail =:detail," +
            "departmentId =:departmentId," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void updateAppUser(
            @Param("id") long id,
            @Param("username") String username,
            @Param("password") String password,
            @Param("email") String email,
            @Param("secondEmail") String secondEmail,
            @Param("role") Role role,
            @Param("departmentId") Long departmentId,
            @Param("detail") String detail,
            @Param("updatedAt") Date updatedAt
    );


    @Modifying
    @Query("update app_user set " +
            "email =:email," +
            "secondEmail =:secondEmail," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void selfUpdate(
            @Param("id") long id,
            @Param("email") String email,
            @Param("secondEmail") String secondEmail,
            @Param("updatedAt") Date updatedAt
    );

}
