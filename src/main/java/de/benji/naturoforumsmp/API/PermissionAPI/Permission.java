package de.benji.naturoforumsmp.API.PermissionAPI;

public enum Permission {
    StatusControl("Status_Control");

    public final String key;

    Permission(String key) {
        this.key = key;
    }

    public static Permission fromString(String s) {
        for(Permission p: Permission.values()) {
            if(p.key.equals(s)) {
                return p;
            }
        }
        return null;
    }
}
