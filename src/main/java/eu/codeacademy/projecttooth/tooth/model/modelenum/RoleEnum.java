package eu.codeacademy.projecttooth.tooth.model.modelenum;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_PATIENT,
    ROLE_UNVERIFIED_DOCTOR,
    ROLE_DOCTOR;

    public String determinateRole() {
        switch (this) {
            case ROLE_ADMIN -> {
                return "ROLE_ADMIN";
            }
            case ROLE_DOCTOR -> {
                return "ROLE_DOCTOR";
            }
            case ROLE_PATIENT -> {
                return "ROLE_PATIENT";
            }
            case ROLE_UNVERIFIED_DOCTOR -> {
                return "ROLE_UNVERIFIED_DOCTOR";
            }
        }
        return "ROLE_ANONYMOUS";
    }
}
