package de.benji.naturoforumsmp.Status.Util;

import java.util.HashMap;
import java.util.UUID;

public class Status {
    public String key;
    public String display;
    public String headURL;
    public boolean isBlacklist;
    public HashMap<UUID, Boolean> statusAllow;

    public Status(String key, String display, String headURL, boolean isBlacklist) {
        this.key = key;
        this.display = display;
        this.headURL = headURL;
        this.isBlacklist = isBlacklist;
        this.statusAllow = new HashMap<>();
    }

    public Status(String key, String display, String headURL, boolean isBlacklist, HashMap<UUID, Boolean> statusAllow) {
        this.key = key;
        this.display = display;
        this.headURL = headURL;
        this.isBlacklist = isBlacklist;
        this.statusAllow = statusAllow;
    }
}
