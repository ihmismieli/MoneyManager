package fi.ahlgren.moneymanager.web;

//This class is used by spring security to authenticate and authorize user

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fi.ahlgren.moneymanager.domain.AppUser;
import fi.ahlgren.moneymanager.domain.AppUserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser curruser = appUserRepository.findByUsername(username);

        UserDetails appUser = new org.springframework.security.core.userdetails.User(username, curruser.getPasswordHash(), AuthorityUtils.createAuthorityList(curruser.getRole()));

        return appUser;
    }
}
