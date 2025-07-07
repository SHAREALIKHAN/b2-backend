package com.example.b2.leave.controller;

import com.example.b2.leave.entity.LeaveRequest;
import com.example.b2.leave.entity.User;
import com.example.b2.leave.repository.LeaveRepository;
import com.example.b2.leave.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leave")
@CrossOrigin(origins = "*")
public class LeaveController {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ APPLY LEAVE
    @PostMapping("/apply")
    public LeaveRequest applyLeave(@RequestBody LeaveRequest leaveRequest) {
        Long userId = leaveRequest.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        System.out.println("Applying leave for user ID: " + leaveRequest.getUser());
        System.out.println("User ID = " + (leaveRequest.getUser() != null ? leaveRequest.getUser().getId() : "NULL"));
        leaveRequest.setUser(user); // Ensure the full User entity is set
        leaveRequest.setStatus("PENDING");

        return leaveRepository.save(leaveRequest);
    }

    // ✅ GET LEAVES FOR A USER
    @GetMapping("/user/{userId}")
    public List<LeaveRequest> getLeavesByUser(@PathVariable Long userId) {
        return leaveRepository.findByUserId(userId);
    }

    // ✅ GET ALL LEAVES (FOR MANAGER)
    @GetMapping("/all")
    public List<LeaveRequest> getAllLeaves() {
        return leaveRepository.findAll();
    }

    // ✅ UPDATE LEAVE STATUS
    @PutMapping("/update")
    public LeaveRequest updateLeave(@RequestBody LeaveRequest leaveRequest) {
        System.out.println("Applying leave for user ID: " + leaveRequest.getUser().getId());

        LeaveRequest existing = leaveRepository.findById(leaveRequest.getId())
                .orElseThrow(() -> new RuntimeException("Leave not found"));

        existing.setStatus(leaveRequest.getStatus());
        return leaveRepository.save(existing);
    }
}