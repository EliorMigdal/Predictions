package engine.manager;

import engine.manager.exception.AdminAlreadyLogged;
import engine.manager.exception.UserAlreadyExists;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class UserManager {
    private Boolean adminLogged = false;
    private String adminName;
    private final Map<String, Boolean> usersStatus = new LinkedHashMap<>();

    synchronized public void addNewClient(String clientName)
            throws UserAlreadyExists {
        if ((usersStatus.get(clientName) != null && usersStatus.get(clientName))
                || (clientName.equals(adminName))) {
            throw new UserAlreadyExists(clientName);
        } else {
            usersStatus.put(clientName, true);
        }
    }

    synchronized public void addAdmin(String adminName)
            throws AdminAlreadyLogged {
        if (adminLogged) {
            throw new AdminAlreadyLogged();
        } else {
            adminLogged = true;
            this.adminName = adminName;
        }
    }

    public Collection<String> getClientsNames() {
        return this.usersStatus.keySet();
    }

    public void removeClient(String clientName) {
        usersStatus.put(clientName, false);
    }

    public void removeAdmin() {
        adminLogged = false;
        adminName = null;
    }
}
