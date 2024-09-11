package com.sakura.config;

import com.sakura.Entities.User;
import com.sakura.repository.UserRepository;
import com.sakura.springjwt.audit.Revision;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class CustomRevisionListener implements RevisionListener {
    @Autowired
    private UserRepository userRepository;
    
    public void newRevision(Object revisionEntity){
        final Revision revision = (Revision) revisionEntity;
          UserDetails userDetails = (UserDetails) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        String username = userDetails.getUsername();
        User user = this.userRepository.findByUsername(username).get();
    }
}
