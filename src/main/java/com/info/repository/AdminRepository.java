package com.info.repository;

<<<<<<< HEAD
import com.info.model.TeamLeader;
import com.info.model.TeamMember;
import com.info.service.TeamLeaderService;
import com.info.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Repository("adminepositoryR")
public interface AdminRepository  extends JpaRepository {

    @Query(value = "SELECT * FROM team_leader", nativeQuery = true)
    List<TeamLeader> getTeamLeaderList();

    List<TeamMember> getTeamMemberList();
=======
import com.info.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("adminRepository")
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findAdminByEmail(String email);
>>>>>>> 24239b859f093b49cd0699981575f20a57e9c49a
}