package business.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.controllers.TokenController;
import business.wrapper.TokenWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TOKENS)
public class TokenResource {

    private TokenController tokenController;

    @Autowired
    public void setTokenController(TokenController tokenController) {
        this.tokenController = tokenController;
    }

    @RequestMapping(method = RequestMethod.POST)
    public TokenWrapper login(@AuthenticationPrincipal User activeUser) {
        return new TokenWrapper(tokenController.login(activeUser.getUsername()));
    }

}
