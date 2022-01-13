package eu.codeacademy.projecttooth.tooth.model.modelenum;

public enum RoleEnum {
    ADMIN,
    PATIENT,
    UNVERIFIED_DOCTOR,
    DOCTOR;

    public String determinateRole() {
        switch (this) {
            case ADMIN -> {
                return "ROLE_ADMIN";
            }
            case DOCTOR -> {
                return "ROLE_DOCTOR";
            }
            case PATIENT -> {
                return "ROLE_PATIENT";
            }
            case UNVERIFIED_DOCTOR -> {
                return "ROLE_UNVERIFIED_DOCTOR";
            }
        }
        return "ROLE_ANONYMOUS";
    }
}
