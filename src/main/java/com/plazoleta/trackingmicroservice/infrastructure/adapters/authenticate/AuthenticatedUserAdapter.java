package com.plazoleta.trackingmicroservice.infrastructure.adapters.authenticate;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.plazoleta.trackingmicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.trackingmicroservice.infrastructure.exceptions.ErrorDecodeAuthoritiesException;
import com.plazoleta.trackingmicroservice.infrastructure.exceptions.ErrorDecodeIdException;
import com.plazoleta.trackingmicroservice.infrastructure.security.jwt.DecodedJwtHolder;

@Service
public class AuthenticatedUserAdapter implements AuthenticatedUserPort {
    
    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DecodedJwtHolder decodedJwtHolder) {
            return decodedJwtHolder.getUserId();
        }
        throw new ErrorDecodeIdException("Could not obtain the authenticated user's ID.");
    }

    @Override
    public List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof DecodedJwtHolder decodedJwtHolder) {
            return decodedJwtHolder.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
        }
        throw new ErrorDecodeAuthoritiesException("Could not obtain the authenticated user's roles.");
    }
}
