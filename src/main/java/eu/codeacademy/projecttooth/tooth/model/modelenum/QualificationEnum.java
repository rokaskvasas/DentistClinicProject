package eu.codeacademy.projecttooth.tooth.model.modelenum;


public enum QualificationEnum {
    FIRST_COURSE(1),
    SECOND_COURSE(2),
    THIRD_COURSE(3),
    FOURTH_COURSE(4);

    private int course;
    QualificationEnum(int course) {
        this.course = course;
    }

    public int getCourse(){
        return course;
    }
}
