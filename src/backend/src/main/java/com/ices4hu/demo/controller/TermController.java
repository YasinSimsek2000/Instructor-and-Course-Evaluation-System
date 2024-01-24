package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.Term;
import com.ices4hu.demo.model.TermDTO;
import com.ices4hu.demo.service.impl.TermServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/term")
@AllArgsConstructor

public class TermController {

    TermServiceImpl termService;

    @GetMapping("/has-term")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> hasTerm() {
        try {
            return ResponseEntity.ok(termService.hasTerm());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-current")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> getCurrentTerm() {
        try {
            return ResponseEntity.ok(termService.getCurrentTerm());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> addTerm(@RequestBody Term term) {
        try {
            termService.addTerm(term);
            return ResponseEntity.ok("Term with id: " + term.getId() + " successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Transactional
    public ResponseEntity<?> updateTerm(@RequestBody TermDTO term) {
        try {
            Term term1 = new Term(term);
            termService.updateTerm(term1);
            return ResponseEntity.ok("Updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteTerm(@PathVariable Long id) {
        try {
            termService.deleteById(id);
            return ResponseEntity.ok("Term with id: " + id + " deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
