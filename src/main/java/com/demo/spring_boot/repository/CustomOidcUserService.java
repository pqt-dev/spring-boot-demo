package com.demo.spring_boot.repository;

import com.demo.spring_boot.entity.author.Author;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends OidcUserService {

    private final AuthorRepository repository;


    public CustomOidcUserService(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String email = oidcUser.getEmail();
        String name = oidcUser.getFullName();
        String googleId = oidcUser.getSubject();
        Author author = repository.findByEmail(email)
                                  .orElse(new Author());
        author.setEmail(email);
        author.setName(name);
        author.setGoogleId(googleId);
        author.setJob("Guest");
        repository.save(author);
        return oidcUser;
    }


}

