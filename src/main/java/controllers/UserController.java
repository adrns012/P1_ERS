package controllers;

import io.javalin.http.Context;
import models.*;
import services.UserService;

import java.util.List;


public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void login(Context context) {
        JsonResponse jsonResponse;

        User credentials = context.bodyAsClass(User.class);

        User userFromDb = userService.validateCredentials(credentials.getUsername(), credentials.getPassword());

        context.sessionAttribute("user", userFromDb);

        if (userFromDb == null)
            jsonResponse = new JsonResponse(false, "Invalid field for username or password", null);
        else
            jsonResponse = new JsonResponse(true, "Login was Successful", userFromDb);

        context.json(jsonResponse);
    }

    public void sessions(Context context) {
        User user = context.sessionAttribute("user");

        if (user == null) {
            context.json(new JsonResponse(false, "Not logged in", null));
        } else {
            context.json(new JsonResponse(true, user.getUsername() + " is logged in", user));
        }
    }

    public void logout(Context context) {
        context.sessionAttribute("user", null);
        context.json(new JsonResponse(true, "Logged out", null));
    }

    public void pastReimbs(Context context) {
        Integer userId = Integer.parseInt(context.queryParam("userId"));

        List<Reimbursement> reimbs = userService.viewPastReimbs(userId);

        for (Reimbursement reimb : reimbs) {
            String reimbStatus = reimb.getStatus();
            String reimbType = reimb.getType();

            ReimbStatus status = null;
            ReimbType type = null;

            for (ReimbStatus reimbStatus1 : ReimbStatus.values()) {
                if (reimbStatus1.getReimbStatus() == Integer.parseInt(reimbStatus))
                    status = reimbStatus1;
            }

            for (ReimbType reimbType1 : ReimbType.values()) {
                if (reimbType1.getReimbType() == Integer.parseInt(reimbType))
                    type = reimbType1;
            }

            reimb.setStatus(status.toString());
            reimb.setType(type.toString());
        }

        context.json(new JsonResponse(true, "Reimbursements for User: " + userId, reimbs));
    }

    public void allReimbs(Context context) {
        String username = context.pathParam("username");

        List<Reimbursement> reimbs = userService.viewAllReimbs(username);

        for (Reimbursement reimb : reimbs) {
            String reimbStatus = reimb.getStatus();
            String reimbType = reimb.getType();

            ReimbStatus status = null;
            ReimbType type = null;

            for (ReimbStatus reimbStatus1 : ReimbStatus.values()) {
                if (reimbStatus1.getReimbStatus() == Integer.parseInt(reimbStatus))
                    status = reimbStatus1;
            }

            for (ReimbType reimbType1 : ReimbType.values()) {
                if (reimbType1.getReimbType() == Integer.parseInt(reimbType))
                    type = reimbType1;
            }

            reimb.setStatus(status.toString());
            reimb.setType(type.toString());
        }

        context.json(new JsonResponse(true, "Viewing reimbursements", reimbs));
    }

    public void reimbsByStatus(Context context) {
        String username = context.pathParam("username");
        Integer statusId = Integer.parseInt(context.queryParam("statusId"));

        List<Reimbursement> reimbs = userService.filterReimbsByStatus(username, statusId);

        for (Reimbursement reimb : reimbs) {
            String reimbStatus = reimb.getStatus();
            String reimbType = reimb.getType();

            ReimbStatus status = null;
            ReimbType type = null;

            for (ReimbStatus reimbStatus1 : ReimbStatus.values()) {
                if (reimbStatus1.getReimbStatus() == Integer.parseInt(reimbStatus))
                    status = reimbStatus1;
            }

            for (ReimbType reimbType1 : ReimbType.values()) {
                if (reimbType1.getReimbType() == Integer.parseInt(reimbType))
                    type = reimbType1;
            }

            reimb.setStatus(status.toString());
            reimb.setType(type.toString());
        }

        context.json(new JsonResponse(true, "All reimbursements of Status " + statusId, reimbs));
    }

    public void approveReimb(Context context) {
        String username = context.pathParam("username");
        Integer reimbId = Integer.parseInt(context.queryParam("reimbId"));

        String result = userService.approveReimb(username, reimbId);

        if (result == null)
            context.json(new JsonResponse(false, "You are unable to approve your own requests...", null));
        else
            context.json(new JsonResponse(true, result, null));
    }

    public void denyReimb(Context context) {
        String username = context.pathParam("username");
        Integer reimbId = Integer.parseInt(context.queryParam("reimbId"));

        String result = userService.denyReimb(username, reimbId);

        if (result == null)
            context.json(new JsonResponse(false, "You are unable to deny your own requests...", null));
        else
            context.json(new JsonResponse(true, result, null));
    }

    public void newReimb(Context context) {
        String username = context.pathParam("username");
        Reimbursement reimb = context.bodyAsClass(Reimbursement.class);

        userService.addReimb(username, reimb);
        context.json(new JsonResponse(true, "Reimbursement request created", reimb));
    }
}