package com.fengjie.courseprogram.model.queryvo;

import com.fengjie.courseprogram.model.entity.Student;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author fengjie
 * @date 2019:05:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StudentVO extends Student implements Comparable<StudentVO> {

    private Integer grade;

    private Boolean isSubmitOperation;

    @Override
    public int compareTo(StudentVO o) {
        if (this.grade > o.getGrade()) {
            return 1;
        } else if (this.grade < o.getGrade()) {
            return -1;
        }
        return 0;
    }
}
