package io.picarete.picarete.model.container.userdata;

import java.io.Serializable;
import java.util.List;

import io.picarete.picarete.model.container.userdata.Condition;
import io.picarete.picarete.model.container.userdata.User;

/**
 * Created by iem on 13/01/15.
 */
public class Title implements Serializable {
    public String title = "";
    public List<Condition> conditions = null;

    public Title(String title, List<Condition> conditions) {
        this.title = title;
        this.conditions = conditions;
    }

    public boolean isUnlocked(User user){
        boolean isUnlocked = true;

        if(conditions != null){
            for (Condition c : conditions){
                if (!c.isConditionValidated(user))
                    isUnlocked = false;
            }
        }

        return isUnlocked;
    }
}
