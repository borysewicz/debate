package com.example.Debate.model;

import com.example.Debate.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.Id;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public abstract class Activity {
    @Id
    protected String _id;
    protected final Long creationDate;
    protected String author;
    protected String content;
    protected long lastEditTime;
    protected Map<Long, String> editHistory;
    protected Set<String> comments;


    protected Activity(){
        this.creationDate = System.currentTimeMillis();
        this.lastEditTime = this.creationDate;
        this.editHistory = new HashMap<>();
    }

    public Activity(long creationDate) {
        this.creationDate = creationDate;
    }

    public void saveEdit(){
        var currentTime = System.currentTimeMillis();
        this.putOldContent(currentTime);
        this.lastEditTime = currentTime;
    }

    protected abstract void putOldContent(long editTime);

    /**
     * Method checks whether the principal passed to the method is the activity's creator or the administrator
     * @param principal Principal object containing data about user performing the action
     * @return Boolean indicating whether the user can perform edition or deletion of the activity
     */
    public boolean isAuthorized(Principal principal){
        var userDetails = (UsernamePasswordAuthenticationToken) principal;
        return author.equals(userDetails.getName())
                || userDetails.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMINISTRATOR.toString()));
    }

}
