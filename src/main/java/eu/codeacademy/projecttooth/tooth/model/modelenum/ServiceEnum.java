package eu.codeacademy.projecttooth.tooth.model.modelenum;

public enum ServiceEnum {
    FILLING(1L),
    ORAL_HYGIENE(2L),
    ROOT_CANAL_TREATMENT(3L),
    PROSTHETIC(4L),
    CONSULTATION(5L);

    private final Long service;

    ServiceEnum(Long service) {
        this.service = service;
    }
    public Long getService(){return service;}
}
