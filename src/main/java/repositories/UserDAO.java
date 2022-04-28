package repositories;

import models.Reimbursement;
import models.User;

import java.util.List;

public interface UserDAO {

    User getUser(String username);

    User getUserById(Integer userId);

    List<Reimbursement> listPastReimb(Integer userId);

    List<Reimbursement> listAllReimb();

    List<Reimbursement> filterByStatus(Integer statusId);

    void approve(Integer reimbId, Integer userId);

    void deny(Integer reimbId, Integer userId);

    void addReimb(Integer userId, Reimbursement reimbursement);

    Reimbursement getReimb(Integer reimbId);
}