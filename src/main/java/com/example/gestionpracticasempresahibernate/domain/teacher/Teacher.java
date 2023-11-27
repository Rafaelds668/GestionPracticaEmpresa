package com.example.gestionpracticasempresahibernate.domain.teacher;

import com.example.gestionpracticasempresahibernate.domain.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "teacher")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacher_id;

    private String first_name;
    private String last_name;
    private String password;
    private String email;

    @OneToMany(mappedBy = "tutor", fetch = FetchType.EAGER)
    private List<Student> students;
    @Override
    public String toString( ) {
        return "Teacher{" +
                "teacher_id=" + teacher_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", access_password='" + password + '\'' +
                ", email_address='" + email + '\'' +
                ", students=" + students.size() +
                '}';
    }
}
