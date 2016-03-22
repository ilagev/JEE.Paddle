package data.entities;

public enum Role {
    ADMIN, PLAYER, TRAINER, AUTHENTICATED;

    public String roleName() {
        return "ROLE_" + this.toString();
    }

}
