package io.picarete.picarete.model.container.userdata;

import android.content.Context;

/**
 * Created by iem on 16/01/15.
 */
public class UserAccessor {
    private static User user;

    public static User getUser(Context context){
        if(user == null){
            user = new User(context).load(context);
            user.level = 30;
        }
        return user;
    }
}
